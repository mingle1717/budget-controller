import CategoryForm from "./CategoryForm"
import AddCategoryContainer from "./AddCategoryContainer"
import { useHistory, useParams } from "react-router-dom"
import { useContext, useEffect, useState } from "react";
import Directories from "../Directories";
import AuthContext from "../AuthContext";
import FormInput from "../FormInput";


function EditBudget(){

    const {budgetId} = useParams();

    const [budget, setBudget]=useState(null);
    const [categories, setCategories] = useState([]);

    const [errors, setErrors] = useState(null);

    const history = useHistory();

    const auth = useContext(AuthContext);



    useEffect( () =>{
        fetch( "http://localhost:8080/api/budget/personal/" + auth.user.username,{
            method: "GET",
            headers: {
                Authorization: `Bearer ` + auth.user.token,
                "Content-Type": "application/json"
            },
        })
        .then(response => {
            if(response.status===200){
                return response.json();
            } else {
                console.log(response)
            }
        })
        .then(selectedBudget => {
            const incomingCategories = selectedBudget.categories;
            setCategories(incomingCategories);
            setBudget(selectedBudget);
            console.log(selectedBudget);
        })
        .catch(error => {
            if(error instanceof TypeError){
                setErrors( ["Could not connect to API"] );
            } else { 
               setErrors(error)
            }
        });
},[]);
    
    function inputChangeHandler(inputChangeEvent){
        const propertyName = inputChangeEvent.target.name;
        const newValue = inputChangeEvent.target.value;

        const budgetCopy = {...budget};

        budgetCopy[propertyName] = newValue;

    
        setBudget(budgetCopy);
        
    }

    function handleSubmit(evt){
        evt.preventDefault();

        console.log(budget);
        fetch( "http://localhost:8080/api/budget/personal/" + auth.user.username, {
            method: "PUT",
            headers: {
                "Content-Type" : "application/json",
                Authorization: `Bearer ` + auth.user.token,
                Accept : "application/json"
            },
            body: JSON.stringify(budget)
        }).then ( response => {
            if ( response.status === 204){
                history.push(Directories.MEMBERDASHBOARD);
            }
        })
        .catch (errors => {
            console.log(errors);

        })
    }

    function categoriesChangeHandler(incomingCategories){
        const categoriesCopy = [incomingCategories]
        setCategories(categoriesCopy);
    }


    return(
       
            
            <div className="container">
           
      
           
            <form onSubmit={handleSubmit}>
            {budget ? 
            <FormInput 
                    inputType={"number"} 
                    identifier={"balance"} 
                    labelText={"Starting Balance"} 
                    currVal={budget.startingBalance} 
                    labelClass={"balanceLabel"}
                    onChangeHandler={inputChangeHandler}  
                    className={"form-control"}/> : null
                    }
             
                    <div id="balanceHelp" className="form-text">What balance do you want to start with?</div>
                
                {categories ? categories.map( c => <CategoryForm category={c}  onCategoryChange={categoriesChangeHandler} />) : null}
                <button type="submit" className="budgetSubmitButton mySubmitButton">Submit</button>
            </form>
        </div>
        

        )
}
export default EditBudget
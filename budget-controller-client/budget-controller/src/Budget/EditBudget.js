import CategoryForm from "./CategoryForm"
import AddCategoryContainer from "./AddCategoryContainer"
import { useHistory, useParams } from "react-router-dom"
import { useContext, useEffect, useState } from "react";
import Directories from "../Directories";
import AuthContext from "../AuthContext";
import FormInput from "../FormInput";


function EditBudget(){

    

    const [budget, setBudget]=useState(null);
    const [categories, setCategories] = useState([]);
    const [categoryContainer, setCategoryContainer] = useState(false);
    const [errors, setErrors] = useState([]);

    const history = useHistory();

    const auth = useContext(AuthContext);



    useEffect( () =>{
        fetch( "http://localhost:8080/api/budget/personal" ,{
            method: "GET",
            headers: {
                Authorization: `Bearer ` + auth.user.token,
                "Content-Type": "application/json"
            },
        })
        .then(async response => {
            if(response.status===200){
                return response.json();
            } else {
                return Promise.reject(await response.json());
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
        fetch( "http://localhost:8080/api/budget/personal", {
            method: "PUT",
            headers: {
                "Content-Type" : "application/json",
                Authorization: `Bearer ` + auth.user.token,
                Accept : "application/json"
            },
            body: JSON.stringify(budget)
        }).then ( async response => {
            if ( response.status === 204){
                history.push(Directories.MEMBERDASHBOARD);
            }
            return Promise.reject(await response.json())
        })
        .catch (errorList => {
            if( errorList instanceof TypeError){
                setErrors(["Could not connect to api"]);
            } else {
                setErrors( errorList + " ");
            }
        })
    }

    function categoriesChangeHandler(incomingCategories){
        const categoriesCopy = [...categories]
        for(let i = 0; i < categoriesCopy.length; i++){
            if(categoriesCopy[i].categoryId === incomingCategories.categoryId){
                categoriesCopy[i] = incomingCategories;
            }
        }
        setCategories(categoriesCopy);
        const budgetCopy = {budgetId : budget.budgetId, budgetName: budget.budgetName, balance : budget.balance, categories : categoriesCopy};
        setBudget(budgetCopy)
    }
    // function removeCategory(category){
    //     console.log("clock")
    //     const categoriesCopy = [...categories]
    //     for(let i = 0; i < categoriesCopy.length; i++){
    //         if(categoriesCopy[i].categoryId === category.categoryId){
    //             categoriesCopy.splice(i,1);
    //             console.log(categoriesCopy);
    //         }
    //     }
    // }
//     function categoriesChangeHandler(incomingCategories){
//         const categoriesCopy = [...incomingCategories];
       
//         const budgetCopy = {balance : budget.balance, categories : categoriesCopy};
//         setBudget(budgetCopy)
//     }
// function addCategoryContainer(){
//     setCategoryContainer(true);
// }

    return(
       
            
            <div className="container createBudgetContainer">
           {errors?
            <div className=" myText error" id="messages">{errors}</div>
                : null}
      
           
            <form onSubmit={handleSubmit}>
            {budget ? 
            <FormInput 
                    inputType={"number"} 
                    identifier={"balance"} 
                    labelText={"Starting Balance"} 
                    currVal={budget.balance} 
                    labelClass={"balanceLabel"}
                    onChangeHandler={inputChangeHandler}  
                    className={"form-control"}/> : null
                    }
             
                    <div id="balanceHelp" className="form-text">What balance do you want to start with?</div>

                {categories ? categories.map( c => <CategoryForm category={c}  onCategoryChange={categoriesChangeHandler} /> ) : null}
                {/* <button type="button" onClick={addCategoryContainer}> + </button>
                    {categoryContainer ? categoryContainer === true ? <AddCategoryContainer onCategoriesChange={categoriesChangeHandler} /> : null : null} */}
                <button type="submit" className="budgetSubmitButton mySubmitButton">Submit</button>
            </form>
        </div>
        

        )
}
export default EditBudget
import CategoryForm from "./CategoryForm"
import AddMemberForm from "./AddMemberForm"
import {useState} from "react"
import{useHistory} from "react-router-dom"

import FormInput from "../FormInput"


function CreateBudget(){

    const [budget, setBudget] = useState(null);
    const [error, setError]= useState([]);
    const [categories, setCategories] = useState([]);

    const history = useHistory();

    function handleSubmit(evt){
        evt.preventDefault();

        fetch( "http://localhost:8080/api", {
            method: "POST",
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify(budget)
        }).then ( response => {
            if ( response.status === 201){
                history.push("/budgetownerdashboard")
            }
        })
        .catch (errors => {
            if(errors instanceof TypeError){
                setError(["Could not connect to api"]);
            } else {
                setError( errors );
            }

        })
    }

    function inputChangeHandler(inputChangeEvent){
        const propertyName = inputChangeEvent.target.name;
        const newValue = inputChangeEvent.target.value;

        const budgetCopy = {...budget};

        budgetCopy[propertyName] = newValue;

        setBudget(budgetCopy);
    }

    return(
        <div className="container">
            <h1>Create Budget</h1>
            <form onSubmit={handleSubmit}>
            <FormInput 
                    inputType={"text"} 
                    identifier={"startingBalance"} 
                    labelText={"Starting Balance"} 
                    currVal={""} 
                    labelClass={"balanceLabel"}
                    onChangeHandler={inputChangeHandler}  
                    className={"form-control"}/>
             
                    <div id="balanceHelp" className="form-text">What balance do you want to start with?</div>
             
                <CategoryForm category={categories}/>
                <AddMemberForm/>
                <button type="submit" className="btn btn-primary">Submit</button>
            </form>
        </div>
        )
}
export default CreateBudget
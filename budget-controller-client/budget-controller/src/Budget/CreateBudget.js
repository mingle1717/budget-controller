
import {useState} from "react"
import{useHistory} from "react-router-dom"

import FormInput from "../FormInput"
import AddCategoryContainer from "./AddCategoryContainer"
import AddMemberContainer from "./AddMemberContainer";

function CreateBudget(){

    const [budget, setBudget] = useState({startingBalance: 0, members : [], categories: []});
    const [error, setError]= useState([]);
    const [categories, setCategories] = useState([]);
    const [members, setMembers] = useState([]);
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

    function handleCategories(category) {
        setCategories(...categories);
    }


    function handleMembers(...members) {
        setMembers(...members);
    }

    return(
        <div className="container">
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
             <div className="categories">
                <AddCategoryContainer/>
            </div>
            <div className="members">
                <AddMemberContainer />
            </div>
                <button type="submit" className="btn btn-primary">Submit</button>
            </form>
        </div>
        )
}
export default CreateBudget
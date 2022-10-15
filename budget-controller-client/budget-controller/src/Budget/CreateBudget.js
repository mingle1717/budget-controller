
import {useState, useContext} from "react"
import{useHistory} from "react-router-dom"
import AuthContext from "../AuthContext";
import Directories from "../Directories";
import FormInput from "../FormInput"
import AddCategoryContainer from "./AddCategoryContainer"
import AddMemberContainer from "./AddMemberContainer";

function CreateBudget(){
    const auth = useContext(AuthContext);
    const [budget, setBudget] = useState({balance: 0, appUsers : [], categories: []});
    const [error, setError]= useState([]);
    const [categories, setCategories] = useState([]);
    const [appUsers, setAppUsers] = useState([auth.user]);
    const history = useHistory();

    
   
    
    function handleSubmit(evt){
        evt.preventDefault();

      

        console.log(budget)
        fetch( "http://localhost:8080/api/budget", {
            method: "POST",
            headers: {
                "Content-Type" : "application/json",
                Authorization : "Bearer " + auth.user.token,
            },
            body: JSON.stringify(budget)
        }).then ( response => {
            if ( response.status === 201){
                history.push(Directories.OWNERDASHBOARD)
            }
        })
        .catch (errors => {
            console.log(errors);

        })
    }

    function inputChangeHandler(inputChangeEvent){
        const propertyName = inputChangeEvent.target.name;
        const newValue = inputChangeEvent.target.value;

        const budgetCopy = {...budget};

        budgetCopy[propertyName] = newValue;

        setBudget(budgetCopy);
        
    }

    function categoriesChangeHandler(incomingCategories){
        const categoriesCopy = [...incomingCategories]
        setCategories(...categoriesCopy);
        const budgetCopy = {balance : budget.balance, appUsers : appUsers, categories : categories};
        setBudget(budgetCopy)
    }

    function appUsersChangeHandler(incomingAppUsers) {
        const appUsersCopy = [...incomingAppUsers]
        setAppUsers(...appUsersCopy);
        const budgetCopy = {balance : budget.balance, appUsers : appUsers, categories : categories};
        setBudget(budgetCopy)
    }

    return(
        <div className="container createBudgetContainer">
            <form onSubmit={handleSubmit}>
            <FormInput 
                    inputType={"text"} 
                    identifier={"balance"} 
                    labelText={"Starting Balance"} 
                    currVal={""} 
                    labelClass={"balanceLabel"}
                    onChangeHandler={inputChangeHandler}  
                    className={"form-control"}/>
             
                    <div id="balanceHelp" className="form-text">What balance do you want to start with?</div>
             <div className="categories">
                <AddCategoryContainer onCategoriesChange={categoriesChangeHandler}/>
            </div>
            <div className="members">
                <AddMemberContainer onMembersChange={appUsersChangeHandler}/>
            </div>
                <button type="submit" className="btn btn-primary mySubmitButton">Submit</button>
            </form>
        </div>
        )
}
export default CreateBudget
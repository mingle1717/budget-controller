
import {useState, useContext} from "react"
import{useHistory} from "react-router-dom"
import AuthContext from "../AuthContext";
import Directories from "../Directories";
import FormInput from "../FormInput"
import AddCategoryContainer from "./AddCategoryContainer"
import AddMemberContainer from "./AddMemberContainer";

function CreateBudget(){
    const auth = useContext(AuthContext);
    const startingCategories = [{categoryId : 0, categoryName : "Savings", categoryPercent : 100, higherLimit : 1000, lowerLimit : 500, goal : false }];
    const [budget, setBudget] = useState({budgetName : auth.user.username, balance: 0, appUsers : [auth.user], categories: [...startingCategories]});
    const budgetAppUsers = budget.appUsers;
    const [error, setError]= useState([]);
    const [categories, setCategories] = useState();
    const [appUsers, setAppUsers] = useState(budgetAppUsers);
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
        })
        .then ( response => {
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
        const categoriesCopy = [...incomingCategories];
        setCategories(categoriesCopy);
        const budgetCopy = {balance : budget.balance, appUsers : appUsers, categories : categoriesCopy};
        setBudget(budgetCopy)
    }

    function appUsersChangeHandler(incomingAppUsers) {
        const appUsersCopy = [...incomingAppUsers];
        setAppUsers(appUsersCopy);
        const budgetCopy = {balance : budget.balance, appUsers : appUsersCopy, categories : categories};
        console.log(budgetCopy);
        setBudget(budgetCopy)
    }

    return(
        <div className="container createBudgetContainer" key="createBudget">
            <form onSubmit={handleSubmit}>
            <FormInput 
                    inputType={"number"} 
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
                <button type="submit" className="budgetSubmitButton mySubmitButton">Submit</button>
            </form>
        </div>
        

        )
}
export default CreateBudget
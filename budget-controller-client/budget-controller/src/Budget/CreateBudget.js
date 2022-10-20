
import {useState, useContext} from "react"
import{useHistory} from "react-router-dom"
import AuthContext from "../AuthContext";
import Directories from "../Directories";
import FormInput from "../FormInput"
import AddCategoryContainer from "./AddCategoryContainer"
import AddMemberContainer from "../AddMembers/AddMemberContainer";

function CreateBudget({createBudgetPromote}){
    const auth = useContext(AuthContext);
    const startingCategories = [{categoryId : 0, categoryName : "Savings", categoryPercent : 100, higherLimit : 1000, lowerLimit : 500, goal : false }];
    const [budget, setBudget] = useState({budgetName : auth.user.username, balance: 0, appUsers : [auth.user], categories: [...startingCategories]});
    const budgetAppUsers = budget.appUsers;
    const [errors, setErrors]= useState([]);
    const [categories, setCategories] = useState();
    const [appUsers, setAppUsers] = useState(budgetAppUsers);
    const history = useHistory();

    
   
    
    function handleSubmit(evt){
        evt.preventDefault();
    
        console.log( budget)

        fetch( "http://localhost:8080/api/budget", {
            method: "POST",
            headers: {
                "Content-Type" : "application/json",
                Authorization : "Bearer " + auth.user.token,
            },
            body: JSON.stringify(budget)
        })
        .then ( async response => {
            if ( response.status === 201){
                createBudgetPromote();
                history.push(Directories.OWNERDASHBOARD)
            }
            return Promise.reject(await response.json());
        })
        .catch (errorList => {
            if( errorList instanceof TypeError){
                setErrors(["Could not connect to api"]);
            } else {
                setErrors( errorList + " ");
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

    function categoriesChangeHandler(incomingCategories){
        const categoriesCopy = [...incomingCategories];
       
        const budgetCopy = {balance : budget.balance, appUsers : appUsers, categories : categoriesCopy};
        setBudget(budgetCopy)
    }

    function appUsersChangeHandler(incomingAppUsers) {
        const appUsersCopy = [...incomingAppUsers];
     
        const budgetCopy = {balance : budget.balance, appUsers : appUsersCopy, categories : categories};
        console.log(budgetCopy);
        setBudget(budgetCopy)
    }

    return(
        <div className="container createBudgetContainer" key="createBudget">
           {errors?
            <div className=" myText error" id="messages">{errors}</div>
                : null}
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
                <button type="submit" className="budgetSubmitButton mySubmitButton">Submit</button>
            </form>
        </div>
        

        )
}
export default CreateBudget
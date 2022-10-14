
import {useState, useContext} from "react"
import{useHistory} from "react-router-dom"
import AuthContext from "../AuthContext";
import FormInput from "../FormInput"
import AddCategoryContainer from "./AddCategoryContainer"
import AddMemberContainer from "./AddMemberContainer";

function CreateBudget(){

    const auth = useContext(AuthContext);
    const [budget, setBudget] = useState({startingBalance: 0, appUsers : [], categories: []});
    const [error, setError]= useState([]);
    const [categories, setCategories] = useState([]);
    const [appUsers, setAppUsers] = useState([]);
    const history = useHistory();

    
    function handleSubmit(evt){
        evt.preventDefault();

        budget.categories = categories;
        budget.appUsers = appUsers;
        setBudget(budget);

        fetch( "http://localhost:8080/api/budget", {
            method: "POST",
            headers: {
                "Content-Type" : "application/json",
                Accept : "application/json",
                Authorization : `Bearer ${auth.user.token}`
                
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

    function categoriesChangeHandler(categories){
        const categoriesCopy = [...categories]
        setCategories(...categoriesCopy);
    }

    function appUsersChangeHandler(appUsers) {
        const appUsersCopy = [...appUsers]
        setAppUsers(...appUsersCopy);
    }

    return(
        <div className="container createBudgetContainer">
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
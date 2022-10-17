import CategoryForm from "./CategoryForm"
import AddCategoryContainer from "./AddCategoryContainer"
import { useHistory, useParams } from "react-router-dom"
import { useContext, useEffect, useState } from "react";
import Directories from "../Directories";
import AuthContext from "../AuthContext";
function EditBudget(){

    const {budgetId} = useParams();

    const [budget, setBudget]=useState(null);
    const [categories, setCategories] = useState([]);

    const [errors, setErrors] = useState(null);

    const history = useHistory();

    const auth = useContext(AuthContext);



    useEffect( () =>
        fetch( "http://localhost:8080/api/budget/" + budgetId)
            .then(response => {
                if(response.status===200){
                    return response.json();
                } else {
                    console.log(response)
                }
            }
        )
        .then(selectedBudget => {
            setBudget(selectedBudget);
        })
            .catch(error => {
                if(error instanceof TypeError){
                    setErrors( ["Could not connect to API"] );

                } else { 
                    setErrors(error)
            }
        })
    ,[]);
    
    function handleSubmit(evt){
        evt.preventDefault();

        

        fetch( "http://localhost:8080/api/budget/" + auth.user.username, {
            method: "PUT",
            headers: {
                "Content-Type" : "application/json",
                Authorization : `Bearer ${auth.user.token}`,
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

    function categoriesChangeHandler(categories){
        const categoriesCopy = [...categories]
        setCategories(...categoriesCopy);
    }


    return(
        <div> 
            
            <div className="container">
           
      
           
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="startingBalance" className="form-label">Starting Balance</label>
                    <input type="number" className="form-control" id="startingBalance" aria-describedby="balanceHelp"/>
                    <div id="balanceHelp" className="form-text">What balance do you want to start with?</div>
                </div>
                <AddCategoryContainer onCategoriesChange={categoriesChangeHandler}/>
                <button type="submit" className="budgetSubmitButton mySubmitButton">Submit</button>
            </form>
        </div>
        

        </div>
        )
}
export default EditBudget
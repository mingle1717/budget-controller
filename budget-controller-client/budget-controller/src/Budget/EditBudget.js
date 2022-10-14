import CategoryForm from "./CategoryForm"
import AddCategoryContainer from "./AddCategoryContainer"

function EditBudget(){
    return(
        <div> 
            
            <div className="container">
           
      
           
            <form>
                <div className="mb-3">
                    <label htmlFor="startingBalance" className="form-label">Starting Balance</label>
                    <input type="number" className="form-control" id="startingBalance" aria-describedby="balanceHelp"/>
                    <div id="balanceHelp" className="form-text">What balance do you want to start with?</div>
                </div>
                <AddCategoryContainer/>
                <button type="submit" className="btn btn-primary">Submit</button>
            </form>
        </div>
        

        </div>
        )
}
export default EditBudget
import CategoryForm from "./CategoryForm"


function EditBudget(){
    return(
        <div> 
            
            <div className="container">
            <h1>Edit Budget</h1>
           
      
           
            <form>
                <div className="mb-3">
                    <label htmlFor="startingBalance" className="form-label">Starting Balance</label>
                    <input type="number" className="form-control" id="startingBalance" aria-describedby="balanceHelp"/>
                    <div id="balanceHelp" className="form-text">What balance do you want to start with?</div>
                </div>
                <CategoryForm/>
                <button type="submit" className="btn btn-primary">Submit</button>
            </form>
        </div>
        

        </div>
        )
}
export default EditBudget
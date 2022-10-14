
import TransactionForm from "./TransactionForm"
import { Link } from "react-router-dom"
function EditTransaction(){
    return(
        <div>
            <form>
                <TransactionForm/>
                <button type="submit" className="btn btn-primary">Edit</button>
                <Link to ="budgetmemberview" className="btn btn-danger">Cancel</Link>
            </form>

        </div>
        )
}
export default EditTransaction
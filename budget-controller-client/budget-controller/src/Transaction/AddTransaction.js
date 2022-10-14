
import TransactionForm from "./TransactionForm"
import { Link } from "react-router-dom"




function AddTransaction(){
    return(
        <div>
            <form>
            <TransactionForm/>
            <button type="submit" className="btn btn-primary">Add</button>
            <Link to ="budgetmemberview" className="btn btn-danger">Cancel</Link>
            </form>
        </div>
        )
}
export default AddTransaction
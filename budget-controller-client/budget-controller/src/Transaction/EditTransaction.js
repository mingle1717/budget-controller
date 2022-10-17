
import TransactionForm from "./TransactionForm"
import { Link } from "react-router-dom"
import Directories from "../Directories";
import "./Transaction.css"
function EditTransaction(){
    return(
        <div className="editTransaction">
            <form>
                <TransactionForm/>
                <div className="editButtons">
                <button type="submit" className="btn btn-primary">Edit</button>
                <Link to ={Directories.MEMBERVIEW} className="btn btn-danger">Cancel</Link>
                </div>
            </form>

        </div>
        )
}
export default EditTransaction
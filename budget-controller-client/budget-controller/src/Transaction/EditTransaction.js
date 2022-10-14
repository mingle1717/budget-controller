
import TransactionForm from "./TransactionForm"
import { Link } from "react-router-dom"
import Directories from "../Directories";
function EditTransaction(){
    return(
        <div>
            <form>
                <TransactionForm/>
                <button type="submit" className="btn btn-primary">Edit</button>
                <Link to ={Directories.MEMBERVIEW} className="btn btn-danger">Cancel</Link>
            </form>

        </div>
        )
}
export default EditTransaction

import TransactionForm from "./TransactionForm"
import { Link } from "react-router-dom"
import Directories from "../Directories";
import "./Transaction.css"
function EditTransaction(){
    return(
        <div className="container addContainer">
            <form>
                <TransactionForm/>
                <div className="buttons">
                <button type="submit" className="tranSubmitButton">Edit</button>
                <Link to ={Directories.MEMBERVIEW} className="tranCancelButton">Cancel</Link>
                </div>
            </form>

        </div>
        )
}
export default EditTransaction
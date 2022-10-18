
import TransactionForm from "./TransactionForm"
import { Link, useHistory, useParams } from "react-router-dom"
import AuthContext from "../AuthContext";
import { useContext, useState, useEffect } from "react";
import Directories from "../Directories";
import "./Transaction.css"
function EditTransaction(){

    const transactionId = useParams();

    const auth = useContext(AuthContext)
    const [transaction, setTransaction] = useState();
    const history = useHistory();

    function handleTransactionChange(updatedTransaction) {
        setTransaction(updatedTransaction);
    }


    useEffect(() => {
        fetch("http://localhost:8080/api/transaction/" + transactionId, {
            method: "GET",
            headers: {
                Authorization: `Bearer ` + auth.user.token,
                "Content-Type": "application/json"
            },
        })
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    console.log(response);
                }
            })
            .then(incomingTransaction => {
            
                //setBudgetTransactions(transactions);
                setTransaction(incomingTransaction);
                console.log(incomingTransaction)
            })
            .catch(error => {
                console.log(error);
            });
    }, [])


    return(
        <div className="container addContainer">
            <form>
                <TransactionForm parentTransaction={transaction} onTransactionChange={handleTransactionChange} />
                <div className="buttons">
                <button type="submit" className="tranSubmitButton">Edit</button>
                <Link to ={Directories.MEMBERVIEW} className="tranCancelButton">Cancel</Link>
                </div>
            </form>

        </div>
        )
}
export default EditTransaction
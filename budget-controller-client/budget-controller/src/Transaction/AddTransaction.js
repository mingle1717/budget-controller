
import TransactionForm from "./TransactionForm"
import { Link } from "react-router-dom"
import { useEffect, useState } from "react";
import Directories from "../Directories";



function AddTransaction() {

    const [transaction, setTransaction] = useState({transactionName: "", transactionAmount: 0, transactionCategory: "", transacationDescription : ""});


    function handleTransactionChange(updatedTransaction) {
        setTransaction(updatedTransaction);
    }


    function handleSubmit(event) {
        event.preventDefault();
        
        fetch("http://localhost:8080/api/transaction", {
            method: "POST",
            headers: {
                Authorization : `Bearer ` + auth.user.token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(transaction)
        })
            .then(response => {
                if (response.status === 201) {
                    return response.json();
                } else {
                    console.log(response);
                }
            })
            .catch(error => {
                console.log(error);
            });
        }
            

    return (
            <div>
                <form onSubmit={handleSubmit}>
                    <TransactionForm onTransactionChange={handleTransactionChange} />
                    <button type="submit" className="btn btn-primary">Add</button>
                    <Link to={Directories.MEMBERVIEW} className="btn btn-danger">Cancel</Link>
                </form>
            </div>
        )
    }
    export default AddTransaction

import TransactionForm from "./TransactionForm"
import { Link } from "react-router-dom"
import { useEffect, useState, useContext } from "react";
import AuthContext from "../AuthContext";




function AddTransaction() {

    const [transaction, setTransaction] = useState(null);
    const auth = useContext(AuthContext);

    function handleTransactionChange(transaction) {
        setTransaction(transaction);
    }


    function handleSubmit(event) {
        event.preventDefault();
        
        fetch("http://localhost:8080/api/transaction", {
            method: "POST",
            headers: {
                Authorization : `Bearer ` + auth.user.token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(
                transaction
            )
        })
            .then(response => {
                if (response.status === 200) {
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
                    <Link to="budgetmemberview" className="btn btn-danger">Cancel</Link>
                </form>
            </div>
        )
    }
    export default AddTransaction
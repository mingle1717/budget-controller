import { Link } from "react-router-dom"
import AuthContext from '../AuthContext';
import { useContext, useState, useEffect } from "react";
import Transaction from "./Transaction";
import Directories from "../Directories"
import "./Transaction.css"

function BudgetMemberView(){

    const auth = useContext(AuthContext);

    const [transactions, setTransactions] = useState([{transactionName: "", transactionAmount: 0, transactionCategory: "", transacationDescription : ""}]);

    useEffect(() => {
        fetch("http://localhost:8080/api/transaction/" + auth.user.username, {
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
            .then(transactions => {
                const transactionsCopy = transactions;
                setTransactions(transactionsCopy);
                console.log(transactions)
            })
            .catch(error => {
                console.log(error);
            });
    }, [])

    return(
        <div>
            <table className="table container">
                <thead>
                    <tr>
                    <th scope="col"> Money Spent </th>
                    <th scope="col"> Category </th>
                    <th scope="col"> Description </th>
                    <th/>
                    
                    </tr>
                </thead>
                <tbody> 
                <>
                {transactions.map( t => <Transaction transactionId={t.transactionId} transactionAmount={t.transactionAmount} transactionCategory={t.transactionCategory} transacationDescription={t.transacationDescription} username={t.username}/>)}
                </>
                </tbody>
                </table>
                <Link to={Directories.ADDTRANSACTION} className="addLink"> Add Transaction </Link>
        </div>
        )
}
export default BudgetMemberView
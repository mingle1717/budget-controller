import { Link } from "react-router-dom"
import AuthContext from '../AuthContext';
import { useContext, useState, useEffect } from "react";
import Transaction from "./Transaction";
import Directories from "../Directories"
import "./Transaction.css"

function BudgetMemberView(){

    const auth = useContext(AuthContext);
    const [budgetTransactionTotals, setBudgetTransactionTotals] = useState([]);
    const [transactions, setTransactions] = useState([{transactionName: "", transactionAmount: 0, category:{}, transacationDescription : ""}]);
    const [errors, setErrors] = useState([])

    function onTransactionDelete(){
        loadTransactions();
    }

    function loadTransactions(){
    
        fetch("http://localhost:8080/api/transaction/getall/0", {
            method: "GET",
            headers: {
                Authorization: `Bearer ` + auth.user.token,
                "Content-Type": "application/json"
            },
        })
            .then(async response => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    return Promise.reject(await response.json());
                }
            })
            .then(transactions => {
                const transactionsCopy = [...transactions];
                setTransactions(transactionsCopy);
                console.log(transactions)
            })
            .catch (errorList => {
                if( errorList instanceof TypeError){
                    setErrors(["Could not connect to api"]);
                } else {
                    setErrors( errorList + " ");
                }
            })
        }

        useEffect(() => {
            fetch("http://localhost:8080/api/transaction/transactionbalance", {
                method: "GET",
                headers: {
                    Authorization: `Bearer ` + auth.user.token,
                    "Content-Type": "application/json"
                },
            })
            .then(async response => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    return Promise.reject(await response.json());
                }
            })
            .then(result => {
                console.log(result)
                setBudgetTransactionTotals(result);
            })
            .catch (errorList => {
                if( errorList instanceof TypeError){
                    setErrors(["Could not connect to api"]);
                } else {
                    setErrors( errorList + " ");
                }
            })
        }, [])

        useEffect(
            () => {
                loadTransactions();
            },
            []);


    return(
        <div className="container">
            {errors?
            <div className=" myText error" id="messages">{errors}</div>
                : null}
            <table className="table container myTable">
                <thead className="myTable">
                    <tr>
                    <th className="myTable" scope="col"> Transaction name </th>
                        <th className="myTable" scope="col"> Money Spent </th>
                        <th className="myTable" scope="col"> Category </th>
                        <th className="myTable" scope="col"> Description </th>
                        <th/>
                    
                    </tr>
                </thead>
                <tbody> 
                    <>
                    {transactions ? transactions.map( t => <Transaction key={t.transactionId} onTransactionDelete={onTransactionDelete} transactionName={t.transactionName} transactionId={t.transactionId} transactionAmount={t.transactionAmount} transactionCategory={t.category.categoryName} transacationDescription={t.transacationDescription} username={t.username}/>): null}
                    </>
                </tbody>
            </table>
            <Link to={Directories.ADDTRANSACTION} className="addLink tranSubmitButton"> Add Transaction </Link>
            <table className="table container budgetTable">
            <thead className="myTotalTable">
                    <tr >
                        <th className="myTotalTable" scope="col"> Category </th>
                        <th className="myTotalTable" scope="col"> Total Spent </th>
                    </tr>
                    </thead>
             
                <>
                    {budgetTransactionTotals.map( t =>   <tbody><td className="myTotalTable"> {t.name}  </td>
                <td className="myTotalTable"> ${t.value} </td> </tbody>)}
                </>  


                
            </table>
        </div>
        )
}
export default BudgetMemberView
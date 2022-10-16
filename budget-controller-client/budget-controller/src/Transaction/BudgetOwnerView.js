

import { Link } from "react-router-dom"
import AuthContext from '../AuthContext';
import { useContext, useState } from "react";
import Transaction from "./Transaction";
import Directories from "../Directories"
function BudgetOwnerView(props){

    const auth = useContext(AuthContext);

    const [transactions, setTransactions] = useState([{transactionName: "", transactionAmount: 0, transactionCategory: "", transacationDescription : ""}]);

    function loadAllTransactions(){
        fetch ( "http://localhost:8080/api/transaction" + props.userId)
        .then(response =>{
            if(response.status === 200){
                return response.json();
            } else {
                console.log(response.json);
            }
        })
        .then( transactionList => {
            setTransactions(transactionList);
        })
        
    }


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
                <Transaction category={"savings"} moneySpent={500} spender={"Cristian"} description={"ipsum adfasdfasdfasdfasdfa"}/>
                <Transaction category={"savings"} moneySpent={500} spender={"Ryan"}description={"ipsum adfasdfasdfasdfasdfa"}/>
                <Transaction category={"savings"} moneySpent={500} spender={"Cristian"} description={"ipsum adfasdfasdfasdfasdfa"}/>
                <Transaction category={"savings"} moneySpent={500} spender={"Kendy"} description={"ipsum adfasdfasdfasdfasdfa"}/>
                <Transaction category={"savings"} moneySpent={500} spender={"Cristian"} description={"ipsum adfasdfasdfasdfasdfa"}/>
                
                </tbody>
                </table>
                <Link to="/addtransaction"> Add Transaction </Link>
                <table className="table container">
                    <tbody>


                    </tbody>
                </table>
        </div>
        )
}
export default BudgetOwnerView
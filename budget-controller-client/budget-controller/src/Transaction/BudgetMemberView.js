import { Link } from "react-router-dom"
import Transaction from "./Transaction"

function BudgetMemberView(){
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
        </div>
        )
}
export default BudgetMemberView
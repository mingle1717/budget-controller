import { Link, useParams } from "react-router-dom"
import Transaction from "./Transaction"
import Directories from "../Directories"

function BudgetMemberView(){

    const {userId} = useParams();

    function loadAllTransactions() {
        fetch()
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
                
                </tbody>
                </table>
                <Link to={Directories.ADDTRANSACTION}> Add Transaction </Link>
        </div>
        )
}
export default BudgetMemberView
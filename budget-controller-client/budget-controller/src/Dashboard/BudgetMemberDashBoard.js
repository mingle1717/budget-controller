import {Link} from "react-router-dom"
import shibe from "../Images/PieChart.jpg"
import CategoriesPieChart from "../Budget/CategoriesPieChart"
import TransactionsPieChart from "../Transaction/TransactionsPieChart";


function BudgetMemberDashboard(){
    return(
        <div>
            <h1>Budget Member Dashboard</h1>

            <div>
            <Link to="/createbudget">Create Budget</Link>
            </div>
            <Link> Click here for some advice! </Link>
            <div>
                <CategoriesPieChart/>
            <Link to = "/budgetmemberview" className="spendingPieContainer" >
                <TransactionsPieChart/>
            </Link>
            </div>
            <div className="manageTransactions">
            <Link to ="/budgetmembermanageauto"> Manage auto transactions </Link>
            </div>
            
        </div>
        )
}
export default BudgetMemberDashboard
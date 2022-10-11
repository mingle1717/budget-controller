import { useContext } from "react";
import { useHistory } from "react-router-dom";
import AuthContext from "./AuthContext";
import { Link } from "react-router-dom";

function Navbar(props){


    const auth = useContext(AuthContext);
    const history = useHistory();




    return(

    <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <div>
            {auth.user ? 
            (auth.user.userRoles[0].roleName==="Admin" ? <Link to ="/budgetownerdashboard" className="navbar-brand">{auth.user.username} Dashboard</Link> : 
            <Link to ="/budgetmemberdashboard" className="navbar-brand">Member Dashboard</Link> ) : 
            <Link to ="/home" className="navbar-brand">Home</Link>}
        
        </div>
        <div>
            {auth.user ? (auth.user.userRoles[0].roleName==="Admin" ?  <Link to ="/home" className="navbar-brand">Add members</Link>  : 
            null) : null}
        
        </div>
        <Link to ="/contact" className="navbar-brand">Contact</Link>
        <div>
            {auth.user? <button className="btn-btn danger" onClick={() => auth.logout()}>Logout</button>
           : <Link to ="/login" className="navbar-brand">Login</Link> }
        </div>
    </nav>
    )
}
export default Navbar;
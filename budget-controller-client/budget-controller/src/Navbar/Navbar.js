import { useContext, useLayoutEffect, useState } from "react";
import { useHistory, useLocation } from "react-router-dom";
import AuthContext from "../AuthContext";
import { Link } from "react-router-dom";
import "./Navbar.css"

import Directories, {PageTitles} from "../Directories";

function Navbar(){


    const auth = useContext(AuthContext);
    const history = useHistory();
    const currentLocation = useLocation();
    const [savedLocationPath, setSavedLocationPath] = useState(null);
    const [pageTitle, setPageTitle] = useState('');

    useLayoutEffect(() => {
        if ( savedLocationPath === currentLocation.pathname) return;
        setSavedLocationPath(currentLocation.pathname);

        switch(currentLocation.pathname)
        {
            case Directories.LOGIN:
                setPageTitle(PageTitles.LOGIN);
                break;
            case Directories.CONTACT:
                setPageTitle(PageTitles.CONTACT);
                break;
            case Directories.SIGNUP:
                setPageTitle(PageTitles.SIGNUP);
                break;
            case Directories.OWNERDASHBOARD:
                setPageTitle(PageTitles.OWNERDASHBOARD);
                break;  
            case Directories.MEMBERDASHBOARD:
                setPageTitle(PageTitles.MEMBERDASHBOARD);
                break;
            case Directories.OWNERVIEW:
                setPageTitle(PageTitles.OWNERVIEW);
                break;
            case Directories.MEMBERVIEW:
                setPageTitle(PageTitles.MEMBERVIEW);
                break;
            case Directories.CREATEBUDGET:
                setPageTitle(PageTitles.CREATEBUDGET);
                break;
            case Directories.EDITBUDGET:
                setPageTitle(PageTitles.EDITBUDGET);
                break;
            case Directories.ADDTRANSACTION:
                setPageTitle(PageTitles.ADDTRANSACTION);
                break;
            case Directories.EDITTRANSACTION:
                setPageTitle(PageTitles.EDITTRANSACTION);
                break;
            case Directories.OWNERMANAGEAUTO:
                setPageTitle(PageTitles.OWNERMANAGEAUTO);
                break;
            case Directories.MEMBERMANAGEAUTO:
                setPageTitle(PageTitles.MEMBERMANAGEAUTO);
                break;
            default:
                setPageTitle(PageTitles.HOME);
                break;
        }
    }, [currentLocation.pathname, savedLocationPath])

    return(

    <div className="navbarTop">
            
     hi
        <nav className="myNav">
            
            <div className="homeNav">
                {auth.user ? 
                    (auth.user.userRoles[0].roleName==="Admin" ? 
                    <Link to ={Directories.OWNERDASHBOARD} className="homeLink">{auth.user.username} Dashboard</Link> : 
                    <Link to ={Directories.MEMBERDASHBOARD} className="homeLink">Member Dashboard</Link> ) : 
                    <Link to ={Directories.HOME} className="homeLink">Home</Link>}
            </div>
            <div className="addNav">
                <div>
                    {auth.user ? (auth.user.userRoles[0].roleName==="Admin" ?  <Link to ={Directories.ADDMEMBERS}  className="homeLink">Add members</Link>  : 
                    null) : null}
                
                </div >
                        </div>
                <div className="contactNav">
                    <Link to ={Directories.CONTACT}  className="homeLink" >Contact</Link>
                </div>



                <div className="loginNav">
                    {
                    auth.user ? 
                    <button className="navCancelButton" onClick={() => auth.logout()}>Logout</button>
                    : 
                    <Link to ={Directories.LOGIN}  className="homeLink" >Login</Link> 
                    }
                </div>
        
            {
                (savedLocationPath !== Directories.HOME) && (
                    <div  className={"pageHeaderContainer"}>
                        <div className={"pageHeader"}>
                            <div className="title">{pageTitle}</div>
                        </div>
                    </div>
                )
            }
        </nav>
    </div>
    )
}
export default Navbar;
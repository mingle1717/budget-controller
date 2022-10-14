import { BrowserRouter, Switch, Route, Redirect } from 'react-router-dom';
import {useEffect, useState} from 'react';
import Navbar from './Navbar/Navbar';
import Home from './Home';
import Contact from './Contact';
import Login from './Authentication/Login';
import Signup from './Authentication/Signup';
import AddTransaction from './Transaction/AddTransaction';
import CreateBudget from './Budget/CreateBudget';
import jwtDecode from "jwt-decode";
import AuthContext from "./AuthContext";
import EditBudget from "./Budget/EditBudget";
import BudgetMemberDashboard from './Dashboard/BudgetMemberDashboard';
import BudgetMemberView from "./Transaction/BudgetMemberView";
import BudgetOwnerView from "./Transaction/BudgetOwnerView";
import BudgetOwnerDashboard from './Dashboard/BudgetOwnerDashboard';
import EditTransaction from './Transaction/EditTransaction';
import './App.css';
import BudgetMemberManageAuto from './AutoTransaction/BudgetMemberManageAuto';
import BudgetOwnerManageAuto from './AutoTransaction/BudgetOwnerManageAuto';
import Directories from './Directories';

const LOCAL_STORAGE_TOKEN_KEY = "budgetCalcToken";


function App() {

   const [user, setUser] = useState(null);

   const [restoreLoginAttemptCompleted, setRestoreLoginAttemptCompleted] = useState(false);

  
   useEffect( () => {
     const token = localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY);
     if(token){
       login(token);
     }
     setRestoreLoginAttemptCompleted(true);
   },[]);



  const login = (token) => {


    localStorage.setItem(LOCAL_STORAGE_TOKEN_KEY, token);

    const {sub: username,  roles:rolesList} = jwtDecode(token);

    const userRoles = rolesList;

    const user = {
      username,
      userRoles,
      token,
      hasRole(role){
        return this.roles.includes(role);
      }
    };

    console.log(user);

    setUser(user);

    return user;
  }



   const logout = () => {
     setUser(null);

     localStorage.removeItem(LOCAL_STORAGE_TOKEN_KEY);
   };


     const auth ={
     user: user ? {...user} : null,
     login,
     logout
   };


   if (!restoreLoginAttemptCompleted) {
     return null;
   }


  return (
    <div className="darkMode">
      <AuthContext.Provider value={auth}>
      <BrowserRouter>
        <Navbar setUser={setUser}/>
          <Switch>
            <Route path={Directories.HOME}>
              {!user ? <Home/> : (user.userRoles[0].roleName === "Admin" ? <Redirect to = {Directories.OWNERDASHBOARD}/> : <Redirect to ={Directories.MEMBERDASHBOARD}/>)}
            </Route>
            <Route path={Directories.CONTACT}>
              <Contact/>
            </Route>
            <Route path={Directories.LOGIN}>
              {!user ? <Login setUser={setUser}/> : (user.userRoles[0].roleName === "Admin" ? <Redirect to={Directories.OWNERDASHBOARD}/> : <Redirect to={Directories.MEMBERDASHBOARD}/> )}
            </Route>
            <Route path={Directories.SIGNUP}>
              <Signup/>
            </Route>
            <Route path={Directories.CREATEBUDGET}>
              {user ? (user.userRoles[0].roleName === "Member" ? <CreateBudget/>:<Redirect to={Directories.OWNERDASHBOARD}/> ) : <Redirect to={Directories.LOGIN}/>}
            </Route>
            <Route path={Directories.EDITBUDGET}>
              {user ?(user.userRoles[0].roleName === "Admin" ? <EditBudget/>:<Redirect to={Directories.MEMBERDASHBOARD}/> ): <Redirect to={Directories.LOGIN}/>}
            </Route>
            <Route path={Directories.MEMBERVIEW}>
              {user ? (user.userRoles[0].roleName === "Member" ? <BudgetMemberView/>:<Redirect to={Directories.OWNERVIEW}/> ) : <Redirect to={Directories.LOGIN}/>}
            </Route>
            <Route path={Directories.OWNERVIEW}>
              {user ?(user.userRoles[0].roleName === "Admin" ? <BudgetOwnerView/>:<Redirect to={Directories.MEMBERVIEW}/> ) : <Redirect to={Directories.LOGIN}/>}
            </Route>
            <Route path={Directories.ADDTRANSACTION}>
              {user ? <AddTransaction/> : <Redirect to={Directories.LOGIN}/>}
            </Route>
            <Route path={Directories.EDITTRANSACTION}>
              {user ? <EditTransaction/> : <Redirect to={Directories.LOGIN}/>}
            </Route>
            <Route path={Directories.MEMBERDASHBOARD}>
              {user ? (user.userRoles[0].roleName === "Member" ? <BudgetMemberDashboard/>:<Redirect to={Directories.OWNERDASHBOARD}/> ): <Redirect to={Directories.LOGIN}/>}
            </Route>
            <Route path={Directories.OWNERDASHBOARD}>
              {user ? (user.userRoles[0].roleName === "Admin" ? <BudgetOwnerDashboard/>:<Redirect to={Directories.MEMBERDASHBOARD}/> ) : <Redirect to={Directories.LOGIN}/>}
            </Route>
            <Route path={Directories.MEMBERMANAGEAUTO}>
              {user ? (user.userRoles[0].roleName === "Member" ? <BudgetMemberManageAuto/>:<Redirect to={Directories.OWNERMANAGEAUTO}/> ) : <Redirect to={Directories.LOGIN}/>}
            </Route>
            <Route path={Directories.OWNERMANAGEAUTO}>
              {user ? (user.userRoles[0].roleName === "Admin" ? <BudgetOwnerManageAuto/>:<Redirect to={Directories.MEMBERMANAGEAUTO}/> ) : <Redirect to={Directories.LOGIN}/>}
            </Route>


          </Switch>
      
      </BrowserRouter>
      </AuthContext.Provider>
    </div>
  );
}

export default App;

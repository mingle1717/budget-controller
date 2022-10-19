

import { useEffect } from "react";
import { useContext, useState } from "react";
import {useHistory} from "react-router-dom";
import AuthContext from "../AuthContext";
import FormInput from '../FormInput';
import Directories from "../Directories";
function AddMembers(){

    const auth = useContext(AuthContext);
   
    
    const [userToAdd, setUserToAdd] = useState({username:"", budgetId : ""})
    const history = useHistory();
    

    function memberChangeHandler(inputChangeEvent){
        const propertyName = inputChangeEvent.target.name;
        const newValue = inputChangeEvent.target.value;
       const memberCopy = {...userToAdd};
        memberCopy[propertyName] = newValue;
         
         setUserToAdd(memberCopy)
         console.log(memberCopy)
    }
    
    useEffect(() => {
        fetch("http://localhost:8080/api/budget/personal", {
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
        .then(budget => {

            const userToAddCopy = {username: userToAdd.username, budgetId: budget.budgetId}
            setUserToAdd(userToAddCopy)

        })
        .catch(error => {
            console.log(error);
        });
    }, [])


    function handleSubmit(evt){
        
        evt.preventDefault();

        console.log(userToAdd);
        fetch( "http://localhost:8080/api/budget/addmember", {
            method: "POST",
            headers: {
                "Content-Type" : "application/json",
                Authorization : "Bearer " + auth.user.token,
            },
            body: JSON.stringify(userToAdd)
        })
        .then ( response => {
            if ( response.status === 201){
                history.push(Directories.OWNERDASHBOARD)
            }
        })
        .catch (errors => {
            console.log(errors);
        })
    }


    return(
    <div>
         <div className="container">
         <form onSubmit={handleSubmit}>
             <FormInput 
                    inputType={"text"} 
                    identifier={"username"} 
                    labelText={"Username"} 
                    currVal={userToAdd.username} 
                    labelClass={"membersLabel"}
                    onChangeHandler={memberChangeHandler}  
                    className={"form-control"}/>
                <button className="btn btn-primary"onSubmit={handleSubmit}>Add Member</button>    
                    </form>
    </div>
    </div>
    )
} export default AddMembers;


import { useEffect } from "react";
import { useContext, useState } from "react";
import {useHistory} from "react-router-dom";
import AuthContext from "../AuthContext";
import FormInput from '../FormInput';
import Directories from "../Directories";
import "./Member.css"
function AddMembers(){

    const auth = useContext(AuthContext);
   
    
    const [userToAdd, setUserToAdd] = useState({username:"", budgetId : ""})
    const history = useHistory();
    const [errors, setErrors] = useState([]);

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
        .then(async response => {
            if (response.status === 200) {
                
                return response.json();
            } else {
                
                return Promise.reject(await response.json())
            }
        })
        .then(budget => {

            const userToAddCopy = {username: userToAdd.username, budgetId: budget.budgetId}
            setUserToAdd(userToAddCopy)

        })
        .catch (errorList => {
            if( errorList instanceof TypeError){
                setErrors(["Could not connect to api"]);
            } else {
                setErrors( errorList + " ");
            }
        })
    }, [])


    function handleSubmit(evt){
        
        evt.preventDefault();

        console.log(userToAdd);
        const username = userToAdd.username;
        const budgetId = userToAdd.budgetId;
        fetch( "http://localhost:8080/api/budget/addmember", {
            method: "POST",
            headers: {
                "Content-Type" : "application/json",
                Authorization : "Bearer " + auth.user.token,
            },
            body: JSON.stringify([username, budgetId ])
        })
        .then ( async response => {
            if ( response.status === 204){
                history.push(Directories.OWNERDASHBOARD)
            }
            return Promise.reject(await response.json())
        })
        .catch (errorList => {
            if( errorList instanceof TypeError){
                setErrors(["Could not connect to api"]);
            } else {
                setErrors( errorList + " ");
            }
        })
    }


    return(
    <div>
         <div className="container memberContainer">
        <h3>Search by </h3>
         {errors?
            <div className=" myText error" id="messages">{errors}</div>
                : null}
         <form onSubmit={handleSubmit}>
             <FormInput 
                    inputType={"text"} 
                    identifier={"username"} 
                    labelText={"Username"} 
                    currVal={userToAdd.username} 
                    labelClass={"membersLabel"}
                    onChangeHandler={memberChangeHandler}  
                    className={"form-control"}/>
                    <div className="buttonContainer">
                <button className="memberSubmitButton"onSubmit={handleSubmit}>Add Member</button>    
                    </div>
                    </form>
    </div>
    </div>
    )
} export default AddMembers;
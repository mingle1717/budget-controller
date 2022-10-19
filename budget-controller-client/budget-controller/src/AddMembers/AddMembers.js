

import { useEffect } from "react";
import { useContext, useState } from "react";
import AuthContext from "../AuthContext";
import FormInput from '../FormInput';
function AddMembers(){

    const auth = useContext(AuthContext);
   
    
    const [userToAdd, setUserToAdd] = useState({username:"", budgetId : ""})
    

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


    return(
    <div>
         <div className="container">
             <FormInput 
                    inputType={"text"} 
                    identifier={"username"} 
                    labelText={"Add members"} 
                    currVal={userToAdd.username} 
                    labelClass={"membersLabel"}
                    onChangeHandler={memberChangeHandler}  
                    className={"form-control"}/>    
    </div>
    </div>
    )
} export default AddMembers;
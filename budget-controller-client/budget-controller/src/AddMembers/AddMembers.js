
import AddMemberContainer from './AddMemberContainer';
import { useEffect } from "react";
import { useContext, useState } from "react";
import AuthContext from "../AuthContext";
function AddMembers(){

    const auth = useContext(AuthContext);
   
 
    

    
    
    useEffect(() => {
        fetch("http://localhost:8080/api/budget/personal/" + auth.user.username, {
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
            console.log(budget);
        })
        .catch(error => {
            console.log(error);
        });
    }, [])


    return(
    <div>
        <AddMemberContainer/>
    </div>
    )
} export default AddMembers;
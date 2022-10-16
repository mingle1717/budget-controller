
import {useState} from 'react';

import FormInput from '../FormInput';


function AddMemberForm({onMemberChange, member}){



    function memberChangeHandler(inputChangeEvent){
        const propertyName = inputChangeEvent.target.name;
        const newValue = inputChangeEvent.target.value;

        const memberCopy = {...member};

        memberCopy[propertyName] = newValue;


        onMemberChange(memberCopy);
    }



    return(
        <div className="container">
             <FormInput 
                    inputType={"text"} 
                    identifier={"username"} 
                    labelText={"Add members"} 
                    currVal={member.username} 
                    labelClass={"membersLabel"}
                    onChangeHandler={memberChangeHandler}  
                    className={"form-control"}/>

                
          
    </div>
    )
} export default AddMemberForm;

import {useState} from 'react';

import FormInput from '../FormInput';


function AddMemberForm({onMemberChange, member}){



    function inputChangeHandler(inputChangeEvent){
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
                    currVal={""} 
                    labelClass={"membersLabel"}
                    onChangeHandler={inputChangeHandler}  
                    className={"form-control"}/>

                
          
    </div>
    )
} export default AddMemberForm;
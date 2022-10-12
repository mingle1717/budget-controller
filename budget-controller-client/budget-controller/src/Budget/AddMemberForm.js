
import {useState} from 'react';

import FormInput from '../FormInput';


function AddMemberForm(){

    const [member, setMember] = useState();

    const [addedForm,setAddedForm] = useState(false);
    const [addButton, setAddButton] = useState(true);
    const [removeButton, setRemoveButton] = useState(true);

    function inputChangeHandler(inputChangeEvent){
        const propertyName = inputChangeEvent.target.name;
        const newValue = inputChangeEvent.target.value;

        const memberCopy = {...member};

        memberCopy[propertyName] = newValue;

        setMember(memberCopy);
    }


    function showForm(){
        setAddedForm(true);
        setAddButton(false);
        setRemoveButton(true);
    }

    function hideForm(){
        setAddedForm(false);
        setAddButton(true);
        setRemoveButton(false);
    }

    return(
        <div className="container">
             <FormInput 
                    inputType={"text"} 
                    identifier={"member"} 
                    labelText={"Add members"} 
                    currVal={""} 
                    labelClass={"membersLabel"}
                    onChangeHandler={inputChangeHandler}  
                    className={"form-control"}/>

                
{
               addedForm === true ? 
               <div>
                <AddMemberForm/>
                {removeButton === true ? <button onClick = {hideForm} type="button" className="btn btn-primary addButton">-</button> : null} 
                </div> : 
                null
                }
                
                {addButton === true ? <button onClick = {showForm} type="button" className="btn btn-primary addButton">+</button> : null}
                
          
    </div>
    )
} export default AddMemberForm;

import FormInput from "../FormInput"

import {useState} from 'react';




function TransactionForm(){

    const [transaction, setTransaction] = useState();

    function inputChangeHandler(inputChangeEvent){
        const propertyName = inputChangeEvent.target.name;
        const newValue = inputChangeEvent.target.value;
    
        const transactionCopy = {...transaction};
    
        transactionCopy[propertyName] = newValue;
    
        setTransaction(transactionCopy);
    
    }
    return(
        <div className="container">
            <FormInput 
                inputType={"text"} 
                identifier={"moneySpent"} 
                labelText={"Money Spent"} 
                currVal={""} 
                labelClass={"inputLabel"}
                onChangeHandler={inputChangeHandler}
                className={"form-control"}/>
            <FormInput 
                inputType={"select"} 
                identifier={"category"} 
                labelText={"Category"} 
                currVal={""} 
                labelClass={"inputLabel"}
                onChangeHandler={inputChangeHandler}
                className={"form-control"}/>

            <FormInput 
                inputType={"text"} 
                identifier={"description"} 
                labelText={"Description of Transacation"} 
                currVal={""} 
                labelClass={"inputLabel"}
                onChangeHandler={inputChangeHandler}
                className={"form-control"}/>
        </div>
        )
}
export default TransactionForm
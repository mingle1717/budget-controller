
import FormInput from "../FormInput"

import {useState} from 'react';




function TransactionForm({onTransactionChange}){

    const [transaction, setTransaction] = useState();

    function inputChangeHandler(inputChangeEvent){
        const propertyName = inputChangeEvent.target.name;
        const newValue = inputChangeEvent.target.value;
    
        const transactionCopy = {...transaction};
    
        transactionCopy[propertyName] = newValue;

        setTransaction(transactionCopy);
        onTransactionChange(transaction);
    
    }
    return(
        <div className="container">
            <FormInput
                inputType={"text"} 
                identifier={"transactionName"} 
                labelText={"Name"} 
                currVal={""} 
                labelClass={"inputLabel"}
                onChangeHandler={inputChangeHandler}
                className={"form-control"}/>
            <FormInput 
                inputType={"text"} 
                identifier={"transactionAmount"} 
                labelText={"Money Spent"} 
                currVal={""} 
                labelClass={"inputLabel"}
                onChangeHandler={inputChangeHandler}
                className={"form-control"}/>
            <FormInput 
                inputType={"select"} 
                identifier={"categoryName"} 
                labelText={"Category"} 
                currVal={""} 
                labelClass={"inputLabel"}
                onChangeHandler={inputChangeHandler}
                className={"form-control"}/>

            <FormInput 
                inputType={"text"} 
                identifier={"transacationDescription"} 
                labelText={"Description of Transaction"} 
                currVal={""} 
                labelClass={"inputLabel"}
                onChangeHandler={inputChangeHandler}
                className={"form-control"}/>
        </div>
        )
}
export default TransactionForm
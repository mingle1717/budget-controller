
import FormInput from "../FormInput"

import {useState,useContext} from 'react';
import AuthContext from "../AuthContext";




function TransactionForm({onTransactionChange}){
    const auth = useContext(AuthContext);
    const [transaction, setTransaction] = useState({username: auth.user.username, transactionName: "", transactionAmount: 0, categoryName: "", transacationDescription : ""});

    function inputChangeHandler(inputChangeEvent){
        const propertyName = inputChangeEvent.target.name;
        const newValue = inputChangeEvent.target.value;
    
        const transactionCopy = {...transaction};
    
        transactionCopy[propertyName] = newValue;

        setTransaction(transactionCopy);
        onTransactionChange(transactionCopy);
    
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
                inputType={"number"} 
                identifier={"transactionAmount"} 
                labelText={"Money Spent"} 
                currVal={""} 
                labelClass={"inputLabel"}
                onChangeHandler={inputChangeHandler}
                className={"form-control"}/>
            <FormInput 
                inputType={"text"} 
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
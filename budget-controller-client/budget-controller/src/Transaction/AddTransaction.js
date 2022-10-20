
import TransactionForm from "./TransactionForm"
import { Link, useHistory } from "react-router-dom"
import { useContext, useEffect, useState } from "react";
import Directories from "../Directories";
import AuthContext from "../AuthContext";



function AddTransaction() {

    const auth = useContext(AuthContext)
    const [transaction, setTransaction] = useState();
    const [errors, setErrors] = useState([]);
    const history = useHistory();
    

    function handleTransactionChange(updatedTransaction) {
        console.log(updatedTransaction)
        setTransaction(updatedTransaction);
    }


    function handleSubmit(event) {
        event.preventDefault();
        console.log(transaction)
        fetch("http://localhost:8080/api/transaction", {
            method: "POST",
            headers: {
                Authorization : `Bearer ` + auth.user.token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(transaction)
        })
        .then(async response => {
        if (response.status === 201) {
            history.push(Directories.OWNERDASHBOARD);
            } else {
            return Promise.reject(await response.json());
            }
        })
        .catch (errorList => {
            
            if( errorList instanceof TypeError){
                setErrors(["Could not connect to api"]);
            } else {
                setErrors( errorList + " ");
            }
        })
    }
            

    return (
            <div className="container addContainer">
                {errors?
            <div className=" myText error" id="messages">{errors}</div>
                : null}
                <form onSubmit={handleSubmit}>
                    <TransactionForm onTransactionChange={handleTransactionChange} />
                    <div className="buttons">
                        <button type="submit" className="tranSubmitButton ">Add</button>
                        <Link to={Directories.MEMBERDASHBOARD} className="tranCancelButton">Cancel</Link>
                    </div>
                </form>
            </div>
        )
    }
    export default AddTransaction
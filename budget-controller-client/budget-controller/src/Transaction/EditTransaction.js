import TransactionForm from "./TransactionForm";
import { Link, useHistory, useParams } from "react-router-dom";
import AuthContext from "../AuthContext";
import { useContext, useState, useEffect } from "react";
import Directories from "../Directories";
import "./Transaction.css";
function EditTransaction() {
  const editTransactionId = useParams();
  const [budgetId, setBudgetId] = useState();
  const auth = useContext(AuthContext);
  const [transaction, setTransaction] = useState();
  const history = useHistory();
  const [errors, setErrors] = useState([])

  function handleTransactionChange(updatedTransaction) {
    setTransaction(updatedTransaction)
   
}

  useEffect(() => {
    fetch(
      "http://localhost:8080/api/transaction/" + editTransactionId.transactionId,
      {
        method: "GET",
        headers: {
          Authorization: `Bearer ` + auth.user.token,
          "Content-Type": "application/json",
        },
      }
    )
      .then(async response => {
        if (response.status === 200) {
          return response.json();
        } else {
          return Promise.reject(await response.json());
        }
      })
      .then(incomingTransaction => {

        const transactionCopy = incomingTransaction;
        setTransaction(transactionCopy);
        setBudgetId(incomingTransaction.category.budgetId);
        
      })
      .catch (errorList => {
        if( errorList instanceof TypeError){
            setErrors(["Could not connect to api"]);
        } else {
            setErrors( errorList + " ");
        }
    })
  }, []);

  function handleSubmit(event) {
    event.preventDefault();
    console.log(transaction)
    fetch(
      "http://localhost:8080/api/transaction/" + editTransactionId.transactionId,
      {
        method: "PUT",
        headers: {
          Authorization: `Bearer ` + auth.user.token,
          "Content-Type": "application/json",
        },
        body: JSON.stringify(transaction),
      }
    )
      .then(async response => {
        if (response.status === 204) {
          history.push(Directories.OWNERVIEW + "/" + budgetId);
        } else {
          return Promise.reject(response.json())
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
    <div className="container" key="editTransaction">
      <h2 className="editHeader">Edit a Transaction!</h2>
      {errors?
            <div className=" myText error" id="messages">{errors}</div>
                : null}
      <form className="addContainer" onSubmit={handleSubmit}>
        <TransactionForm editedTransaction={transaction} id={editTransactionId.transactionId}
          onTransactionChange={handleTransactionChange}
        />
        <div className="buttons">
          <button type="submit" className="tranSubmitButton">
            Edit
          </button>
          {transaction?
          <Link to={Directories.OWNERVIEW + "/" + budgetId} className="tranCancelButton">
            Cancel
          </Link>: null
}
        </div>
      </form>
    </div>
  );
}

export default EditTransaction;

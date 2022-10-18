import TransactionForm from "./TransactionForm";
import { Link, useHistory, useParams } from "react-router-dom";
import AuthContext from "../AuthContext";
import { useContext, useState, useEffect } from "react";
import Directories from "../Directories";
import "./Transaction.css";
function EditTransaction() {
  const editTransactionId = useParams();

  const auth = useContext(AuthContext);
  const [transaction, setTransaction] = useState();
  const history = useHistory();

  function handleTransactionChange(updatedTransaction) {
    setTransaction(updatedTransaction);
    
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
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else {
          console.log(response);
        }
      })
      .then((incomingTransaction) => {

        const transactionCopy = {...incomingTransaction};
        setTransaction(transactionCopy);
       
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  function handleSubmit(event) {
    event.preventDefault();

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
      .then((response) => {
        if (response.status === 204) {
          history.push(Directories.OWNERVIEW);
        } else {
          console.log(response);
        }
      })
      .catch((error) => {
        console.log(error);
      });
  }

  return (
    <div className="container addContainer" key="editTransaction">
      <form onSubmit={handleSubmit}>
        <TransactionForm editedTransaction={transaction} id={editTransactionId.transactionId}
          onTransactionChange={handleTransactionChange}
        />
        <div className="buttons">
          <button type="submit" className="tranSubmitButton">
            Edit
          </button>
          <Link to={Directories.MEMBERVIEW} className="tranCancelButton">
            Cancel
          </Link>
        </div>
      </form>
    </div>
  );
}

export default EditTransaction;

import {useState} from "react";
import AuthContext from "../AuthContext";

function Transactions() {

    const [transactions, setTransactions] = useState([]);
    const auth = AuthContext;

    function loadAllTransactions() {
        fetch("http://localhost:8080/api/transaction/" + auth.user.username, {
            method: "GET",
            headers: {
                "Content-Type" : "application"
            }
        })
    }
}
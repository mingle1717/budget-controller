import { useContext, useEffect, useState } from "react"
import AuthContext from "../AuthContext"

function AutoTrigger(){

    const auth = useContext(AuthContext);

    const [autoTriggers, setAutoTriggers] = useState
    ([{autoId: 0, userId: 0, cronSchedule: "", paymentAmount: 0, endDate: 0 , categoryId: 0, creationDate: 0, lastExecutionDate: 0}]);

    function loadAutoTriggers(){
        fetch("http://localhost:8080/api/auto/" + auth.user.username, {
            method: "GET",
            headers: {
                Authorization: `Bearer ` + auth.user.token,
                "Content-Type": "application/json"
            },
        })
        .then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                console.log(response);
            }
        })
        .then(autoTriggers => {
            const autoTriggerCopy = autoTriggers;
            setAutoTriggers(autoTriggerCopy);
            console.log(autoTriggers)
        })
        .catch(error =>{
            console.log(error)
        })
    }

    useEffect(
        () => {
            loadAutoTriggers();
        },
        [] );

    return(
        <div className="container">
            <table className="autotrigger container autotrigger">
                
            </table>

        </div>
    )

}

export default AutoTrigger
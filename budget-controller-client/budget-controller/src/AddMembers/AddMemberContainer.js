import {useContext, useState} from 'react';
import AuthContext from '../AuthContext';
import AddMemberForm from './AddMemberForm';


function AddMemberContainer({onMembersChange}){


    const auth = useContext(AuthContext)

    const startingMember = [{memberId : 0, username: auth.user.username}]
    const [members, setMembers] = useState([...startingMember]);

    function memberChangeHandler(member){
        const membersCopy = members.map(m => m.username === member.username ? member : m);

        setMembers(membersCopy);
        onMembersChange(members);

    }

    function addMember(){
        const membersCopy = [...members];
        membersCopy.push({memberId : membersCopy.length , username : ""});
        setMembers(membersCopy);
    }


    function removeMember(){
        if(members.length > 1){
            const membersCopy = [...members];
            membersCopy.pop();
            setMembers(membersCopy);
        }
    }

    return(
        <div className="memberContainer">
            {members.map( m => <AddMemberForm member={m} key={m.memberId} onMemberChange={memberChangeHandler} />)}
            <div className="budgetButtons">
                <button type="button" className="budgetSubmitButton" onClick={addMember}  > + </button>
                <button  type="button" className="budgetCancelButton" onClick={removeMember} > - </button>
            </div>
        </div>
    )
}
export default AddMemberContainer;
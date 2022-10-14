import {useState} from 'react';
import AddMemberForm from './AddMemberForm';

function AddMemberContainer(){

    const startingMembers = [null]

    const [members, setMembers] = useState([startingMembers]);

    function memberChangeHandler(member){
        const membersCopy = members.map(m => m.username === member.username ? member : m);

        setMembers(membersCopy);


    }

    function addMember(){
        const membersCopy = [...members];
        membersCopy.push({memberId : membersCopy.length , username : ""});
        setMembers(membersCopy);
    }
    function removeMember(){
        const membersCopy = [...members];
        membersCopy.pop();
        setMembers(membersCopy);
    }

    return(
        <div className="memberContainer">
            {members.map( m => <AddMemberForm member={m} onMemberChange={memberChangeHandler} />)}
            <button className="btn btn-primary" onClick={addMember}  > + </button>
            <button className="btn btn-danger" onClick={removeMember} > - </button>
        </div>
    )




}
export default AddMemberContainer;
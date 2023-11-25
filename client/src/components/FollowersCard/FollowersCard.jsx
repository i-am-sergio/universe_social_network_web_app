import React, { useEffect, useState, useRef } from "react";
import "./FollowersCard.css";
import FollowersModal from "../FollowersModal/FollowersModal";
import { getAllUser } from "../../api/UserRequests";
import User from "../User/User";
import { useSelector } from "react-redux";
const FollowersCard = ({ location }) => {
  const [modalOpened, setModalOpened] = useState(false);
  const [persons, setPersons] = useState([]);
  const user = useSelector((state) => state.authReducer.authData);
  const isMounted = useRef(true);

  console.log("USER FollowersCard => ", user)
  console.log(user.token)
  useEffect(() => {
    const fetchPersons = async () => {
      isMounted.current = true;
      // const { data } = await getAllUser();
      // console.log("antes de await getAllUser");
      // const response =  await getAllUser();
      // console.log("RESPONSE ALL USERS => ", response);
      // const data = null;
      const response = await getAllUser();
      const data = response.data;
      console.log("DATA ALL USERS => ", data);

      if (isMounted.current) {
        setPersons(data);
      }
    };
    fetchPersons();
    // Limpieza: El componente está a punto de desmontarse
    return () => {
      isMounted.current = false;
    };
    
  }, []);

  

  return (
    <div className="FollowersCard">
      <h3>Personas que quizas conozcas</h3>

      {persons.map((person, id) => {
        if (person.id !== user.id) return <User person={person} key={person.id} />;
      })}
      {!location ? (
        <span onClick={() => setModalOpened(true)}>Show more</span>
      ) : (
        ""
      )}

      <FollowersModal
        modalOpened={modalOpened}
        setModalOpened={setModalOpened}
      />
    </div>
  );
};

export default FollowersCard;

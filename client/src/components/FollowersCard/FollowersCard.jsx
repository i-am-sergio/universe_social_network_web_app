import React, { useEffect, useState, useRef } from "react";
import "./FollowersCard.css";
import FollowersModal from "../FollowersModal/FollowersModal";
import { getAllUser } from "../../api/UserRequests";
import User from "../User/User";
import { useSelector } from "react-redux";
import PropTypes from "prop-types";

const FollowersCard = ({ location }) => {
  const [modalOpened, setModalOpened] = useState(false);
  const [persons, setPersons] = useState([]);
  const user = useSelector((state) => state.authReducer.authData);
  const isMounted = useRef(true);
  useEffect(() => {
    const fetchPersons = async () => {
      isMounted.current = true;
      const response = await getAllUser();
      const data = response.data;
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
      <h3>Personas que quizás conozcas</h3>

      {persons.map((person, id) => {
        if (person.id !== user.id) {
          return <User person={person} key={person.id} />;
        } else {
          return null;
        }
      })}
      {!location ? (
        <button
          style={{
            fontSize: "12px",
            cursor: "pointer",
            textDecoration: "underline",
            border: "none",
            background: "none",
            padding: "0",
          }}
          onClick={() => setModalOpened(true)}
          onKeyDown={(e) => {
            if (e.key === "Enter") {
              setModalOpened(true);
            }
          }}
        >
          Show more
        </button>
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

FollowersCard.propTypes = {
  location: PropTypes.oneOfType([PropTypes.object, PropTypes.string]),
};

export default FollowersCard;

import React from "react";

import {Link} from "react-router-dom";
import "./Navicons.css";
import { AiFillHome } from 'react-icons/ai';
import { BsFillChatDotsFill } from 'react-icons/bs';
import { IoLogOut } from "react-icons/io5";

import { useDispatch } from "react-redux";
import { logout } from "../../actions/AuthActions";

const NavIcons = () => {
    const dispatch = useDispatch();

    const handleLogOut = () => {
        dispatch(logout());
    };
    

    return (
        <div className="navIcons">
            <Link to="../home">
                <AiFillHome className="unsa"/>
            </Link>
            <Link to="../chat" className="messages_icon">
                <BsFillChatDotsFill className="unsa"/>
            </Link>
            
            <div className="div_logout">
                <span className="logout_span">Exit</span>
                <IoLogOut className="unsa" onClick={handleLogOut} />
            </div>
        </div>
    );
};

export default NavIcons;
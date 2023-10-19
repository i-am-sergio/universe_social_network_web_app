import React from "react";

import {Link} from "react-router-dom";
import "./Navicons.css";
import { AiFillHome, AiFillBell } from 'react-icons/ai';
import {BsFillGearFill, BsFillChatDotsFill} from 'react-icons/bs';

const NavIcons = () => {
    return (
        <div className="navIcons">
            <Link to="../home">
                <AiFillHome className="unsa"/>
            </Link>
            <BsFillGearFill className="unsa"/>
            <AiFillBell className="unsa"/>
            <Link to="../chat">
                <BsFillChatDotsFill className="unsa"/>
            </Link>
        </div>
    );
};

export default NavIcons;
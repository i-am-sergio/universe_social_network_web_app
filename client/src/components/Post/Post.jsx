import React, { useState, useEffect } from "react";
import PropTypes from "prop-types";
import "./Post.css";
import Comment from "../../img/comment.png";
import Share from "../../img/share.png";
import Heart from "../../img/like.png";
import NotLike from "../../img/notlike.png";
import { likePost } from "../../api/PostsRequests";
import { useSelector } from "react-redux";
import { format } from "timeago.js";

const Post = ({ data }) => {

  const user = useSelector((state) => state.authReducer.authData);
  const [liked, setLiked] = useState(data.likes?.includes(user._id));
  const [likes, setLikes] = useState(data.likes ? data.likes.length : 0);

  const [createdAt, setCreatedAt] = useState(null);

  useEffect(() => {
    if (data.createdAt) {
      const formattedDate = format(data.createdAt);
      setCreatedAt(formattedDate);
    }
  }, [data.createdAt]);

  const handleLike = () => {
    likePost(data.id, user.id);
    setLiked((prev) => !prev);
    liked ? setLikes((prev) => prev - 1) : setLikes((prev) => prev + 1);
  };

  return (
    <div className="Post">
      <img
        src={data.image ? process.env.REACT_APP_PUBLIC_FOLDER + data.image : ""}
        alt=""
      />

      <div className="postReact">
        <button onClick={handleLike}>
          <img
            src={liked ? Heart : NotLike}
            alt=""
            style={{ cursor: "pointer" }}
          />
        </button>
        
        <img src={Comment} alt="" />
        <img src={Share} alt="" />
      </div>
      <div className="detail">
        <span>
          <b>{data.name} </b>
        </span>
        <span>{data.desc}</span>
      </div>
      <div style={{ display: "flex", justifyContent: "space-between" }}>
        <span style={{ color: "var(--gray)", fontSize: "12px" }}>
          {likes} likes
        </span>
        <span style={{ color: "var(--gray)", fontSize: "12px" }}>
          {createdAt}
        </span>
      </div>
    </div>
  );
};

Post.propTypes = {
  data: PropTypes.shape({
    id: PropTypes.string.isRequired,
    name: PropTypes.string.isRequired,
    desc: PropTypes.string.isRequired,
    image: PropTypes.string,
    likes: PropTypes.arrayOf(PropTypes.string),
    createdAt: PropTypes.string.isRequired,
  }).isRequired,
};

export default Post;

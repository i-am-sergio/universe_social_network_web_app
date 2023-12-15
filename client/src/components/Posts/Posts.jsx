import React, { useEffect } from "react";
import { getTimelinePosts } from "../../actions/PostsAction";
import Post from "../Post/Post";
import { useSelector, useDispatch } from "react-redux";
import "./Posts.css";
import { useParams } from "react-router-dom";

const Posts = () => {
  const params = useParams();
  const dispatch = useDispatch();
  const user = useSelector((state) => state.authReducer.authData);
  let { posts, loading } = useSelector((state) => state.postReducer);

  useEffect(() => {
    if (user) {
      dispatch(getTimelinePosts(user.id));
    }
  }, [user]);

  if (!posts){
    return <div>No Posts</div>;
  } 
  let postsToDisplay = params.id
    ? posts.data.filter((post) => post.userId === params.id)
    : posts.data;
  return (
    <div className="Posts">
      {loading ? (
        <div>Fetching posts....</div>
      ) : (
        postsToDisplay.map((post) => {
          return <Post data={post} key={post.id} />;
        })
      )}
    </div>
  );
};

export default Posts;

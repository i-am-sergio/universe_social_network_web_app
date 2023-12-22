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
  }, [user, dispatch]);

  if (!posts) {
    return <div>No Posts</div>;
  }
  const postsWithUniqueIds = posts.data.map((post, index) => ({
    ...post,
    id: index + 1,
  }));
  let postsToDisplay = params.id
    ? postsWithUniqueIds.filter((post) => post.userId === params.id)
    : postsWithUniqueIds;
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

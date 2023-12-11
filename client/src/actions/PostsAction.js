import * as PostsApi from "../api/PostsRequests";

export const getTimelinePosts = (id) => async (dispatch) => {
  dispatch({ type: "RETREIVING_START" });
  try {
    const data = await PostsApi.getTimelinePosts(id);
    // console.log("Status Code:", data.status);
    // console.log("data ---> ",data);
    dispatch({ type: "RETREIVING_SUCCESS", data: data });
  } catch (error) {
    console.log("ERRRRRORRR => ", error);
    dispatch({ type: "RETREIVING_FAIL" });
  }
};

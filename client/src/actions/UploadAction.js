import * as UploadApi from "../api/UploadRequest";

export const uploadImage = (data) => async (dispatch) => {
  try {
    console.log("dataUPload => ", data);
    const response = await UploadApi.uploadImage(data);
    console.log("response => ", response);
  } catch (error) {
    console.log(error);
  }
};

export const uploadPost = (data) => async (dispatch) => {
  dispatch({ type: "UPLOAD_START" });
  try {
    console.log("datapostsss => ", data);
    const newPost = await UploadApi.uploadPost(data);
    console.log("Funciona!!", newPost);
    dispatch({ type: "UPLOAD_SUCCESS", data: newPost.data });
  } catch (error) {
    console.log("ERROR => ", error);
    dispatch({ type: "UPLOAD_FAIL" });
  }
};

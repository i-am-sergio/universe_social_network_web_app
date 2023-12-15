import React, { useState, useRef } from "react";
import "./PostShare.css";
import {
  UilScenery,
  UilPlayCircle,
  UilLocationPoint,
  UilSchedule,
  UilTimes,
} from "@iconscout/react-unicons";
import { useDispatch, useSelector } from "react-redux";
import { uploadImage, uploadPost } from "../../actions/UploadAction";

const PostShare = () => {
  const dispatch = useDispatch();
  const user = useSelector((state) => state.authReducer.authData);
  const loading = useSelector((state) => state.postReducer.uploading);
  const [image, setImage] = useState(null);
  const desc = useRef();
  const serverPublic = process.env.REACT_APP_PUBLIC_FOLDER;
  const imageRef = useRef();

  // Verificar si user está definido
  if (!user) {
    return <div>Loading...</div>;
  }

  // handle Image Change
  // if (event.target.files && event.target.files[0]) {
  const onImageChange = (event) => {
    console.log("onImageChange called");
    if (event.target.files?.[0]) {
      let img = event.target.files[0];
      console.log("Image selected:", img);
      setImage(img);
    }
  };

  // handle post upload
  const handleUpload = async (e) => {
    e.preventDefault();
    const newPost = {
      userId: user.id,
      desc: desc.current.value,
    };
    const formData = new FormData();
    try {
      if (image) {
        const fileName = Date.now() + image.name;
        formData.append("name", fileName);
        formData.append("file", image);
        dispatch(uploadImage(formData))
          .then((response) => {
            newPost.image = response;
            dispatch(uploadPost(newPost));
            resetShare();
          })
          .catch((error) => {
            console.error("Error:", error);
          });
      } else {
        dispatch(uploadPost(newPost));
        resetShare();
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

  // Reset Post Share
  const resetShare = () => {
    setImage(null);
    desc.current.value = "";
  };
  return (
    <div className="PostShare">
      <img
        src={
          user.profilePicture
            ? serverPublic + user.profilePicture
            : serverPublic + "defaultProfile.png"
        }
        alt="Profile"
      />
      <div>
        <input
          type="text"
          placeholder="What's happening?"
          required
          ref={desc}
        />
        <div className="postOptions">
          <div
            className="option"
            style={{ color: "var(--photo)", cursor: "pointer" }}
            onClick={() => imageRef.current.click()}
            onKeyDown={(e) => {
              if (e.key === "Enter") {
                imageRef.current.click();
              }
            }}
          >
            <UilScenery />
            Photo
          </div>

          <div className="option" style={{ color: "var(--video)" }}>
            <UilPlayCircle />
            Video
          </div>
          <div className="option" style={{ color: "var(--location)" }}>
            <UilLocationPoint />
            Location
          </div>
          <div className="option" style={{ color: "var(--shedule)" }}>
            <UilSchedule />
            Shedule
          </div>
          <button
            className="button ps-button"
            onClick={handleUpload}
            disabled={loading}
          >
            {loading ? "uploading" : "Share"}
          </button>

          <div style={{ display: "none" }}>
            <input type="file" ref={imageRef} onChange={onImageChange} />
          </div>
        </div>

        {image && (
          <div className="previewImage">
            <UilTimes onClick={() => setImage(null)} />
            <img src={URL.createObjectURL(image)} alt="preview" />
          </div>
        )}
      </div>
    </div>
  );
};

export default PostShare;

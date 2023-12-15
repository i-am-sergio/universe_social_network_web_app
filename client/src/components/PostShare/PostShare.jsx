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
  const onImageChange = (event) => {
    if (event.target.files?.[0]) {
      setImage(event.target.files[0]);
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
    if (image) {
      const fileName = Date.now() + image.name;
      formData.append("name", fileName);
      formData.append("file", image);
      newPost.image = fileName;
      try {
        dispatch(uploadImage(formData));
      } catch (err) {
        console.log(err);
      }
    }

    dispatch(uploadPost(newPost));
    resetShare();
  };

  const OptionButton = ({ icon, color, onClick, children }) => {
    return (
      <button className="option" style={{ color, cursor: "pointer" }} onClick={onClick}>
        {icon}
        {children}
      </button>
    );
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
          <OptionButton icon={<UilScenery />} color="var(--photo)" onClick={() => imageRef.current.click()}>
            Photo
          </OptionButton>

          <OptionButton icon={<UilPlayCircle />} color="var(--video)">
            Video
          </OptionButton>
          
          <OptionButton icon={<UilLocationPoint />} color="var(--location)">
            Location
          </OptionButton>
          
          <OptionButton icon={<UilSchedule />} color="var(--shedule)">
            Schedule
          </OptionButton>
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

import * as UserApi from "../api/UserRequests";
import * as ChatApi from "../api/ChatRequests"

export const updateUser=(id, formData)=> async(dispatch)=> {
    dispatch({type: "UPDATING_START"})
    try{
        const {data} = await UserApi.updateUser(id, formData);
        console.log("Action ko receive hoa hy ye : ",data)
        dispatch({type: "UPDATING_SUCCESS", data: data})
    }   
    catch(error){
        dispatch({type: "UPDATING_FAIL"})
    }
}

export const followUser = (id, data)=> async(dispatch)=> {
    dispatch({type: "FOLLOW_USER", data: id})
    const result = await ChatApi.findChat(data._id,id);
    UserApi.followUser(id, data)
    console.log(result);
    if(result.data == null){
        ChatApi.createChat(data._id,id);
    }
}

export const unfollowUser = (id, data)=> async (dispatch)=> {
    dispatch({type: "UNFOLLOW_USER", data: id})
    UserApi.unfollowUser(id, data)
    const result = await ChatApi.findChat(data._id,id);
    console.log(result.data._id);
    ChatApi.deleteChat(result.data._id);
}
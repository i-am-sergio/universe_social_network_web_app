const chatReducer = (state = { chatUsers: [], loading: false, error: false }, action) => {
    switch (action.type) {
        case "SAVE_USER":
            return { ...state, chatUsers: [...state.chatUsers, action.data] };
        case "___":
            return { chatUsers: [], loading: false, error: false };
        default:
            return state;
    }
};

export default chatReducer;

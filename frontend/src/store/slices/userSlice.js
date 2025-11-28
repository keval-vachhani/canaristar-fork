import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";

const BACKEND_URL = import.meta.env.VITE_BASE_URL;

const userSlice = createSlice({
  name: "user",
  initialState: {
    loading: false,
    error: null,
    message: null,
    user: null,
    userId: null,
  },
  reducers: {
    getUserRequest(state) {
      state.loading = true;
      state.error = null;
      state.message = null;
    },
    getUserSuccess(state, action) {
      state.loading = false;
      state.user = action.payload;
    },
    getUserFailed(state, action) {
      state.loading = false;
      state.error = action.payload;
    },

    getUserIdRequest(state) {
      state.loading = true;
      state.error = null;
      state.message = null;
    },
    getUserIdSuccess(state, action) {
      state.loading = false;
      state.userId = action.payload;
    },
    getUserIdFailed(state, action) {
      state.loading = false;
      state.error = action.payload;
    },

    deleteUserRequest(state) {
      state.loading = true;
      state.error = null;
      state.message = null;
    },
    deleteUserSuccess(state, action) {
      state.loading = false;
      state.message = action.payload;
      state.user = null;
    },
    deleteUserFailed(state, action) {
      state.loading = false;
      state.error = action.payload;
    },

    updateUserRequest(state) {
      state.loading = true;
      state.error = null;
      state.message = null;
    },
    updateUserSuccess(state, action) {
      state.loading = false;
      state.user = action.payload;
    },
    updateUserFailed(state, action) {
      state.loading = false;
      state.error = action.payload;
    },

    resetPasswordRequest(state) {
      state.loading = true;
      state.error = null;
      state.message = null;
    },
    resetPasswordSuccess(state, action) {
      state.loading = false;
      state.message = action.payload;
    },
    resetPasswordFailed(state, action) {
      state.loading = false;
      state.error = action.payload;
    },

    verifyPasswordRequest(state) {
      state.loading = true;
      state.error = null;
      state.message = null;
    },
    verifyPasswordSuccess(state, action) {
      state.loading = false;
      state.message = action.payload;
    },
    verifyPasswordFailed(state, action) {
      state.loading = false;
      state.error = action.payload;
    },

    resetUserSlice(state) {
      state.loading = false;
      state.error = null;
      state.message = null;
    },
  },
});

export const resetUserSlice = () => (dispatch) => {
  dispatch(userSlice.actions.resetUserSlice());
};

export const getUserById = (id) => async (dispatch) => {
  dispatch(userSlice.actions.getUserRequest());
  await axios
    .get(`${BACKEND_URL}/api/user/${id}`, { withCredentials: true })
    .then((res) => {
      dispatch(userSlice.actions.getUserSuccess(res.data));
    })
    .catch((error) => {
      dispatch(
        userSlice.actions.getUserFailed(
          error.response?.data?.message || error.message
        )
      );
    });
};

export const getUserIdByEmail = (email) => async (dispatch) => {
  dispatch(userSlice.actions.getUserIdRequest());
  await axios
    .get(`${BACKEND_URL}/api/user/get-id?email=${email}`, {
      withCredentials: true,
      headers: { "Content-Type": "application/json" },
    })
    .then((res) => {
      dispatch(userSlice.actions.getUserIdSuccess(res.data));
    })
    .catch((error) => {
      dispatch(
        userSlice.actions.getUserIdFailed(
          error.response?.data?.message || error.message
        )
      );
    });
};

export const deleteUser = (id) => async (dispatch) => {
  dispatch(userSlice.actions.deleteUserRequest());
  await axios
    .delete(`${BACKEND_URL}/api/user/${id}`, { withCredentials: true })
    .then((res) => {
      dispatch(userSlice.actions.deleteUserSuccess(res.data));
    })
    .catch((error) => {
      dispatch(
        userSlice.actions.deleteUserFailed(
          error.response?.data?.message || error.message
        )
      );
    });
};

export const updateUser = (userData) => async (dispatch) => {
  dispatch(userSlice.actions.updateUserRequest());
  await axios
    .put(`${BACKEND_URL}/api/user`, userData, {
      withCredentials: true,
      headers: { "Content-Type": "application/json" },
    })
    .then((res) => {
      dispatch(userSlice.actions.updateUserSuccess(res.data));
    })
    .catch((error) => {
      dispatch(
        userSlice.actions.updateUserFailed(
          error.response?.data?.message || error.message
        )
      );
    });
};

export const resetPassword = (data) => async (dispatch) => {
  dispatch(userSlice.actions.resetPasswordRequest());
  await axios
    .post(`${BACKEND_URL}/api/user/password/reset`, data, {
      withCredentials: true,
      headers: { "Content-Type": "application/json" },
    })
    .then((res) => {
      dispatch(userSlice.actions.resetPasswordSuccess(res.data));
    })
    .catch((error) => {
      dispatch(
        userSlice.actions.resetPasswordFailed(
          error.response?.data?.message || error.message
        )
      );
    });
};

export const verifyPassword = (email, otp) => async (dispatch) => {
  dispatch(userSlice.actions.verifyPasswordRequest());
  await axios
    .post(
      `${BACKEND_URL}/api/user/password/verify?email=${email}&otp=${otp}`,
      {},
      {
        withCredentials: true,
        headers: { "Content-Type": "application/json" },
      }
    )
    .then((res) => {
      dispatch(userSlice.actions.verifyPasswordSuccess(res.data));
      console.log("verified");
    })
    .catch((error) => {
      dispatch(
        userSlice.actions.verifyPasswordFailed(
          error.response?.data?.message || error.message
        )
      );
    });
};

export default userSlice.reducer;

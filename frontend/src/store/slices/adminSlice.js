import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";

const BACKEND_URL = import.meta.env.VITE_BASE_URL;

const adminSlice = createSlice({
  name: "admin",
  initialState: {
    loading: false,
    error: null,
    message: null,
    users: [],
  },
  reducers: {
    makeAdminRequest(state) {
      state.loading = true;
      state.error = null;
      state.message = null;
    },
    makeAdminSuccess(state, action) {
      state.loading = false;
      state.message = action.payload;
    },
    makeAdminFailed(state, action) {
      state.loading = false;
      state.error = action.payload;
    },

    createAdminRequest(state) {
      state.loading = true;
      state.error = null;
      state.message = null;
    },
    createAdminSuccess(state, action) {
      state.loading = false;
      state.message = action.payload;
    },
    createAdminFailed(state, action) {
      state.loading = false;
      state.error = action.payload;
    },

    getAllUsersRequest(state) {
      state.loading = true;
      state.error = null;
      state.message = null;
    },
    getAllUsersSuccess(state, action) {
      state.loading = false;
      state.users = action.payload;
    },
    getAllUsersFailed(state, action) {
      state.loading = false;
      state.error = action.payload;
    },

    resetAdminSlice(state) {
      state.loading = false;
      state.error = null;
      state.message = null;
    },
  },
});

export const resetAdminSlice = () => (dispatch) => {
  dispatch(adminSlice.actions.resetAdminSlice());
};

export const makeAdmin = (data) => async (dispatch) => {
  dispatch(adminSlice.actions.makeAdminRequest());
  await axios
    .post(`${BACKEND_URL}/api/admin/make-admin`, data, {
      withCredentials: true,
      headers: { "Content-Type": "application/json" },
    })
    .then((res) => {
      dispatch(adminSlice.actions.makeAdminSuccess(res.data));
    })
    .catch((error) => {
      dispatch(
        adminSlice.actions.makeAdminFailed(
          error.response?.data?.message || error.message
        )
      );
    });
};

export const createAdmin = (email) => async (dispatch) => {
  dispatch(adminSlice.actions.createAdminRequest());
  await axios
    .post(`${BACKEND_URL}/api/admin/create-admin`, email, {
      withCredentials: true,
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((res) => {
      dispatch(adminSlice.actions.createAdminSuccess(res.data));
    })
    .catch((error) => {
      dispatch(
        adminSlice.actions.createAdminFailed(
          error.response?.data?.message || error.message
        )
      );
    });
};

export const getUsers =
  (page = 1, size = 20) =>
  async (dispatch) => {
    dispatch(adminSlice.actions.getAllUsersRequest());

    await axios
      .get(`${BACKEND_URL}/api/admin/users`, {
        params: { page, size },
        withCredentials: true,
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((res) => {
        dispatch(adminSlice.actions.getAllUsersSuccess(res.data));
      })
      .catch((error) => {
        dispatch(
          adminSlice.actions.getAllUsersFailed(
            error.response?.data?.message || error.message
          )
        );
      });
  };

export const getAllUsers = () => async (dispatch) => {
  dispatch(adminSlice.actions.getAllUsersRequest());
  await axios
    .get(`${BACKEND_URL}/api/admin/all`, {
      withCredentials: true,
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((res) => {
      dispatch(adminSlice.actions.getAllUsersSuccess(res.data));
    })
    .catch((error) => {
      dispatch(
        adminSlice.actions.getAllUsersFailed(
          error.response?.data?.message || error.message
        )
      );
    });
};

export default adminSlice.reducer;

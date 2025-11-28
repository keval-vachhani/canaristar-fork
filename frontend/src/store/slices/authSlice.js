import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";

const BACKEND_URL = import.meta.env.VITE_BASE_URL;

const authSlice = createSlice({
  name: "auth",
  initialState: {
    loading: false,
    error: null,
    message: null,
    user: null,
    userId: JSON.parse(localStorage.getItem("userId")) || null,
    isAuthenticated: localStorage.getItem("isAuthenticated") === "true",
  },
  reducers: {
    signupRequest(state) {
      state.loading = true;
      state.error = null;
      state.message = null;
    },
    signupSuccess(state, action) {
      state.loading = false;
      state.message = action.payload;
    },
    signupFailed(state, action) {
      state.loading = false;
      state.error = action.payload;
    },

    verifyOtpRequest(state) {
      state.loading = true;
      state.error = null;
      state.message = null;
    },
    verifyOtpSuccess(state, action) {
      state.loading = false;
      state.message = action.payload.message;
      state.user = action.payload.user || null;
      state.isAuthenticated = true;
    },
    verifyOtpFailed(state, action) {
      state.loading = false;
      state.error = action.payload;
    },

    resendOtpRequest(state) {
      state.loading = true;
      state.error = null;
      state.message = null;
    },

    resendOtpSuccess(state, action) {
      state.loading = false;
      state.message = action.payload;
    },

    resendOtpFailed(state, action) {
      state.loading = false;
      state.error = action.payload;
    },

    signinRequest(state) {
      state.loading = true;
      state.error = null;
      state.message = null;
    },
    signinSuccess(state, action) {
      state.loading = false;
      state.message = action.payload.message;
      state.user = action.payload.token || null;
      state.isAuthenticated = true;

      localStorage.setItem("isAuthenticated", "true");
      localStorage.setItem("userId", JSON.stringify(action.payload.token));
    },
    signinFailed(state, action) {
      state.loading = false;
      state.error = action.payload;
    },

    signoutSuccess(state) {
      state.user = null;
      state.isAuthenticated = false;
      localStorage.removeItem("isAuthenticated");
      localStorage.removeItem("userId");
    },

    resetAuthSlice(state) {
      state.loading = false;
      state.error = null;
      state.message = null;
    },
  },
});

export const resetAuthSlice = () => (dispatch) => {
  dispatch(authSlice.actions.resetAuthSlice());
};

export const signup = (userData) => async (dispatch) => {
  dispatch(authSlice.actions.signupRequest());
  await axios
    .post(`${BACKEND_URL}/auth/signup`, userData, {
      withCredentials: true,
      headers: { "Content-Type": "application/json" },
    })
    .then((res) => {
      dispatch(authSlice.actions.signupSuccess(res.data));
    })
    .catch((error) => {
      dispatch(
        authSlice.actions.signupFailed(
          error.response?.data?.message || error.message
        )
      );
    });
};

export const verifyOtp = (email, otp) => async (dispatch) => {
  dispatch(authSlice.actions.verifyOtpRequest());
  await axios
    .post(
      `${BACKEND_URL}/auth/verify-otp`,
      { email, otp },
      {
        withCredentials: true,
        headers: { "Content-Type": "application/json" },
      }
    )
    .then((res) => {
      dispatch(authSlice.actions.verifyOtpSuccess(res.data));
    })
    .catch((error) => {
      dispatch(
        authSlice.actions.verifyOtpFailed(
          error.response?.data?.message || error.message
        )
      );
    });
};

export const resendOtp = (email) => async (dispatch) => {
  dispatch(authSlice.actions.resendOtpRequest());
  await axios
    .post(`${BACKEND_URL}/auth/resend-otp`, email, {
      withCredentials: true,
      headers: { "Content-Type": "application/json" },
    })
    .then((res) => {
      dispatch(authSlice.actions.resendOtpSuccess(res.data.message));
    })
    .catch((error) => {
      dispatch(
        authSlice.actions.resendOtpFailed(
          error.response?.data?.message || error.message
        )
      );
    });
};

export const signin = (credentials) => async (dispatch) => {
  dispatch(authSlice.actions.signinRequest());
  await axios
    .post(`${BACKEND_URL}/auth/signin`, credentials, {
      withCredentials: true,
      headers: { "Content-Type": "application/json" },
    })
    .then((res) => {
      dispatch(authSlice.actions.signinSuccess(res.data));
    })
    .catch((error) => {
      dispatch(
        authSlice.actions.signinFailed(
          error.response?.data?.message || error.message
        )
      );
    });
};

export const signout = () => async (dispatch) => {
  try {
    await axios.post(
      `${BACKEND_URL}/auth/signout`,
      {},
      { withCredentials: true }
    );
    dispatch(authSlice.actions.signoutSuccess());
  } catch (error) {
    console.error("Logout failed", error);
  }
};

export default authSlice.reducer;

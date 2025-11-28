import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";

const BACKEND_URL = import.meta.env.VITE_BASE_URL;

const cartSlice = createSlice({
  name: "cart",
  initialState: {
    cart: null,
    loading: false,
    error: null,
    message: null,
  },

  reducers: {
    request(state) {
      state.loading = true;
      state.error = null;
      state.message = null;
    },

    success(state, action) {
      state.loading = false;
      state.cart = action.payload;
    },

    successMessage(state, action) {
      state.loading = false;
      state.message = action.payload;
    },

    failed(state, action) {
      state.loading = false;
      state.error = action.payload;
    },

    resetCartSlice(state) {
      state.loading = false;
      state.error = null;
      state.message = null;
    },
  },
});

export const getCartById = (id) => async (dispatch) => {
  dispatch(cartSlice.actions.request());
  try {
    const res = await axios.get(`${BACKEND_URL}/api/cart/${id}`, {
      withCredentials: true,
    });
    dispatch(cartSlice.actions.success(res.data));
  } catch (err) {
    dispatch(
      cartSlice.actions.failed(err.response?.data || "Failed to fetch cart")
    );
  }
};

export const getCartByUserId = (userId) => async (dispatch) => {
  dispatch(cartSlice.actions.request());
  try {
    const res = await axios.get(`${BACKEND_URL}/api/cart/user/${userId}`, {
      withCredentials: true,
    });
    dispatch(cartSlice.actions.success(res.data));
  } catch (err) {
    dispatch(
      cartSlice.actions.failed(err.response?.data || "Failed to fetch cart")
    );
  }
};

export const addItemToCart =
  (userId, productId, quantity) => async (dispatch) => {
    dispatch(cartSlice.actions.request());
    try {
      const res = await axios.post(
        `${BACKEND_URL}/api/cart/add?userId=${userId}&productId=${productId}&quantity=${quantity}`,
        null,
        {
          // params: { userId, productId, quantity },
          withCredentials: true,
        }
      );
      dispatch(cartSlice.actions.success(res.data));
    } catch (err) {
      dispatch(
        cartSlice.actions.failed(err.response?.data || "Failed to add item")
      );
    }
  };

export const updateCartItem =
  (userId, productId, quantity) => async (dispatch) => {
    dispatch(cartSlice.actions.request());
    try {
      const res = await axios.put(`${BACKEND_URL}/api/cart/update`, null, {
        params: { userId, productId, quantity },
        withCredentials: true,
      });
      dispatch(cartSlice.actions.success(res.data));
    } catch (err) {
      dispatch(
        cartSlice.actions.failed(err.response?.data || "Failed to update item")
      );
    }
  };

export const removeItemFromCart = (userId, productId) => async (dispatch) => {
  dispatch(cartSlice.actions.request());
  try {
    const res = await axios.delete(`${BACKEND_URL}/api/cart/remove`, {
      params: { userId, productId },
      withCredentials: true,
    });
    dispatch(cartSlice.actions.success(res.data));
  } catch (err) {
    dispatch(
      cartSlice.actions.failed(err.response?.data || "Failed to remove item")
    );
  }
};

export const clearCart = (userId) => async (dispatch) => {
  dispatch(cartSlice.actions.request());
  try {
    const res = await axios.delete(`${BACKEND_URL}/api/cart/clear/${userId}`, {
      withCredentials: true,
    });
    dispatch(cartSlice.actions.success(res.data));
  } catch (err) {
    dispatch(
      cartSlice.actions.failed(err.response?.data || "Failed to clear cart")
    );
  }
};

export const deleteCart = (userId) => async (dispatch) => {
  dispatch(cartSlice.actions.request());
  try {
    const res = await axios.delete(`${BACKEND_URL}/api/cart/delete/${userId}`, {
      withCredentials: true,
    });
    dispatch(cartSlice.actions.successMessage(res.data));
  } catch (err) {
    dispatch(
      cartSlice.actions.failed(err.response?.data || "Failed to delete cart")
    );
  }
};

export const { resetCartSlice } = cartSlice.actions;
export default cartSlice.reducer;

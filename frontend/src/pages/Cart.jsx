import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  clearCart,
  getCartByUserId,
  removeItemFromCart,
  updateCartItem,
} from "../store/slices/cartSlice";

const Cart = () => {
  const dispatch = useDispatch();
  const [total, setTotal] = useState(0);

  const { cart, loading } = useSelector((state) => state.cart);
  const { userId } = useSelector((state) => state.auth);

  useEffect(() => {
    if (userId) dispatch(getCartByUserId(userId));
    console.log(cart);
  }, [userId]);

  const increaseQty = (item) => {
    dispatch(updateCartItem(userId, item.productId, item.quantity + 1));
  };

  const decreaseQty = (item) => {
    if (item.quantity > 1) {
      dispatch(updateCartItem(userId, item.productId, item.quantity - 1));
    }
  };

  const removeItem = (item) => {
    dispatch(removeItemFromCart(userId, item.productId));
  };

  useEffect(() => {
    const calculatedTotal =
      cart?.cartItems?.reduce((acc, item) => acc + item.quantity, 0) || 0;

    setTotal(calculatedTotal);
  }, [cart, userId]);

  return (
    <div className="min-h-screen pt-20 p-4 bg-gray-50">
      <h1 className="text-2xl font-semibold mb-4">Your Cart</h1>

      {/* Loading */}
      {loading && (
        <div className="fixed top-30 left-10 text-red-500">Loading...</div>
      )}

      {/* Empty cart */}
      {!loading && cart?.cartItems?.length === 0 && (
        <div className="text-center text-red-500 py-10 text-lg">
          Your cart is empty.
        </div>
      )}

      {/* Cart Items */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* ITEMS */}
        <div className="lg:col-span-2 space-y-4">
          {cart?.cartItems?.map((item) => (
            <div
              key={item?.productId}
              className="bg-white rounded-xl shadow p-4 flex flex-col sm:flex-row gap-4"
            >
              <img
                src={item?.productImage || "/placeholder.png"}
                alt={item?.productName}
                className="w-full sm:w-32 h-32 object-cover rounded-lg"
              />

              <div className="flex flex-col justify-between flex-grow">
                <div>
                  <h2 className="text-lg font-medium">{item?.productName}</h2>
                  <p className="text-gray-600">
                    ₹{item?.productPrice?.toLocaleString()}
                  </p>
                </div>

                {/* Quantity Controls */}
                <div className="flex items-center gap-3 mt-3">
                  <button
                    onClick={() => decreaseQty(item)}
                    className="px-3 py-1 bg-gray-200 rounded-md text-lg"
                  >
                    -
                  </button>
                  <span className="font-semibold">{item?.quantity}</span>
                  <button
                    onClick={() => increaseQty(item)}
                    className="px-3 py-1 bg-gray-200 rounded-md text-lg"
                  >
                    +
                  </button>

                  {/* Remove Item */}
                  <button
                    onClick={() => removeItem(item)}
                    className="ml-auto text-red-600 font-semibold"
                  >
                    Remove
                  </button>
                </div>
              </div>
            </div>
          ))}

          {/* Clear Cart */}
          {cart?.cartItems?.length > 0 && (
            <button
              onClick={() => dispatch(clearCart(userId))}
              className="w-full py-3 mt-4 bg-red-500 text-white rounded-lg font-semibold"
            >
              Clear Cart
            </button>
          )}
        </div>

        {/* SUMMARY */}
        {cart?.cartItems?.length > 0 && (
          <div className="bg-white shadow rounded-xl p-6 h-fit">
            <h2 className="text-xl font-semibold mb-4">Order Summary</h2>

            <div className="flex justify-between mb-2">
              <p className="text-gray-600">Subtotal</p>
              <p className="font-medium">₹{total.toLocaleString()}</p>
            </div>

            <div className="flex justify-between mb-2">
              <p className="text-gray-600">Delivery</p>
              <p className="font-medium text-green-600">Free</p>
            </div>

            <hr className="my-3" />

            <div className="flex justify-between text-lg font-semibold mb-4">
              <p>Total</p>
              <p>₹{total.toLocaleString()}</p>
            </div>

            <button className="w-full py-3 bg-blue-600 text-white rounded-lg font-semibold">
              Proceed to Checkout
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default Cart;

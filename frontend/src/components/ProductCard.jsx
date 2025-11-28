import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { toast } from "react-toastify";
import { addItemToCart, resetCartSlice } from "../store/slices/cartSlice";

const ProductCard = ({ product }) => {
  const dispatch = useDispatch();
  const { userId, isAuthenticated } = useSelector((state) => state.auth);
  const { cart, loading, message, error } = useSelector((state) => state.cart);

  const handleAddToCart = () => {
    if (!isAuthenticated || !userId) {
      toast.warning("Please login to add products to cart");
      return;
    }
    dispatch(addItemToCart(userId, product.id, 1));
  };

  useEffect(() => {
    if (message) {
      toast.success(message);
      dispatch(resetCartSlice());
    }
    if (error) {
      toast.error(error);
      dispatch(resetCartSlice());
    }
  }, [dispatch, message, error]);

  return (
    <div className="p-5 border border-black/10 bg-white hover:shadow-lg transitionborder-gray-200 rounded-xl">
      <Link to={`/product-details/${product.id}`}>
        <div className="flex flex-col">
          {/* Product Image */}
          <div className="w-full mb-4 overflow-hidden rounded-lg bg-gray-100">
            {product?.imageUrls?.[0] ? (
              <img src={product?.imageUrls?.[0]} alt={product?.productName} />
            ) : (
              <img
                src="/images/logo.jpg"
                alt={product?.productName}
                className="grayscale-100 opacity-20"
              />
            )}
          </div>

          {/* Name */}
          <h3 className="text-xl font-semibold mb-2">{product?.productName}</h3>

          {/* Status Badges */}
          <div className="flex gap-3 mb-3">
            {product?.active && (
              <span className="px-3 py-1 text-xs font-medium bg-red-100 text-red-600 rounded-full">
                Active
              </span>
            )}
            {product?.featured && (
              <span className="px-3 py-1 text-xs font-medium bg-blue-100 text-blue-600 rounded-full">
                Featured
              </span>
            )}
          </div>

          {/* Price */}
          <div className="mb-3">
            <p className="text-gray-600 text-sm line-through">
              MRP: ₹{product?.mrpPrice}
            </p>
            <p className="text-lg font-bold text-green-600">
              Selling: ₹{product?.sellingPrice}
            </p>
          </div>

          {/* Category Info */}
          <div className="text-sm text-gray-700 mb-4">
            <p>
              <span className="font-semibold">Category:</span>{" "}
              {product?.productCategory}
            </p>
            <p>
              <span className="font-semibold">Sub-Category:</span>{" "}
              {product?.productSubCategory}
            </p>
          </div>
        </div>{" "}
      </Link>
      {/* Add to Cart Button */}
      <button
        onClick={handleAddToCart}
        className="mt-auto w-full py-1 bg-blue-600 hover:bg-blue-700 text-white rounded-lg font-semibold transition"
      >
        Add to Cart
      </button>
    </div>
  );
};

export default ProductCard;

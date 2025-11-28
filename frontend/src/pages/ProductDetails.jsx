import { Edit2, Trash } from "lucide-react";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { toast } from "react-toastify";
import UpdateProductModal from "../components/UpdateProductModal";
import {
  deleteProduct,
  updateProduct,
} from "../store/slices/adminProductSlice";
import {
  getProductById,
  resetProductsSlice,
} from "../store/slices/productsSlice";
import { getUserById } from "../store/slices/userSlice";

const ProductDetails = () => {
  const { id } = useParams();
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const { product, loading, error, message } = useSelector(
    (state) => state.products
  );
  const { userId, isAuthenticated } = useSelector((state) => state.auth);
  const { user } = useSelector((state) => state.user);

  useEffect(() => {
    dispatch(getUserById(userId));
  }, []);

  useEffect(() => {
    dispatch(getProductById(id));
  }, []);

  useEffect(() => {
    if (message) {
      toast.success(message);
      dispatch(resetProductsSlice());
    }
    if (error) {
      toast.error(error);
      dispatch(resetProductsSlice());
    }
  }, [dispatch, message, error]);

  const [selectedProduct, setSelectedProduct] = useState(null);
  const [popupOpen, setPopupOpen] = useState(false);

  const handleUpdateProduct = () => {
    setSelectedProduct(product);
    setPopupOpen(true);
  };

  const handleUpdateSubmit = (updatedProduct) => {
    dispatch(updateProduct(updatedProduct));
  };

  const handleProductDelete = () => {
    const conf = window.confirm(
      `Are you sure you want to delete: ${product?.productName} ❓`
    );
    if (conf) {
      dispatch(deleteProduct(id));
      navigate("/menu");
    }
  };

  return (
    <div className="pt-16 p-6 bg-white rounded-xl shadow-md max-w-4xl min-h-screen mx-auto">
      {/* Product Title */}
      <h3 className="text-2xl font-bold mb-4 text-gray-800">
        {product?.productName}
      </h3>

      {/* Main Content Layout */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {/* Images Section */}
        <div>
          {/* Main Image */}
          <div className="w-full h-64 rounded-lg overflow-hidden bg-gray-100 shadow">
            <img
              src={product?.imageUrls?.[0] || "/images/logo.jpg"}
              alt={product?.productName}
              className="w-full h-full object-cover"
            />
          </div>

          {/* All Images Gallery */}
          <div className="flex gap-3 mt-4 overflow-x-auto">
            {product?.imageUrls?.map((url, ind) => (
              <img
                src={url || "/images/logo.jpg"}
                key={ind}
                className="w-20 h-20 object-cover rounded-lg border border-gray-300"
              />
            ))}
          </div>
        </div>

        {/* Product Info Section */}
        <div>
          {/* Status Badges */}
          <div className="flex gap-3 mb-4">
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

          {/* Price Section */}
          <div className="mb-4">
            <p className="text-gray-500 text-sm line-through">
              MRP: ₹{product?.mrpPrice}
            </p>
            <p className="text-2xl font-bold text-green-600">
              ₹{product?.sellingPrice}
            </p>
          </div>

          {/* Category Info */}
          <div className="text-sm text-gray-700 space-y-1 mb-4">
            <p>
              <span className="font-semibold">Category:</span>{" "}
              {product?.productCategory}
            </p>
            <p>
              <span className="font-semibold">Sub-Category:</span>{" "}
              {product?.productSubCategory}
            </p>
            <p>
              <span className="font-semibold">Weight:</span> {product?.weight}
            </p>
          </div>

          {/* Description */}
          <p className="text-gray-600 leading-relaxed text-sm">
            {product?.productDescription}
          </p>
        </div>
      </div>

      {isAuthenticated && user?.role === "ADMIN" && (
        <>
          <div className="w-full mt-5 p-4 bg-red-500/10 rounded-xl flex items-center gap-4">
            <button
              onClick={handleUpdateProduct}
              className="flex items-center gap-2 rounded-lg py-2 px-4 text-white bg-blue-600 hover:bg-blue-700 transition-all"
            >
              <Edit2 className="w-4 h-4" />
              Edit Product
            </button>

            <button
              onClick={handleProductDelete}
              className="flex items-center gap-2 rounded-lg py-2 px-4 text-white bg-red-600 hover:bg-red-700 transition-all"
            >
              <Trash className="w-4 h-4" />
              Delete Product
            </button>
          </div>
          <UpdateProductModal
            isOpen={popupOpen}
            onClose={() => setPopupOpen(false)}
            product={selectedProduct}
            onUpdate={handleUpdateSubmit}
          />
        </>
      )}
    </div>
  );
};

export default ProductDetails;

import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import {
  deleteProductImage,
  uploadProductImage,
} from "../store/slices/adminProductSlice";

const UpdateProductModal = ({ isOpen, onClose, product, onUpdate }) => {
  const dispatch = useDispatch();

  const [form, setForm] = useState({
    productName: "",
    productDescription: "",
    productCategory: "",
    productSubCategory: "",
    sellingPrice: "",
    mrpPrice: "",
    weight: "",
    active: false,
    featured: false,
    imageUrls: [],
  });

  const [preview, setPreview] = useState([]);

  useEffect(() => {
    if (product) {
      setForm({
        productName: product.productName,
        productDescription: product.productDescription,
        productCategory: product.productCategory,
        productSubCategory: product.productSubCategory,
        sellingPrice: product.sellingPrice,
        mrpPrice: product.mrpPrice,
        weight: product.weight,
        active: product.active,
        featured: product.featured,
        imageUrls: product.imageUrls || [],
      });

      setPreview(product?.imageUrls || []);
    }
  }, [product]);

  if (!isOpen) return null;

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleImage = async (e) => {
    const files = Array.from(e.target.files);
    if (files.length === 0) return;

    const localPreviews = files.map((file) => URL.createObjectURL(file));
    setPreview((prev) => [...prev, ...localPreviews]);

    let uploadedUrls = [];

    for (let file of files) {
      const formData = new FormData();
      formData.append("file", file);

      const result = await dispatch(uploadProductImage(product.id, formData));

      if (result?.payload?.imageUrls) {
        const latestUrl = result.payload.imageUrls.at(-1);
        uploadedUrls.push(latestUrl);
      }
    }

    setForm((prev) => ({
      ...prev,
      imageUrls: [...prev.imageUrls, ...uploadedUrls],
    }));
  };

  const handleDeleteImage = async (url) => {
    console.log(url);
    dispatch(deleteProductImage(product.id, url));

    setForm((prev) => ({
      ...prev,
      imageUrls: prev.imageUrls.filter((img) => img !== url),
    }));

    setPreview((prev) => prev.filter((img) => img !== url));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (form.productCategory === "") {
      alert("Please select Product Category ⚠");
      return;
    }
    if (form.productSubCategory === "") {
      alert("Please select Product Sub-Category ⚠");
      return;
    }

    const updatedProduct = {
      ...product,
      ...form,
      sellingPrice: Number(form.sellingPrice),
      mrpPrice: Number(form.mrpPrice),
      weight: Number(form.weight),
    };

    onUpdate(updatedProduct);
    onClose();
  };

  return (
    <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 backdrop-blur-[3px]">
      <div className="bg-white max-h-[90vh] overflow-y-auto rounded-xl shadow-xl p-6 w-full max-w-2xl">
        <h2 className="text-2xl font-semibold text-amber-800 mb-5 text-center">
          Update Product
        </h2>

        <form
          onSubmit={handleSubmit}
          className="grid grid-cols-1 md:grid-cols-2 gap-6"
        >
          {/* Product Name */}
          <div>
            <label className="font-medium text-gray-700">Product Name</label>
            <input
              type="text"
              name="productName"
              value={form.productName}
              onChange={handleChange}
              required
              className="w-full mt-1 px-4 py-2 border rounded-md"
            />
          </div>

          {/* Selling Price */}
          <div>
            <label className="font-medium text-gray-700">
              Selling Price (₹)
            </label>
            <input
              type="number"
              name="sellingPrice"
              value={form.sellingPrice}
              onChange={handleChange}
              required
              className="w-full mt-1 px-4 py-2 border rounded-md"
            />
          </div>

          {/* MRP Price */}
          <div>
            <label className="font-medium text-gray-700">MRP Price (₹)</label>
            <input
              type="number"
              name="mrpPrice"
              value={form.mrpPrice}
              onChange={handleChange}
              required
              className="w-full mt-1 px-4 py-2 border rounded-md"
            />
          </div>

          {/* Weight */}
          <div>
            <label className="font-medium text-gray-700">Weight (grams)</label>
            <input
              type="number"
              name="weight"
              value={form.weight}
              onChange={handleChange}
              required
              className="w-full mt-1 px-4 py-2 border rounded-md"
            />
          </div>

          {/* Category */}
          <div>
            <label className="font-medium text-gray-700">Category</label>
            <select
              name="productCategory"
              value={form.productCategory}
              onChange={handleChange}
              className="w-full mt-1 px-4 py-2 border rounded-md"
            >
              <option value="">Select category</option>
              <option value="CHOCOLATE">CHOCOLATE</option>
              <option value="GIFT_BOX">GIFT_BOX</option>
              <option value="PREMIUM_HERBS">PREMIUM_HERBS</option>
            </select>
          </div>

          {/* Sub Category */}
          <div>
            <label className="font-medium text-gray-700">Sub Category</label>
            <select
              name="productSubCategory"
              value={form.productSubCategory}
              onChange={handleChange}
              className="w-full mt-1 px-4 py-2 border rounded-md"
            >
              <option value="">Select sub-category</option>
              <option value="KUNAFA_CHOCOLATE">KUNAFA_CHOCOLATE</option>
              <option value="DATES_CHOCOLATE">DATES_CHOCOLATE</option>
              <option value="CIGAR_CHOCOLATE">CIGAR_CHOCOLATE</option>
              <option value="CELEBRATION_BOX">CELEBRATION_BOX</option>
              <option value="IRANI_METHI">IRANI_METHI</option>
              <option value="KESAR">KESAR</option>
            </select>
          </div>

          {/* Active */}
          <div className="flex items-center gap-3">
            <label className="font-medium text-gray-700">Active:</label>
            <input
              type="checkbox"
              checked={form.active}
              onChange={() => setForm({ ...form, active: !form.active })}
              className="h-5 w-5"
            />
          </div>

          {/* Featured */}
          <div className="flex items-center gap-3">
            <label className="font-medium text-gray-700">Featured:</label>
            <input
              type="checkbox"
              checked={form.featured}
              onChange={() => setForm({ ...form, featured: !form.featured })}
              className="h-5 w-5"
            />
          </div>

          {/* Image Upload */}
          <div className="md:col-span-2">
            <label className="font-medium text-gray-700">Product Image</label>
            <input
              type="file"
              accept="image/*"
              onChange={handleImage}
              className="w-full mt-1 px-4 py-2 border rounded-md"
            />

            {preview.length > 0 &&
              preview.map((url, ind) => (
                <div key={ind} className="relative w-40">
                  <img
                    src={url}
                    className="mt-4 w-40 h-40 object-cover rounded-md shadow"
                  />
                  <button
                    type="button"
                    onClick={() => handleDeleteImage(url)}
                    className="absolute top-2 right-2 bg-red-600 text-white px-2 py-1 text-xs rounded"
                  >
                    Delete
                  </button>
                </div>
              ))}
          </div>

          {/* Description */}
          <div className="md:col-span-2">
            <label className="font-medium text-gray-700">Description</label>
            <textarea
              name="productDescription"
              value={form.productDescription}
              onChange={handleChange}
              required
              rows="4"
              className="w-full mt-1 px-4 py-2 border rounded-md"
            />
          </div>

          {/* Buttons */}
          <div className="md:col-span-2 flex justify-end gap-4 mt-4">
            <button
              type="button"
              onClick={onClose}
              className="px-6 py-2 bg-gray-300 rounded-md hover:bg-gray-400"
            >
              Cancel
            </button>

            <button
              type="submit"
              className="px-6 py-2 bg-amber-800 text-white rounded-md hover:bg-amber-700"
            >
              Update Product
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default UpdateProductModal;

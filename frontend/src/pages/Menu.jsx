import { Filter, X } from "lucide-react";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import ProductCard from "../components/ProductCard";
import {
  getActiveFeaturedProducts,
  getActiveProducts,
  getAllProducts,
  getFeaturedProducts,
  getProductByName,
  getProductsByCategory,
  getProductsBySubCategory,
} from "../store/slices/productsSlice";

const categories = ["CHOCOLATE", "GIFT_BOX", "PREMIUM_HERBS"];

const subCategories = [
  "KUNAFA_CHOCOLATE",
  "DATES_CHOCOLATE",
  "CIGAR_CHOCOLATE",
  "CELEBRATION_BOX",
  "IRANI_METHI",
  "KESAR",
];

const Menu = () => {
  const dispatch = useDispatch();
  const { products, loading } = useSelector((state) => state.products);

  const [filter, setFilter] = useState({
    category: "",
    subCategory: "",
    search: "",
    type: "ALL", // ALL | ACTIVE | FEATURED | ACTIVE_FEATURED
  });

  const [openMobileFilter, setOpenMobileFilter] = useState(false);

  useEffect(() => {
    applyFilters();
  }, [filter]);

  const applyFilters = () => {
    if (filter.search.trim() !== "") {
      dispatch(getProductByName(filter.search));
      return;
    }

    if (filter.type === "ACTIVE") {
      dispatch(getActiveProducts());
    } else if (filter.type === "FEATURED") {
      dispatch(getFeaturedProducts());
    } else if (filter.type === "ACTIVE_FEATURED") {
      dispatch(getActiveFeaturedProducts());
    } else if (filter.category) {
      dispatch(getProductsByCategory(filter.category));
    } else if (filter.subCategory) {
      dispatch(getProductsBySubCategory(filter.subCategory));
    } else {
      dispatch(getAllProducts());
    }
  };

  const handleFilterChange = (key, value) => {
    setFilter({ ...filter, [key]: value });
  };

  const clearFilters = () => {
    setFilter({
      category: "",
      subCategory: "",
      search: "",
      type: "ALL",
    });
  };

  return (
    <div className="min-h-screen flex pt-20 px-5 relative">
      {/* Desktop Sidebar */}
      <div className="hidden md:block w-64 mr-6">
        <div className="bg-white shadow rounded-lg p-4 sticky top-24">
          <h2 className="text-xl font-semibold text-amber-800 mb-3">Filters</h2>

          {/* Search */}
          <input
            type="text"
            placeholder="Search by name..."
            value={filter.search}
            onChange={(e) => handleFilterChange("search", e.target.value)}
            className="w-full px-3 py-2 mb-4 border rounded-md"
          />

          {/* Category */}
          <h3 className="font-semibold text-gray-700 mb-1">Category</h3>
          <select
            value={filter.category}
            onChange={(e) => handleFilterChange("category", e.target.value)}
            className="w-full mb-4 px-3 py-2 border rounded-md"
          >
            <option value="">All</option>
            {categories.map((c, i) => (
              <option key={i} value={c}>
                {c}
              </option>
            ))}
          </select>

          {/* Sub Category */}
          <h3 className="font-semibold text-gray-700 mb-1">Sub Category</h3>
          <select
            value={filter.subCategory}
            onChange={(e) => handleFilterChange("subCategory", e.target.value)}
            className="w-full mb-4 px-3 py-2 border rounded-md"
          >
            <option value="">All</option>
            {subCategories.map((s, i) => (
              <option key={i} value={s}>
                {s}
              </option>
            ))}
          </select>

          {/* Type */}
          <h3 className="font-semibold text-gray-700 mb-1">Type</h3>
          <select
            value={filter.type}
            onChange={(e) => handleFilterChange("type", e.target.value)}
            className="w-full mb-4 px-3 py-2 border rounded-md"
          >
            <option value="ALL">All</option>
            <option value="ACTIVE">Active</option>
            <option value="FEATURED">Featured</option>
            <option value="ACTIVE_FEATURED">Active + Featured</option>
          </select>

          <button
            onClick={clearFilters}
            className="w-full bg-red-500 text-white py-2 rounded-md"
          >
            Clear Filters
          </button>
        </div>
      </div>

      {/* Products Grid */}
      <div className="flex-1">
        <div className="mb-8 text-center">
          <h2 className="text-3xl font-bold text-gray-800">Menu Page</h2>
        </div>

        {loading ? (
          <p className="text-red-500 text-center font-semibold text-lg">
            Loading...
          </p>
        ) : (
          <div className="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-6">
            {products
              ? products?.map((product, ind) => (
                  <ProductCard product={product} key={ind} />
                ))
              : "No products found."}
          </div>
        )}
      </div>

      {/* Mobile Filter Floating Button */}
      <button
        className="md:hidden fixed bottom-14 right-6 bg-amber-700 text-white p-4 rounded-full shadow-lg"
        onClick={() => setOpenMobileFilter(true)}
      >
        <Filter size={24} />
      </button>

      {/* Mobile Bottom Sheet Filter */}
      {openMobileFilter && (
        <div className="fixed inset-0 bg-black/50 flex justify-center items-end z-50">
          <div className="bg-white w-full p-5 rounded-t-2xl shadow-xl animate-slideUp">
            {/* Header */}
            <div className="flex justify-between items-center mb-4">
              <h3 className="text-xl font-semibold text-amber-800">Filters</h3>
              <X
                className="cursor-pointer"
                onClick={() => setOpenMobileFilter(false)}
              />
            </div>

            {/* Mobile Filter Options */}
            <div>
              {/* Search */}
              <input
                type="text"
                placeholder="Search by name..."
                value={filter.search}
                onChange={(e) => handleFilterChange("search", e.target.value)}
                className="w-full px-3 py-2 mb-4 border rounded-md"
              />

              {/* Category */}
              <select
                value={filter.category}
                onChange={(e) => handleFilterChange("category", e.target.value)}
                className="w-full mb-4 px-3 py-2 border rounded-md"
              >
                <option value="">All Categories</option>
                {categories.map((c, i) => (
                  <option key={i} value={c}>
                    {c}
                  </option>
                ))}
              </select>

              {/* Sub Category */}
              <select
                value={filter.subCategory}
                onChange={(e) =>
                  handleFilterChange("subCategory", e.target.value)
                }
                className="w-full mb-4 px-3 py-2 border rounded-md"
              >
                <option value="">All Sub Categories</option>
                {subCategories.map((s, i) => (
                  <option key={i} value={s}>
                    {s}
                  </option>
                ))}
              </select>

              {/* Type */}
              <select
                value={filter.type}
                onChange={(e) => handleFilterChange("type", e.target.value)}
                className="w-full mb-4 px-3 py-2 border rounded-md"
              >
                <option value="ALL">All Products</option>
                <option value="ACTIVE">Active</option>
                <option value="FEATURED">Featured</option>
                <option value="ACTIVE_FEATURED">Active + Featured</option>
              </select>

              {/* Buttons */}
              <button
                onClick={() => {
                  setOpenMobileFilter(false);
                  applyFilters();
                }}
                className="w-full bg-amber-700 text-white py-3 rounded-md mb-3"
              >
                Apply Filters
              </button>

              <button
                className="w-full bg-red-500 text-white py-3 rounded-md"
                onClick={clearFilters}
              >
                Clear Filters
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Menu;

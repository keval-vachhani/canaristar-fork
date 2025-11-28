import { Edit2, Trash2 } from "lucide-react";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { getUsers } from "../store/slices/adminSlice";
import { deleteUser, updateUser } from "../store/slices/userSlice";
import UpdateUserModal from "./UpdateUserModal";

const AdminUsers = () => {
  const dispatch = useDispatch();
  const { users, loading, totalPages } = useSelector((state) => state.admin);

  const [page, setPage] = useState(1);
  const [size, setSize] = useState(3);
  const [search, setSearch] = useState("");
  const [role, setRole] = useState("all");

  useEffect(() => {
    dispatch(getUsers(page, size, search, role));
  }, [dispatch, page, size, search, role]);

  const [sortField, setSortField] = useState("name");
  const [sortOrder, setSortOrder] = useState("asc");

  const sortData = (field) => {
    const order = sortOrder === "asc" ? "desc" : "asc";
    setSortOrder(order);
    setSortField(field);

    users.sort((a, b) => {
      const valA = a[field]?.toString().toLowerCase();
      const valB = b[field]?.toString().toLowerCase();
      return order === "asc"
        ? valA.localeCompare(valB)
        : valB.localeCompare(valA);
    });
  };

  const handleDeleteUser = (id, name) => {
    const permission = confirm(
      `Are you sure you want to delete user: ${name.toUpperCase()} ❓`
    );

    if (!permission) return;

    dispatch(deleteUser(id));
  };

  const [selectedUser, setSelectedUser] = useState(null);
  const [popupOpen, setPopupOpen] = useState(false);

  const handleUpdateUser = (user) => {
    setSelectedUser(user);
    setPopupOpen(true);
  };

  const handleUpdateSubmit = (updatedUser) => {
    dispatch(updateUser(updatedUser));
  };

  return (
    <div className="p-5 md:p-10 min-h-screen bg-gray-50">
      <h1 className="text-2xl font-bold mb-6">Users Management</h1>

      {/* Filters Section */}
      <div className="grid md:flex items-center gap-4 mb-5">
        {/* Search */}
        <input
          type="text"
          placeholder="Search by name, email, mobile..."
          className="w-full md:w-1/3 px-4 py-2 border rounded-lg"
          onChange={(e) => {
            setPage(1);
            setSearch(e.target.value);
          }}
        />

        {/* Role Filter */}
        <select
          className="px-4 py-2 border rounded-lg"
          onChange={(e) => {
            setPage(1);
            setRole(e.target.value);
          }}
        >
          <option value="all">All Roles</option>
          <option value="admin">Admin</option>
          <option value="customer">Customer</option>
          <option value="seller">Seller</option>
        </select>

        {/* Page Size */}
        <select
          className="px-4 py-2 border rounded-lg"
          onChange={(e) => {
            setPage(1);
            setSize(e.target.value);
          }}
        >
          <option value="3">3 per page</option>
          <option value="5">5 per page</option>
          <option value="10">10 per page</option>
          <option value="20">20 per page</option>
          <option value="50">50 per page</option>
          <option value="100">100 per page</option>
        </select>
      </div>

      {/* Table */}
      <div className="overflow-auto rounded-xl shadow border bg-white">
        <table className="w-full text-left text-sm">
          <thead>
            <tr className="bg-gray-100 text-gray-700">
              <th className="p-3">#</th>
              <th
                className="p-3 cursor-pointer"
                onClick={() => sortData("name")}
              >
                Name {sortField === "name" && (sortOrder === "asc" ? "↑" : "↓")}
              </th>
              <th
                className="p-3 cursor-pointer"
                onClick={() => sortData("email")}
              >
                Email{" "}
                {sortField === "email" && (sortOrder === "asc" ? "↑" : "↓")}
              </th>
              <th className="p-3">Mobile</th>
              <th
                className="p-3 cursor-pointer"
                onClick={() => sortData("role")}
              >
                Role {sortField === "role" && (sortOrder === "asc" ? "↑" : "↓")}
              </th>
              <th className="p-3">Address</th>
              <th className="p-3">Edit</th>
              <th className="p-3">Delete</th>
            </tr>
          </thead>

          {loading ? (
            <tbody>
              {Array.from({ length: 8 }).map((_, i) => (
                <tr key={i} className="animate-pulse">
                  <td className="p-3">
                    <div className="w-6 h-3 bg-gray-200 rounded"></div>
                  </td>
                  <td className="p-3">
                    <div className="w-24 h-3 bg-gray-200 rounded"></div>
                  </td>
                  <td className="p-3">
                    <div className="w-32 h-3 bg-gray-200 rounded"></div>
                  </td>
                  <td className="p-3">
                    <div className="w-20 h-3 bg-gray-200 rounded"></div>
                  </td>
                  <td className="p-3">
                    <div className="w-16 h-3 bg-gray-200 rounded"></div>
                  </td>
                  <td className="p-3">
                    <div className="w-40 h-3 bg-gray-200 rounded"></div>
                  </td>
                  <td className="p-3">
                    <div className="w-40 h-3 bg-gray-200 rounded"></div>
                  </td>
                  <td className="p-3">
                    <div className="w-40 h-3 bg-gray-200 rounded"></div>
                  </td>
                </tr>
              ))}
            </tbody>
          ) : users?.length > 0 ? (
            <tbody>
              {users.map((user, ind) => (
                <tr className="border-b hover:bg-gray-50" key={user?.id}>
                  <td className="p-3">{(page - 1) * size + ind + 1}</td>
                  <td className="p-3 font-medium">{user?.name}</td>
                  <td className="p-3">{user?.email}</td>
                  <td className="p-3">{user?.mobile}</td>
                  <td className="p-3 capitalize">
                    <span
                      className={`px-3 py-1 text-xs rounded-full ${
                        user?.role === "admin"
                          ? "bg-red-100 text-red-600"
                          : user?.role === "seller"
                          ? "bg-blue-100 text-blue-700"
                          : "bg-green-100 text-green-700"
                      }`}
                    >
                      {user?.role}
                    </span>
                  </td>
                  <td className="p-3 text-xs">
                    {user?.address?.street}, {user?.address?.city},{" "}
                    {user?.address?.state}, {user?.address?.country} -{" "}
                    {user?.address?.postalCode}
                  </td>
                  <td className="p-3 text-xs">
                    <button
                      className="text-blue-500 bg-blue-500/10 p-1 rounded-lg hover:scale-110 hover:bg-red-15 transition"
                      onClick={() => handleUpdateUser(user)}
                    >
                      <Edit2 size={16} />
                    </button>
                  </td>
                  <td className="p-3 text-xs">
                    <button
                      className="text-red-500 bg-red-500/10 p-1 rounded-lg hover:scale-110 hover:bg-red-15 transition"
                      onClick={() => handleDeleteUser(user?.id, user?.name)}
                    >
                      <Trash2 size={18} />
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          ) : (
            <tbody>
              <tr>
                <td colSpan="6" className="text-center py-10 text-gray-500">
                  No users found
                </td>
              </tr>
            </tbody>
          )}
        </table>
      </div>

      <UpdateUserModal
        isOpen={popupOpen}
        onClose={() => setPopupOpen(false)}
        user={selectedUser}
        onUpdate={handleUpdateSubmit}
      />

      {/* Pagination */}
      <div className="flex items-center justify-between mt-5">
        <button
          disabled={page === 1}
          onClick={() => setPage((prev) => prev - 1)}
          className={`px-4 py-2 rounded-lg border ${
            page === 1
              ? "bg-gray-200 text-gray-400 cursor-not-allowed"
              : "bg-white hover:bg-gray-100"
          }`}
        >
          Previous
        </button>

        <span className="text-gray-700">
          Page <strong>{page}</strong> of <strong>{totalPages || 1}</strong>
        </span>

        <button
          disabled={page === totalPages}
          onClick={() => setPage((prev) => prev + 1)}
          className={`px-4 py-2 rounded-lg border ${
            page === totalPages
              ? "bg-gray-200 text-gray-400 cursor-not-allowed"
              : "bg-white hover:bg-gray-100"
          }`}
        >
          Next
        </button>
      </div>
    </div>
  );
};

export default AdminUsers;

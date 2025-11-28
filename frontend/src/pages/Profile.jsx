import { Mail, MapPinHouse, Phone, Shield, User } from "lucide-react";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import AdminUsers from "../components/AdminUsers";
import UpdateUserModal from "../components/UpdateUserModal";
import {
  createAdmin,
  getAllUsers,
  resetAdminSlice,
} from "../store/slices/adminSlice";
import { signout } from "../store/slices/authSlice";
import {
  getUserById,
  resetPassword,
  updateUser,
  verifyPassword,
} from "../store/slices/userSlice";

const Profile = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [email, setEmail] = useState("");

  const { userId, isAuthenticated } = useSelector((state) => state.auth);
  const { user, error: err, message: msg } = useSelector((state) => state.user);
  const { loading, error, message } = useSelector((state) => state.admin);

  useEffect(() => {
    if (userId) {
      dispatch(getUserById(userId));
      console.log(user);
    }
  }, []);

  const handleSignOut = () => {
    dispatch(signout());
    navigate("/signin");
  };

  const handleCreateAdmin = (e) => {
    e.preventDefault();
    if (email === "") {
      alert("please enter valid email! ⚠");
      return;
    }
    dispatch(createAdmin(email));
  };

  useEffect(() => {
    if (user?.role === "ADMIN") {
      dispatch(getAllUsers());
    }
  }, []);

  useEffect(() => {
    if (message || msg) {
      toast.success(message);
      dispatch(resetAdminSlice());
    }
    if (error || err) {
      toast.error(error);
      dispatch(resetAdminSlice());
    }
  }, [dispatch, message, error, msg, err]);

  const [selectedUser, setSelectedUser] = useState(null);
  const [popupOpen, setPopupOpen] = useState(false);

  const handleUpdateUser = (user) => {
    setSelectedUser(user);
    setPopupOpen(true);
  };

  const handleUpdateSubmit = (updatedUser) => {
    dispatch(updateUser(updatedUser));
  };

  const [newPassword, setNewPassword] = useState("");

  const handleResetPassword = () => {
    const userEmail = user?.email;
    if (!userEmail || !newPassword) {
      alert("Please enter new password");
      return;
    }

    const data = {
      email: userEmail,
      password: newPassword,
    };

    dispatch(resetPassword(data));
    setNewPassword("");
  };

  const [otp, setOtp] = useState("");
  const handleVerifyPassword = () => {
    const userEmail = user?.email;
    if (!userEmail || !otp) {
      alert("Please enter OTP");
      return;
    }

    dispatch(verifyPassword(userEmail, otp));
  };

  return (
    <div className="min-h-screen pt-16 flex flex-col items-center gap-5 p-5">
      {isAuthenticated && (
        <div className="max-w-md mx-auto bg-white rounded-xl p-6 border border-black/20">
          {/* Profile Header */}
          <div className="flex flex-col items-center">
            <img
              src="/images/logo.jpg"
              className="w-24 h-24 grayscale-100 opacity-50 rounded-full shadow-md border"
              alt="user avatar"
            />
            <h2 className="text-2xl font-bold text-gray-800 mt-3">
              {user?.name}
            </h2>

            <span
              className={`mt-2 px-3 py-1 text-sm text-white rounded-full ${
                user?.role === "ADMIN" ? "bg-red-600" : "bg-blue-600"
              }`}
            >
              {user?.role}
            </span>
          </div>

          {/* Info Section */}
          <div className="mt-6 space-y-4 text-gray-700">
            <div className="flex items-center gap-3">
              <User className="text-gray-500" size={20} />
              <div>
                <p className="text-sm font-semibold text-gray-500">Full Name</p>
                <p className="font-medium">{user?.name}</p>
              </div>
            </div>

            <div className="flex items-center gap-3">
              <Mail className="text-gray-500" size={20} />
              <div>
                <p className="text-sm font-semibold text-gray-500">Email</p>
                <p className="font-medium">{user?.email}</p>
              </div>
            </div>

            <div className="flex items-center gap-3">
              <Phone className="text-gray-500" size={20} />
              <div>
                <p className="text-sm font-semibold text-gray-500">Mobile</p>
                <p className="font-medium">{user?.mobile}</p>
              </div>
            </div>

            <div className="flex items-center gap-3">
              <MapPinHouse className="text-gray-500" size={20} />
              <div>
                <p className="text-sm font-semibold text-gray-500">Address</p>
                <p className="font-medium">
                  {user?.address?.street},{user?.address?.city},
                  {user?.address?.state},{user?.address?.country} -{" "}
                  {user?.address?.postalCode}
                </p>
              </div>
            </div>

            <div className="flex items-center gap-3">
              <Shield className="text-gray-500" size={20} />
              <div>
                <p className="text-sm font-semibold text-gray-500">Role</p>
                <p className="font-medium">{user?.role}</p>
              </div>
            </div>
          </div>

          <div className="p-4 max-w-sm mx-auto bg-white rounded-lg shadow">
            <h2 className="text-xl font-semibold mb-4">Reset Password</h2>

            {/* New Password Input */}
            <input
              type="password"
              placeholder="Enter New Password"
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}
              className="border w-full p-2 rounded mb-3"
            />

            {/* Button */}
            <button
              onClick={handleResetPassword}
              disabled={loading}
              className="bg-blue-600 text-white w-full py-2 rounded mt-2"
            >
              {loading ? "Sending OTP..." : "Reset Password"}
            </button>

            {/* Success/Error Messages */}
            {message && (
              <p className="text-green-600 mt-3 font-medium">{message}</p>
            )}

            {error && <p className="text-red-600 mt-3 font-medium">{error}</p>}
          </div>

          <div className="p-4 max-w-sm mx-auto bg-white rounded-lg shadow">
            <h2 className="text-xl font-semibold mb-4">Verify Password</h2>

            {/* OTP Input */}
            <input
              type="password"
              placeholder="Enter New Password"
              value={otp}
              onChange={(e) => setOtp(e.target.value)}
              className="border w-full p-2 rounded mb-3"
            />

            {/* Button */}
            <button
              onClick={handleVerifyPassword}
              disabled={loading}
              className="bg-blue-600 text-white w-full py-2 rounded mt-2"
            >
              {loading ? "Sending OTP..." : "Verify Password"}
            </button>

            {/* Success/Error Messages */}
            {message && (
              <p className="text-green-600 mt-3 font-medium">{message}</p>
            )}

            {error && <p className="text-red-600 mt-3 font-medium">{error}</p>}
          </div>

          {/* Buttons */}
          <div className="mt-8 flex justify-center gap-4">
            <button
              onClick={() => handleUpdateUser(user)}
              className="px-6 py-2 bg-blue-500 text-white rounded-lg font-medium hover:bg-blue-400 transition"
            >
              Edit Profile
            </button>

            <button
              onClick={handleSignOut}
              className="px-6 py-2 bg-red-500 text-white rounded-lg font-medium hover:bg-red-400 transition"
            >
              Logout
            </button>
          </div>
        </div>
      )}

      <UpdateUserModal
        isOpen={popupOpen}
        onClose={() => setPopupOpen(false)}
        user={selectedUser}
        onUpdate={handleUpdateSubmit}
      />

      {isAuthenticated && user?.role === "ADMIN" && (
        <>
          <div className="bg-amber-950/5 p-10 flex gap-3">
            <input
              onChange={(e) => setEmail(e.target.value)}
              type="email"
              placeholder="enter email to create admin..."
              className="border border-black/20 rounded-lg px-3 py-1"
            />
            <button
              onClick={handleCreateAdmin}
              className="bg-amber-900 px-4 py-2 rounded text-white"
            >
              Create Admin
            </button>
          </div>
          <Link
            to="/create-product"
            className="bg-blue-500 rounded-lg py-1 px-3 text-white"
          >
            Create Product
          </Link>
          <AdminUsers />
        </>
      )}

      {!isAuthenticated && (
        <p>
          Not signed in yet?{" "}
          <Link to="/signin" className="underline text-blue-500">
            Login
          </Link>{" "}
          here
        </p>
      )}
    </div>
  );
};

export default Profile;

import { Eye, EyeOff, Loader2, Lock, LogIn, Mail } from "lucide-react";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { NavLink, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { resetAuthSlice, signin } from "../store/slices/authSlice";

const InputField = ({
  Icon,
  type,
  value,
  onChange,
  placeholder,
  name,
  isPassword = false,
  showPass,
  toggleShowPass,
}) => (
  <div className="relative w-full">
    <Icon className="absolute left-4 top-1/2 transform -translate-y-1/2 w-5 h-5 text-amber-900 transition duration-300 peer-focus:text-amber-700" />

    <input
      type={type}
      value={value}
      onChange={onChange}
      placeholder={placeholder}
      name={name}
      required
      className="peer w-full border border-stone-200 pl-12 pr-4 py-3 bg-stone-50 rounded-xl text-stone-800 placeholder-stone-500 
                       focus:outline-none focus:border-amber-700 focus:ring-4 focus:ring-amber-100 transition duration-300 shadow-sm hover:shadow-md"
    />

    {isPassword && (
      <button
        type="button"
        onClick={toggleShowPass}
        className="absolute right-4 top-1/2 transform -translate-y-1/2 text-stone-500 hover:text-amber-700 transition duration-200 focus:outline-none"
      >
        {showPass ? <EyeOff size={20} /> : <Eye size={20} />}
      </button>
    )}
  </div>
);

const SignIn = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);

  const dispatch = useDispatch();

  const { loading, error, message, user, isAuthenticated } = useSelector(
    (state) => state.auth
  );

  const navigate = useNavigate();

  const handleSignIn = (e) => {
    e.preventDefault();
    const data = { email, password };
    dispatch(signin(data));
  };

  useEffect(() => {
    if (message) {
      toast.success(message);
      dispatch(resetAuthSlice());
    }
    if (error) {
      toast.error(error);
      dispatch(resetAuthSlice());
    }
  }, [dispatch, message, error]);

  useEffect(() => {
    if (isAuthenticated) {
      navigate("/");
    }
  }, [isAuthenticated, navigate]);

  return (
    <div className="min-h-screen bg-stone-100 text-stone-700 flex items-center justify-center p-4 md:p-8">
      <div className="w-full max-w-lg bg-white rounded-3xl p-7 md:p-10 shadow-2xl transition duration-500 transform hover:shadow-stone-400/50 animate-fadeInSlideUp">
        <form
          onSubmit={handleSignIn}
          className="w-full p-4 grid grid-cols-1 gap-8"
        >
          <div className="text-center mb-4">
            <img
              src="/images/logo.jpg"
              alt="Canaristar Logo"
              className="w-16 h-16 mx-auto mb-3 object-contain rounded-full"
            />
            <h2 className="font-extrabold text-4xl text-amber-900 tracking-wider">
              Welcome Back
            </h2>
            <p className="text-stone-500 mt-1">
              Log in and taste the magic of{" "}
              <span className=" text-amber-900 font-bold">Canari Star.</span>
            </p>
          </div>

          <div className="flex flex-col gap-6">
            {/* Email */}
            <InputField
              Icon={Mail}
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              name="email"
              placeholder="Email Address"
            />

            {/* Password */}
            <div className="flex flex-col">
              <InputField
                Icon={Lock}
                type={showPassword ? "text" : "password"}
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Password"
                name="password"
                isPassword
                showPass={showPassword}
                toggleShowPass={() => setShowPassword(!showPassword)}
              />

              {/* Forgot Password Link */}
              <NavLink
                draggable="false"
                to="/password/forgot"
                className="self-end text-sm font-medium text-amber-700 mt-2 hover:text-amber-900 hover:underline transition duration-200"
              >
                Forgot password?
              </NavLink>
            </div>

            {/* Submit */}
            <div className="mt-4">
              <button
                type="submit"
                disabled={loading}
                // Rich cocoa gradient and animated effect
                className="w-full bg-gradient-to-r from-amber-900 to-amber-700 text-white font-bold tracking-widest rounded-xl py-3 shadow-lg 
                                           hover:shadow-amber-500/50 hover:from-amber-800 hover:to-amber-600 transition duration-300 ease-in-out transform hover:scale-[1.01] focus:ring-4 focus:ring-amber-300 focus:outline-none disabled:opacity-50"
              >
                {loading ? (
                  <span className="flex items-center justify-center gap-3">
                    <Loader2 className="animate-spin" size={20} />
                    LOGGING IN...
                  </span>
                ) : (
                  <span className="flex items-center justify-center gap-3">
                    <LogIn size={20} />
                    LOG IN
                  </span>
                )}
              </button>

              {/* Register Link */}
              <p className="text-center text-stone-500 mt-4 text-sm">
                Don’t have an account?{" "}
                <NavLink
                  draggable="false"
                  to="/signup"
                  className="text-amber-700 font-bold hover:text-amber-900 hover:underline transition duration-200"
                >
                  Register
                </NavLink>
              </p>
            </div>
          </div>
        </form>
      </div>

      {/* Custom CSS for the entry animation */}
      <style>
        {`
                @keyframes fadeInSlideUp {
                    0% { opacity: 0; transform: translateY(20px); }
                    100% { opacity: 1; transform: translateY(0); }
                }
                .animate-fadeInSlideUp {
                    animation: fadeInSlideUp 0.8s ease-out forwards;
                }
                `}
      </style>
    </div>
  );
};

export default SignIn;

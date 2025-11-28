import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { resetAuthSlice, signup } from "../store/slices/authSlice";

import { Eye, EyeOff, Lock, LogIn, Mail, Smartphone, User } from "lucide-react";

const InputField = ({
  Icon,
  type,
  value,
  onChange,
  placeholder,
  name,
  minLength,
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
      minLength={minLength}
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

const SignUp = () => {
  const { loading, error, message, user, isAuthenticated } = useSelector(
    (state) => state.auth
  );
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [mobile, setMobile] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [showCPassword, setShowCPassword] = useState(false);

  const navigate = useNavigate();
  const dispatch = useDispatch();

  const handleSignUp = (e) => {
    e.preventDefault();
    if (password !== confirmPassword) {
      toast.error("Passwords do not match", { theme: "dark" });
      return;
    }
    const data = {
      name,
      email,
      mobile,
      password,
      // address: {
      //   street: "string",
      //   city: "string",
      //   state: "string",
      //   country: "string",
      //   postalCode: "string",
      // },
    };
    dispatch(signup(data));
  };

  useEffect(() => {
    if (message) {
      toast.success(message);
      dispatch(resetAuthSlice());
      if (email) {
        navigate(`/otp-verification/${email}`);
      }
    }
    if (error) {
      toast.error(error);
      dispatch(resetAuthSlice());
    }
  }, [dispatch, isAuthenticated, error, loading, message, email, navigate]);

  return (
    <div className="h-screen overflow-hidden bg-stone-100 text-stone-700 flex items-center justify-center p-3 md:p-8">
      <div
        className="w-full max-w-4xl bg-white rounded-3xl p-6 md:p-10 shadow-2xl 
                      transition duration-500 transform hover:shadow-stone-400/50 
                      animate-fadeInSlideUp 
                      max-h-[90vh] overflow-y-auto"
      >
        <form onSubmit={handleSignUp} className="grid grid-cols-1 gap-6">
          <div className="text-center mb-4">
            <img
              src="/images/logo.jpg"
              alt="Canaristar Logo"
              className="w-16 h-16 mx-auto mb-3 object-contain rounded-full"
            />

            <h2 className="font-extrabold text-3xl text-amber-900 tracking-wider">
              Sign Up for Indulgence
            </h2>
            <p className="text-stone-500 mt-1">
              Welcome to{" "}
              <span className="text-amber-700 font-bold">Canaristar</span> –
              bringing you the{" "}
              <span className="text-amber-700 font-bold">Taste of Dubai.</span>
            </p>
          </div>

          <div className="flex flex-col md:flex-row gap-6">
            <InputField
              Icon={User}
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
              placeholder="Full Name"
              name="name"
            />
            <InputField
              Icon={Mail}
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Email Address"
              name="email"
            />
          </div>

          <InputField
            Icon={Smartphone}
            type="tel"
            value={mobile}
            onChange={(e) => setMobile(e.target.value)}
            placeholder="Contact Number"
            name="mobile"
          />

          <div className="flex flex-col md:flex-row gap-6">
            <InputField
              Icon={Lock}
              type={showPassword ? "text" : "password"}
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Password"
              name="password"
              minLength={8}
              isPassword
              showPass={showPassword}
              toggleShowPass={() => setShowPassword(!showPassword)}
            />

            <InputField
              Icon={Lock}
              type={showCPassword ? "text" : "password"}
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              placeholder="Confirm Password"
              name="cpassword"
              minLength={8}
              isPassword
              showPass={showCPassword}
              toggleShowPass={() => setShowCPassword(!showCPassword)}
            />
          </div>

          <div className="flex flex-col items-center mt-2">
            <button
              type="submit"
              disabled={loading}
              className="w-full md:w-2/3 lg:w-1/2 bg-gradient-to-r from-amber-900 to-amber-700 text-white font-bold tracking-widest rounded-xl py-3 shadow-lg 
                         hover:shadow-amber-500/50 hover:from-amber-800 hover:to-amber-600 transition duration-300 transform hover:scale-[1.02] 
                         focus:ring-4 focus:ring-amber-300 disabled:opacity-50"
            >
              <span className="flex items-center justify-center gap-3">
                {loading ? (
                  "Registering..."
                ) : (
                  <>
                    <LogIn size={20} /> SIGNUP
                  </>
                )}
              </span>
            </button>

            <p className="text-center text-stone-500 mt-3 text-sm">
              Already have an account?{" "}
              <Link
                to="/signin"
                className="text-amber-700 font-bold hover:text-amber-900 hover:underline"
              >
                Login Here
              </Link>
            </p>
          </div>
        </form>
      </div>

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

export default SignUp;

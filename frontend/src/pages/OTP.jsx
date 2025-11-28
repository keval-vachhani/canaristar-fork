import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { toast } from "react-toastify";
import {
  resendOtp,
  resetAuthSlice,
  verifyOtp,
} from "../store/slices/authSlice";

const OTP = () => {
  const { email } = useParams();
  const [otp, setOtp] = useState("");
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [timer, setTimer] = useState(20);

  const { loading, error, message, isAuthenticated } = useSelector(
    (state) => state.auth
  );

  useEffect(() => {
    if (timer === 0) return;
    const interval = setInterval(() => {
      setTimer((prev) => prev - 1);
    }, 1000);

    return () => clearInterval(interval);
  }, [timer]);

  const handleOtpVerification = (e) => {
    e.preventDefault();
    dispatch(verifyOtp(email, otp));
    navigate("/signin");
  };

  const handleResendOtp = () => {
    if (timer === 0) {
      dispatch(resendOtp(email));
      toast.success("OTP resent successfully!");
      setTimer(20);
    }
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

  // if (isAuthenticated) {
  //   return <Navigate to="/" />;
  // }

  return (
    <div className="min-h-screen pt-10 flex justify-center">
      <div className="pt-10 m-auto text-gray-300 rounded-lg p-5 backdrop-blur-[3px] bg-amber-900">
        <form
          onSubmit={handleOtpVerification}
          className="flex justify-between gap-8 mb-10 flex-col"
        >
          <div className="flex justify-between">
            <h2 className="text-white font-bold text-2xl">Enter OTP</h2>
            <img
              draggable="false"
              loading="lazy"
              src="/images/logo.jpg"
              className="h-10 rounded-full"
            />
          </div>

          <p>
            Check your <b>{email}</b> mailbox
          </p>
          <p>Please enter the OTP to proceed</p>

          <input
            type="text"
            value={otp}
            onChange={(e) => setOtp(e.target.value)}
            placeholder="Enter OTP"
            className="w-full px-4 py-2 rounded-md bg-white/20 text-white focus:outline-none focus:ring-2 focus:ring-amber-700"
          />

          <button
            type="submit"
            className="w-full py-2 text-amber-950 font-semibold bg-white rounded-md hover:bg-white/90"
          >
            {loading ? "Verifying..." : "VERIFY"}
          </button>
        </form>

        <button
          type="button"
          onClick={handleResendOtp}
          disabled={timer > 0}
          className={`w-full py-2 rounded-md font-semibold 
            ${
              timer === 0
                ? "bg-white text-amber-950"
                : "bg-white/40 text-white cursor-not-allowed"
            }
          `}
        >
          {timer === 0 ? "RESEND OTP" : `Resend OTP in ${timer}s`}
        </button>
      </div>
    </div>
  );
};

export default OTP;

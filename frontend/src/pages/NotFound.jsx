import { useSelector } from "react-redux";
import { NavLink } from "react-router-dom";

const NotFound = () => {
  const { student } = useSelector((state) => state.auth);
  return (
    <>
      <div className="m-4 min-h-screen border border-white/10 rounded flex flex-col md:flex-row items-center justify-center">
        <img
          draggable="false"
          loading="lazy"
          src="/not-found.svg"
          width="250"
          height="250"
          alt="Icon"
        />
        <div className="w-[100%] md:w-[40%] flex flex-col items-center gap-3">
          <p className="text-amber-800 font-semibold text-6xl">OOPS!</p>
          <p className="text-lg tracking-[2px]">nothing found here....</p>
          <NavLink
            draggable="false"
            to="/"
            className="text-amber-950 text-xl bg-amber-900/15 hover:bg-amber-800/20 rounded-lg px-14 py-2"
          >
            Return Home
          </NavLink>
        </div>
      </div>
    </>
  );
};

export default NotFound;

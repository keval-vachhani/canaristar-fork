import React from "react";
import Slider from "../components/Slider";
import InfiniteSlider from "../components/InfiniteSlider";

const Home = () => {
  return (
    <div className="min-h-[88vh]  text-gray-200 text-xl">
      <Slider />
      <h2 className="text-amber-900 font-bold text-center mt-5">
        Our Special Products
      </h2>
      <InfiniteSlider />
      <h2 className="text-amber-900 font-bold text-center mt-5">About Us</h2>
      <h2 className="text-amber-900 font-bold text-center mt-5">Contact Us</h2>
      <h2 className="text-amber-900 font-bold text-center mt-5">...</h2>
    </div>
  );
};

export default Home;

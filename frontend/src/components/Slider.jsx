import { ChevronLeftIcon, ChevronRightIcon } from "lucide-react";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

const images = [
  "/images/slider/image1.png",
  "/images/slider/image2.png",
  "/images/slider/image3.png",
  "/images/slider/image4.png",
  "/images/slider/image5.png",
];

const Slider = () => {
  const [index, setIndex] = useState(0);

  const prevSlide = () => {
    setIndex(index === 0 ? images.length - 1 : index - 1);
  };

  const nextSlide = () => {
    setIndex(index === images.length - 1 ? 0 : index + 1);
  };

  useEffect(() => {
    const interval = setInterval(() => {
      setIndex((prev) => (prev + 1) % images.length);
    }, 2500);

    return () => clearInterval(interval);
  }, []);

  return (
    <div className="w-full h-[70vh] md:h-screen relative overflow-hidden">
      {/* Images */}
      <div
        className="flex transition-transform duration-700 ease-out h-full"
        style={{ transform: `translateX(-${index * 100}%)` }}
      >
        {images.map((src, i) => (
          <img
            key={i}
            src={src}
            className="w-full h-screen object-cover flex-shrink-0"
            alt=""
          />
        ))}
      </div>

      {/* Left & Right Arrows */}
      <button
        onClick={prevSlide}
        className="absolute z-9 top-1/2 left-4 transform -translate-y-1/2 bg-white/70 hover:bg-white/90 text-amber-950 p-2 rounded-full transition"
      >
        <ChevronLeftIcon className="h-6 w-6" />
      </button>

      <button
        onClick={nextSlide}
        className="absolute z-9 top-1/2 right-4 transform -translate-y-1/2 bg-white/70 hover:bg-white/90 text-amber-950 p-2 rounded-full transition"
      >
        <ChevronRightIcon className="h-6 w-6" />
      </button>

      {/* Bottom indicators */}
      <div className="absolute z-9 bottom-13 left-1/2 transform -translate-x-1/2 flex space-x-2">
        {images.map((_, i) => (
          <span
            key={i}
            className={`h-2 w-2 rounded-full transition ${
              i === index ? "bg-amber-950" : "bg-white/60"
            }`}
          />
        ))}
      </div>

      {/* View All Products Button */}
      <div className="absolute z-9 bottom-20 left-1/2 transform -translate-x-1/2">
        <Link
          className="bg-white/80 text-amber-950 px-6 py-2 rounded-md font-semibold hover:bg-white/90 transition"
          to="/menu"
        >
          View all products
        </Link>
      </div>
    </div>
  );
};

export default Slider;

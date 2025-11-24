import { useState, useEffect } from "react";

const images = [
  "/images/slider/image1.png",
  "/images/slider/image2.png",
  "/images/slider/image3.png",
  "/images/slider/image4.png",
  "/images/slider/image5.png",
  "/images/slider/image1.png",
  "/images/slider/image2.png",
  "/images/slider/image3.png",
  "/images/slider/image4.png",
  "/images/slider/image5.png",
];

export default function Slider() {
  const [index, setIndex] = useState(0);

  useEffect(() => {
    const interval = setInterval(() => {
      setIndex((prev) => (prev + 1) % images.length);
    }, 2000); // change every 2 seconds

    return () => clearInterval(interval);
  }, []);

  return (
    <div className="w-full overflow-hidden">
      <div
        className="flex transition-transform duration-700 ease-out"
        style={{ transform: `translateX(-${index * 100}%)` }}
      >
        {images.map((src, i) => (
          <img
            key={i}
            src={src}
            className="w-full h-[88vh] object-cover flex-shrink-0"
            alt=""
          />
        ))}
      </div>
    </div>
  );
}

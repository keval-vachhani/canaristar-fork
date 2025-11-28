const images = [
  "/images/infinite-slider/image1.png",
  "/images/infinite-slider/image2.png",
  "/images/infinite-slider/image3.png",
  "/images/infinite-slider/image4.png",
  "/images/infinite-slider/image5.png",
  "/images/infinite-slider/image6.png",
  "/images/infinite-slider/image7.png",
  "/images/infinite-slider/image8.png",
  "/images/infinite-slider/image9.png",
  "/images/infinite-slider/image10.png",
];

const InfiniteSlider = () => {
  return (
    <div className="overflow-hidden w-full bg-white py-4">
      <div className="flex gap-6 animate-infinite-scroll">
        {/* Original images */}
        {images.map((src, i) => (
          <img
            key={i}
            src={src}
            className="h-40 md:h-50 w-auto object-contain"
          />
        ))}

        {/* Duplicate images for seamless loop */}
        {images.map((src, i) => (
          <img
            key={"copy-" + i}
            src={src}
            className="h-32 w-auto object-contain"
          />
        ))}
      </div>
    </div>
  );
};

export default InfiniteSlider;

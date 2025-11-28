import { ShoppingCart } from "lucide-react"; // example icon
import { useState } from "react";
import InfiniteSlider from "../components/InfiniteSlider";
import Slider from "../components/Slider";

const products = [
  {
    name: "Dark Chocolate Truffle",
    price: 499,
    image: "/images/slider/image1.png",
  },
  {
    name: "Milk Chocolate Delight",
    price: 299,
    image: "/images/slider/image2.png",
  },
  {
    name: "Nutty Chocolate Bar",
    price: 399,
    image: "/images/slider/image3.png",
  },
  {
    name: "White Chocolate Heaven",
    price: 349,
    image: "/images/slider/image4.png",
  },
  {
    name: "Milk Chocolate Delight",
    price: 299,
    image: "/images/slider/image5.png",
  },
];

const testimonials = [
  { name: "Sonia", text: "Best chocolates ever! My family loves them." },
  { name: "Rahul", text: "Handmade and delicious. Highly recommend!" },
  { name: "Anita", text: "Perfect gift for my friends. Loved the packaging." },
];

export default function HomePage() {
  const [email, setEmail] = useState("");

  return (
    <div className="min-h-screen text-gray-200 text-xl bg-amber-50">
      <Slider />

      <InfiniteSlider />

      <section className="py-12 px-4">
        <h2 className="text-amber-900 font-bold text-center text-3xl mb-8">
          Our Special Products
        </h2>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {products.map((p, i) => (
            <div
              key={i}
              className="bg-white/80 text-amber-950 rounded-xl p-4 flex flex-col items-center shadow hover:shadow-lg transition"
            >
              <img
                src={p.image}
                alt={p.name}
                className="w-full aspect-square object-cover rounded-lg mb-4"
              />
              <h3 className="font-semibold text-lg">{p.name}</h3>
              <p className="text-green-600 font-bold">₹{p.price}</p>
              <button className="mt-3 bg-amber-900 text-white px-4 py-1 rounded hover:bg-amber-700 transition flex items-center gap-2">
                <ShoppingCart size={16} /> Add to Cart
              </button>
            </div>
          ))}
        </div>
      </section>

      <section className="py-12 px-4 bg-amber-100">
        <h2 className="text-amber-900 font-bold text-center text-3xl mb-8">
          About Us
        </h2>
        <div className="max-w-5xl mx-auto md:flex md:items-center md:gap-10">
          <img
            src="/images/slider/image1.png"
            alt="Chocolate making"
            className="w-full md:w-1/2 h-80 object-cover rounded-lg mb-6 md:mb-0"
          />
          <p className="text-gray-800 text-lg text-justify md:w-1/2">
            We craft the finest chocolates with passion and care. From bean to
            bar, every chocolate is handmade using premium ingredients, ensuring
            an unforgettable taste experience for you and your loved ones. We
            craft the finest chocolates with passion and care. From bean to bar,
            every chocolate is handmade using premium ingredients, ensuring an
            unforgettable taste experience for you and your loved ones.
          </p>
        </div>
      </section>

      <section className="py-12 px-4">
        <h2 className="text-amber-900 font-bold text-center text-3xl mb-8">
          What Our Customers Say
        </h2>
        <div className="max-w-5xl mx-auto grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-3">
          {testimonials.map((t, i) => (
            <div
              key={i}
              className="bg-white/80 text-amber-950 rounded-xl p-6 shadow hover:shadow-lg transition"
            >
              <p className="italic mb-4">"{t.text}"</p>
              <p className="font-semibold text-right">- {t.name}</p>
            </div>
          ))}
        </div>
      </section>

      <section className="py-12 px-4 bg-amber-200">
        <h2 className="text-amber-900 font-bold text-center text-3xl mb-4">
          Join Our Chocolate Club
        </h2>
        <p className="text-center text-gray-800 mb-6">
          Get the latest flavors and exclusive discounts delivered to your
          inbox.
        </p>
        <div className="flex flex-col sm:flex-row justify-center items-center gap-4 max-w-xl mx-auto">
          <input
            type="email"
            placeholder="Enter your email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="px-4 py-2 rounded-md border border-amber-900 flex-1 focus:outline-none text-black"
          />
          <button className="bg-amber-900 text-white px-6 py-2 rounded-md hover:bg-amber-700 transition">
            Subscribe
          </button>
        </div>
      </section>
    </div>
  );
}

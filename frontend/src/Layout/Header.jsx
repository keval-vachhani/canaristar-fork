import {
  CircleUser,
  Handbag,
  Info,
  Phone,
  ShoppingBag,
  ShoppingCart,
} from "lucide-react";
import { Link } from "react-router-dom";
import SearchBar from "../components/SearchBar";

const Header = () => {
  return (
    <div className="bg-[url('images/bg.jpg')] text-gray-300">
      <div className="flex justify-between items-center p-1 bg-amber-950/5 sticky top-0 z-50 backdrop-blur-[1px]">
        <Link to="/">
          <img src="images/logo.jpg" className="rounded-full h-14" />
        </Link>
        <Link to="/search">
          <SearchBar />
        </Link>
        <Link to="/shop">
          <div className="flex flex-col items-center">
            <ShoppingBag size={20} />
            <p className="text-sm">Shop</p>
          </div>
        </Link>

        <Link to="/about-us">
          <div className="flex flex-col items-center">
            <Info size={20} />
            <p className="text-sm">About Us</p>
          </div>
        </Link>

        <Link to="/cart">
          <div className="flex flex-col items-center">
            <ShoppingCart size={20} />
            <p className="text-sm">Cart</p>
          </div>
        </Link>

        <Link to="/orders">
          <div className="flex flex-col items-center">
            <Handbag size={20} />
            <p className="text-sm">Orders</p>
          </div>
        </Link>

        <Link to="/contact-us">
          <div className="flex flex-col items-center">
            <Phone size={20} />
            <p className="text-sm">Contact Us</p>
          </div>
        </Link>

        <Link to="/profile">
          <div className="flex flex-col items-center">
            <CircleUser size={20} />
            <p className="text-sm">Profile</p>
          </div>
        </Link>
      </div>
    </div>
  );
};

export default Header;

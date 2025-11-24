import { Search } from "lucide-react";
import React from "react";

const SearchBar = () => {
  return (
    <div className="bg-amber-800/50 text-white rounded-lg flex justify-center items-center">
      <input
        type="text"
        placeholder="Search Products..."
        className="outline-0 px-3 py-1"
      />
      <div className="px-2">
        <Search size={18} />
      </div>
    </div>
  );
};

export default SearchBar;

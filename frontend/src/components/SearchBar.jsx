import { Search } from "lucide-react";

const SearchBar = () => {
  return (
    <div className="border-b border-b-white/35 text-white flex justify-center items-center">
      <input
        type="text"
        placeholder="Search Products..."
        className="outline-0 px-3 py-1 w-64 lg:w-90"
      />
      <div className="px-2">
        <Search size={18} />
      </div>
    </div>
  );
};

export default SearchBar;

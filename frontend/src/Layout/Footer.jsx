const Footer = () => {
  const year = new Date().getFullYear();

  return (
    <footer className="py-12 px-4 bg-amber-900 text-white text-center">
      <h2 className="text-2xl font-bold mb-4">Contact Us</h2>
      <p>Email: support@....com</p>
      <p>Phone: +91 1234567890</p>
      <p className="mt-4">&copy; 2025 canaristar</p>
    </footer>
  );
};

export default Footer;

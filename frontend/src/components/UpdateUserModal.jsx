import { useEffect, useState } from "react";

const UpdateUserModal = ({ isOpen, onClose, user, onUpdate }) => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    mobile: "",
    address: {
      street: "",
      city: "",
      country: "",
      postalCode: "",
      state: "",
    },
  });

  useEffect(() => {
    if (user) {
      setFormData({
        name: user.name,
        email: user.email,
        mobile: user.mobile,
        address: {
          street: user.address?.street,
          city: user.address?.city,
          country: user.address?.country,
          postalCode: user.address?.postalCode,
          state: user.address?.state,
        },
      });
    }
  }, [user]);

  if (!isOpen) return null;

  const handleSubmit = (e) => {
    e.preventDefault();
    onUpdate({ ...user, ...formData });
    onClose();
  };

  return (
    <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 backdrop-blur-[3px]">
      <div className="bg-white max-h-[90vh] overflow-y-auto rounded-lg shadow-xl p-6 w-full max-w-md">
        <h2 className="text-xl font-bold mb-4">Update User</h2>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium">Name</label>
            <input
              type="text"
              className="w-full p-2 border rounded-md"
              value={formData.name}
              onChange={(e) =>
                setFormData({ ...formData, name: e.target.value })
              }
            />
          </div>

          <div>
            <label className="block text-sm font-medium">Email</label>
            <input
              type="email"
              className="w-full p-2 border rounded-md"
              value={formData.email}
              onChange={(e) =>
                setFormData({ ...formData, email: e.target.value })
              }
            />
          </div>

          <div>
            <label className="block text-sm font-medium">Mobile number</label>
            <input
              type="text"
              className="w-full p-2 border rounded-md"
              value={formData.mobile}
              onChange={(e) =>
                setFormData({ ...formData, mobile: e.target.value })
              }
            />
          </div>

          <div className="flex flex-col gap-3">
            <h2 className="block text-sm font-medium">Address</h2>

            {/* street */}
            <div>
              <label className="block text-xs font-medium">Street</label>
              <input
                type="text"
                className="w-full p-2 border rounded-md"
                value={formData.address?.street || ""}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    address: {
                      ...formData.address,
                      street: e.target.value,
                    },
                  })
                }
              />
            </div>

            {/* city */}
            <div>
              <label className="block text-xs font-medium">City</label>
              <input
                type="text"
                className="w-full p-2 border rounded-md"
                value={formData.address?.city || ""}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    address: {
                      ...formData.address,
                      city: e.target.value,
                    },
                  })
                }
              />
            </div>

            {/* state */}
            <div>
              <label className="block text-xs font-medium">State</label>
              <input
                type="text"
                className="w-full p-2 border rounded-md"
                value={formData.address?.state || ""}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    address: {
                      ...formData.address,
                      state: e.target.value,
                    },
                  })
                }
              />
            </div>

            {/* country */}
            <div>
              <label className="block text-xs font-medium">Country</label>
              <input
                type="text"
                className="w-full p-2 border rounded-md"
                value={formData.address?.country || ""}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    address: {
                      ...formData.address,
                      country: e.target.value,
                    },
                  })
                }
              />
            </div>

            {/* postalCode */}
            <div>
              <label className="block text-xs font-medium">Postal Code</label>
              <input
                type="text"
                className="w-full p-2 border rounded-md"
                value={formData.address?.postalCode || ""}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    address: {
                      ...formData.address,
                      postalCode: e.target.value,
                    },
                  })
                }
              />
            </div>
          </div>

          <div className="flex justify-end gap-3 pt-4">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 bg-gray-300 rounded-md hover:bg-gray-400"
            >
              Cancel
            </button>

            <button
              type="submit"
              className="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
            >
              Update
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default UpdateUserModal;

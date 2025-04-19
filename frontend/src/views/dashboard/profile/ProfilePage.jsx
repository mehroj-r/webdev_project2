import React from "react";
import { useTheme } from "@/context/ThemeContext";

const ProfilePage = () => {
  const { theme } = useTheme();
  const isDark = theme === "dark";

  return (
    <div
      className="profile-container h-full p-6 flex flex-col"
      data-theme={theme}
      style={{
        backgroundColor: isDark ? "#000" : "#fff",
        color: isDark ? "#f5f5f5" : "#000",
      }}
    >
      <h1 className="text-2xl font-bold mb-4">Profile</h1>
      <div className="flex-grow">
        <div className="flex flex-col">
          <div
            className={`p-4 rounded-lg ${
              isDark ? "bg-gray-900" : "bg-gray-100"
            }`}
          >
            <p>Profile content will be displayed here</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;

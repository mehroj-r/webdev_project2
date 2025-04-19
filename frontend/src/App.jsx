"use client";

import { BrowserRouter as Router } from "react-router-dom";

// Providers
import { AuthProvider } from "./context/AuthContext";
import { ThemeProvider } from "./context/ThemeContext";
import { BlogsProvider } from "./context/main/BlogsContext";
import { HashtagsProvider } from "./context/main/HashtagsContext";
import { ModalProvider } from "./context/main/ModalContext";
import { CommentsProvider } from "./context/main/CommentsContext";
import AppRoutes from "./routes";

const App = () => {
  return (
    <Router>
      <ThemeProvider>
        <AuthProvider>
          <BlogsProvider>
            <HashtagsProvider>
              <CommentsProvider>
                <ModalProvider>
                  <AppRoutes />
                </ModalProvider>
              </CommentsProvider>
            </HashtagsProvider>
          </BlogsProvider>
        </AuthProvider>
      </ThemeProvider>
    </Router>
  );
};

export default App;

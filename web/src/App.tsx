import { Routes, Route } from "react-router-dom";
import "./App.css";
import { DashboardLayout } from "./components/dashboard/DashboardLayout";
import { LandingPage } from "./components/LandingPage";
import { RequireAuth } from "./auth/RequireAuth";
import { OAuth2CallbackPage } from "./components/pages/OAuth2CallbackPage";

function App() {
  return (
    // <DashboardLayout />
    <Routes>
      <Route path="/" element={<LandingPage />} />
      <Route path="/auth/callback" element={<OAuth2CallbackPage />} />

      <Route
        path="/dashboard"
        element={
          <RequireAuth>
            <DashboardLayout />
          </RequireAuth>
        }
      />
    </Routes>
  );
}

export default App;

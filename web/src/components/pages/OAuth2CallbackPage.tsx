import { tokenStore } from "@/auth/token";
import { useUser } from "@/context/UserContext";
import { useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom"

export function OAuth2CallbackPage() {
  const navigate =  useNavigate();
  const [params] = useSearchParams();
  const { refreshUser } = useUser();

  useEffect(() => {
    const finalyze = async () => {
      const token = params.get("token");
      const error = params.get("error");

      if (token) {
        tokenStore.set(token);
        await refreshUser();
        navigate("/dashboard", { replace: true });
        return;
      }

      if (error) {
        navigate(`/?error=${encodeURIComponent(error)}`, { replace: true })
        return;
      }

      navigate("/", { replace: true });
    };

    finalyze();
  }, [navigate, params, refreshUser]);

  return (
    <p className="text-center py-10">Completing Google sign-in...</p>
  )
}
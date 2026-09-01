import { Navigate, Outlet, useLocation } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function ProtectedRoute({ roles }) {
  const { user } = useAuth();
  const location = useLocation();

  if (!user) return <Navigate to="/login" replace state={{ from: location }} />;
  if (roles && !roles.includes(user.role)) {
    const path = user.role === "DRIVER" ? "/driver" : user.role === "ADMIN" ? "/admin" : "/customer";
    return <Navigate to={path} replace />;
  }
  return <Outlet />;
}

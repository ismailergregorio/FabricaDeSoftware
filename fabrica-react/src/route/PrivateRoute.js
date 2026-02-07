import { Navigate } from "react-router-dom";
import { isAuthenticated } from "../utils/authService";

export default function PrivateRoute({ children }) {
  return isAuthenticated()
    ? children
    : <Navigate to="/" replace />;
}
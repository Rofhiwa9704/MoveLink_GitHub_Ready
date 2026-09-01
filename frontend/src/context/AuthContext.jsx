import { createContext, useContext, useMemo, useState } from "react";
import { authApi } from "../services/api";

const AuthContext = createContext(null);

function readUser() {
  const token = localStorage.getItem("token");
  if (!token) return null;
  return {
    token,
    role: localStorage.getItem("role"),
    email: localStorage.getItem("email"),
    userId: localStorage.getItem("userId"),
  };
}

export function AuthProvider({ children }) {
  const [user, setUser] = useState(readUser);

  const login = async (email, password) => {
    const data = await authApi.login({ email, password });
    localStorage.setItem("token", data.token);
    localStorage.setItem("role", data.role);
    localStorage.setItem("email", data.email);
    localStorage.setItem("userId", data.userId);
    const next = { token: data.token, role: data.role, email: data.email, userId: data.userId };
    setUser(next);
    return data;
  };

  const logout = () => {
    localStorage.clear();
    setUser(null);
  };

  const value = useMemo(() => ({ user, login, logout }), [user]);
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  return useContext(AuthContext);
}

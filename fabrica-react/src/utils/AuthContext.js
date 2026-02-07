import { createContext } from "react";

export const AuthContext = createContext();

export function AuthProvider({ children }) {

  const signIn = (token, nome,roles) => {
    localStorage.setItem("accessToken", token);
    localStorage.setItem("nome", nome);
    localStorage.setItem("roles", roles);
  };

  const signOut = () => {
    localStorage.clear();
  };

  return (
    <AuthContext.Provider value={{ signIn, signOut }}>
      {children}
    </AuthContext.Provider>
  );
}

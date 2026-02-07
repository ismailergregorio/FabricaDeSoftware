import api from "../services/api";

export const login = async (login, senha) => {
  const response = await api.post("/auth", {
    login,
    senha,
  });

  localStorage.setItem("accessToken", response.data.toker);
  localStorage.setItem("refreshToken", response.data.refreshToker);

  return response.data;
};

export const register = async (name, email, password) => {
  const response = await api.post("/auth/register", {
    name,
    email,
    password,
  });

  return response.data; // { nome, token }
};

export const logout = () => {
  localStorage.removeItem("accessToken");
  localStorage.removeItem("refreshToken");
  localStorage.removeItem("nome");
  localStorage.removeItem("reles");
};

export const isAuthenticated = () => {
  return !!localStorage.getItem("accessToken");
};

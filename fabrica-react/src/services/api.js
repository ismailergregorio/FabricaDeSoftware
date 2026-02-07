import axios from "axios";
import { toast } from "react-toastify";

const baseURL = "http://localhost:8080"

const api = axios.create({
  baseURL: baseURL,
});

export default api;

const refreshApi = axios.create({
  baseURL: baseURL,
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("accessToken");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

api.interceptors.response.use(
  (res) => res,
  async (error) => {
    // ❌ erro de conexão (backend fora)
    if (!error.response) {
      console.error("Backend indisponível");

      // opcional: aviso ao usuário
      toast.error("Servidor fora do ar");

      return Promise.reject({
        message: "Servidor indisponível",
      });
    }

    const originalRequest = error.config;
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      try {
        const refrash = localStorage.getItem("refreshToken");

        const resposta = await refreshApi.post("/auth/refresh", {
          refrash: refrash,
        });

        const { toker, refreshToker } = resposta.data;

        localStorage.setItem("accessToken", toker);
        localStorage.setItem("refreshToken", refreshToker);

        originalRequest.headers.Authorization = `Bearer ${toker}`;

        return api(originalRequest);
      } catch (err) {
        console.error("Erro no refresh", err);
        localStorage.clear();
        window.location.href = "/";
        return Promise.reject(err);
      }
    }

    return Promise.reject(error);
  },
);
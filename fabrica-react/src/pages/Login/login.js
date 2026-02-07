import "./css-login.css";
import { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { ToastContainer, toast } from 'react-toastify';

import { login, register } from "../../utils/authService";
import { AuthContext } from "../../utils/AuthContext";
import ErrosGat from "../../utils/tratamentoDeErro";

function Login() {
  // const notify = () => toast.error("Wow so easy!");
  const { signIn } = useContext(AuthContext);
  const navigate = useNavigate();
  localStorage.clear();

  const [aba, setAba] = useState("login"); // login | cadastro

  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  async function handleLogin(e) {
    e.preventDefault();
    try {
      const data = await login(email, password);
      signIn(data.toker, data.nome, data.roles);
      toast.success("Sucesso!");
      navigate("/home");
    } catch {
      toast.error("Erro, ao tentar fazer o login");
    }
  }

  async function handleRegister(e) {
    e.preventDefault();
    try {
      const data = await register(nome, email, password);
      signIn(data.token, data.nome);

      navigate("/home");
    } catch {
      alert("Erro ao cadastrar usuário");
    }
  }

  return (
    <div className="page">
      
      <img
        src="https://unisales.br/wp-content/uploads/2020/03/Logo-UniSales_Horizontal.png"
        alt="Logo"
      />
      {/* 🔐 LOGIN */}
      {aba === "login" && (
        <form className="formLogin" onSubmit={handleLogin}>
          <h1>Login</h1>

          <label>E-mail</label>
          <input
            type="text"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />

          <label>Senha</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />

          <button type="submit" className="btn">
            Acessar
          </button>
        </form>
      )}

      {/* 📝 CADASTRO */}
      {aba === "cadastro" && (
        <form className="formLogin" onSubmit={handleRegister}>
          <h1>Cadastrar</h1>

          <label>Nome</label>
          <input
            type="text"
            value={nome}
            onChange={(e) => setNome(e.target.value)}
            required
          />

          <label>E-mail</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />

          <label>Senha</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />

          <button type="submit" className="btn">
            Cadastrar
          </button>
        </form>
      )}
    </div>
  );
}

export default Login;

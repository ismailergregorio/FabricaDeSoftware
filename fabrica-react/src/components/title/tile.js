import "./stale-tile.css";
import { useState, useRef, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";

function Title({ Titulo }) {
  const [open, setOpen] = useState(false);
  const menuRef = useRef(null);
  const navigate = useNavigate();

  const nome = localStorage.getItem("nome");
  const roles = localStorage.getItem("roles");

  // Fecha ao clicar fora
  useEffect(() => {
    function handleClickOutside(event) {
      if (menuRef.current && !menuRef.current.contains(event.target)) {
        setOpen(false);
      }
    }
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  function logout() {
    localStorage.removeItem("token"); // JWT
    navigate("/");
  }

  return (
    <div className="title">
      <div className="textotitle">
        <h1>Fabrica de Software - {Titulo}</h1>
      </div>

      <div className="user" ref={menuRef}>
        <div className="user-btn" onClick={() => setOpen(!open)}>
          <i className="fa-solid fa-circle-user"></i>
        </div>

        {open && (
          <div className="user-menu">
            <strong>{nome}</strong>
            <span>{roles}</span>

            <hr />

            <Link to="/admin" className="linkUser">Gerenciar</Link>
            <button onClick={logout}>Sair</button>
          </div>
        )}
      </div>
    </div>
  );
}

export default Title;

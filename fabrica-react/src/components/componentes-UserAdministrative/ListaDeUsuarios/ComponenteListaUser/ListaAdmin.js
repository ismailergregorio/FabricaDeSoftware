import "./css-listaAdmin.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEllipsisVertical } from "@fortawesome/free-solid-svg-icons";
import { use, useEffect, useState } from "react";
import api from "../../../../services/api";
import { Link } from "react-router-dom";
export default function ListaAdmin() {
  const [user, setUser] = useState([]);
  function getUser() {
    api
      .get("/user")
      .then((a) => {
        setUser(a.data);
      })
      .catch((e) => {
        console.error(e, "erro na busca dos usuarios");
      });
  }

  useEffect(() => {
    getUser();
  }, []);

  return (
    <div className="ListaItensUser">
      <div className="legendasUser">
        <h2>Nome:</h2>
        <h2>Nivel de Acesso:</h2>
        <h2>Editar:</h2>
      </div>
      {user.map((usuario, index) => (
        <Link to={`/admin/${usuario.id}`}>
          <div className="itensUser" id={usuario.id}>
            <h1>{usuario.usuario}</h1>
            <h1>{usuario.role}</h1>
            <div className="btn_lista_Admin" id={"user" + usuario.id}>
              <button id="btn_idUser">Editar</button>
            </div>
          </div>
        </Link>
      ))}
    </div>
  );
}

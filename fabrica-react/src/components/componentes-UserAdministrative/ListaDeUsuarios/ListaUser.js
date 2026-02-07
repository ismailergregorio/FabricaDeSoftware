import "./css-ListaUser.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import ListaAdmin from "./ComponenteListaUser/ListaAdmin";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
export default function ListaUser() {
  return (
    <div className="listaUser">
      <div className="haderListaUser">
        <h1>Usuarios</h1>
        {localStorage.getItem("roles") === "ADMIN" && (
          <div className="addUser">
            <Link to="/admin/add" className="btnAdd">
              <FontAwesomeIcon icon={faPlus} className="icon" />
              <h2>Adicionar</h2>
            </Link>
          </div>
        )}
      </div>
      <ListaAdmin />
    </div>
  );
}

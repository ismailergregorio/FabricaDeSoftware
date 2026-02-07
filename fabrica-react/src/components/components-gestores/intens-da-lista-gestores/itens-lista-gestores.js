import { useEffect, useState } from "react";
import "./css-itens-lista-gestores.css"
import { Link } from "react-router-dom";
import api from "../../../services/api";
import { toast } from "react-toastify";
function ItenGestores() {
  const [gestores, setGestores] = useState([]);

  const getGestores = () => {
    api.get("/gestores/gestores")
      .then((res) => {
        setGestores(res.data);
      }).catch((err) => {
        console.error("Erro na Busca", err)
      })
  }
  async function deletarGestor(codigoGestor) {
    try {
      const resposta = window.confirm("Deseja realmente Deletar este Gestor ?");
      if (resposta) {
        await api.delete(`/gestores/gestor/${codigoGestor}`);
        toast.success("Gestor Deletado!");
        getGestores()
      }
    } catch (e) {
      toast.error("Não foi possivel realisar a operação, " + e.response.status);
    }
  }


  useEffect(() => {
    getGestores();
  }, [])

  return (
    <div className="lista-gestores">
      <h1>Gestores</h1>
      <div className="gestores">
        {gestores.length == 0 && <h1>Ainda não a Gestores</h1>}
        {gestores.map((gestor, i) => (
          <div className="info-gestores">
            <span>
              <i className="fa-solid fa-graduation-cap"></i> {gestor.name}
            </span>
            <div className="acoes">
              <span className="ra">(RA) {gestor.codigoGestor}</span>
              <Link to={`/formulario/gestores/${gestor.codigoGestor}`}>
                <button className="BtnFormate_2">Visualizar</button></Link>
                <button className="BtnFormate_2" id="btnDeletarGestor" onClick={()=>{deletarGestor(gestor.codigoGestor)}}>Deletar</button>
                
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
export default ItenGestores;
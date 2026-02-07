import "./css-lista-projetos.css";
import React, { useState, useEffect } from "react";
import api from "../../../services/api";
import { Link } from "react-router-dom";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

function ListaProjetos() {
  const navegate = useNavigate();
  const [projetos, setProjetos] = useState([]);
  const getProjetos = () => {
    api
      .get("/projetos/getprojetos")
      .then((res) => {
        setProjetos(res.data);
      })
      .catch((err) => {
        console.error("Erro ao buscar projetos:", err);
        toast.error("Erro na Busca," + err.response.status);
      });
  }

  useEffect(() => {
    getProjetos();
  }, []);

  async function deletarProjeto(codigoProjeto) {
    try {
      const resposta = window.confirm("Deseja realmente deletar este projeto?");
      if (resposta) {
        await api.delete(`/projetos/${codigoProjeto}`);
        toast.success("Projeto Deletado!");
        getProjetos();
      }
    } catch (e) {
      toast.error("Não foi possivel realisar a operação, " + e);
    }
  }

  return (
    <div className="listaProjetos">
      <h1>Projetos</h1>
      <ul>
        {projetos.length == 0 && <h1>Não projetos Diponivel</h1>}
        {projetos.map((projeto, index) => (
          <div
            className="projetos"
            key={projeto.codigoProjeto}
            id={projeto.codigoProjeto}
          >
            <img src={projeto.linkImage} alt={projeto.codigoProjeto} />
            <div className="descripiton">
              <h2>{projeto.nomeDoProjeto}</h2>
              <h3>{projeto.descricaoDoProjeto}</h3>
              <h3>{projeto.areaDeConhecimento}</h3>
            </div>
            <div className="descripiton2">
              <h3>Data inicial:{projeto.dataDeInicioDoProjeto}</h3>
              <h3>Data final:{projeto.dataDoFimDoProjeto}</h3>
              <div className="btnsListaProjetos">
                <button id="btnListaEditar" className="BtnListaProjetos" onClick={() => { navegate(`/projetos/formulario/${projeto.codigoProjeto}`) }}>Editar</button>
                <button id="btnListaDeletar" className="BtnListaProjetos" onClick={()=>{deletarProjeto(projeto.codigoProjeto)}}>Deletar</button>
              </div>
            </div>
          </div>
        ))}
      </ul>
    </div>
  );
}

export default ListaProjetos;

import "./css-lista-alunos.css"
import React, { useState, useEffect } from "react";
import api from "../../../services/api";
import { Link } from "react-router-dom";

function ListaAlunos() {
  const [alunos, setAlunos] = useState([]);
  const [alunosNaoAprovados, setAlunosNaoAprovados] = useState([]);

  useEffect(() => {
    api
      .get("/alunos/alunos")
      .then((res) => {
        setAlunos(res.data);
      })
      .catch((err) => {
        console.error("Erro ao buscar alunos:", err.response.status);
      });
  }, []);

  useEffect(() => {
    if (alunos.length > 0) {
      const filtrados = alunos.filter((x) => x.status === true);
      setAlunosNaoAprovados(filtrados);
    }
  }, [alunos]);

  return (
    <div className="listaAlunos">
      <h1>Alunos</h1>
      {alunosNaoAprovados.length === 0 && <h1>Ainda não possui alunos cadastrados</h1>}
      {alunosNaoAprovados.map((aluno, index) => (
        <div className="alunos">
          <div className="info-aluno">
            <span>
              <i className="fa-solid fa-graduation-cap"></i><span className="ra">(RA:{aluno.ra}) </span>{aluno.nome}
            </span>
            <div className="acoes">
              <Link to={`/formulario/aluno/${aluno.ra}`}>
                <button>Visualizar</button>
              </Link>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
}
export default ListaAlunos;

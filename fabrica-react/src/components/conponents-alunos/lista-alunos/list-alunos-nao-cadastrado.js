import "./css-lista-alunos.css"
import React, { useState, useEffect, use } from "react";
import { Link } from "react-router-dom";
import api from "../../../services/api";

function ListaAlunosNaoCadatrado() {
  const [alunos, setAlunos] = useState([]);
  const [alunosNaoAprovados, setAlunosNaoAprovados] = useState([]);

  useEffect(() => {
    api
      .get("/alunos/alunos")
      .then((res) => {
        setAlunos(res.data);
      })
      .catch((err) => {
        console.error("Erro ao buscar alunos:", err);
      });
  }, []);

  const reprovados = () => {
    if (alunos.length > 0) {
      const filtrados = alunos.filter((x) => x.status === false);
      setAlunosNaoAprovados(filtrados);
    }
  }

  useEffect(() => {
    reprovados();
  }, [alunos]);

  const aprovarAluno = (ra) => {
    const aluno = alunos.find((aluno) => aluno.ra === ra);
    const dados = aluno;

    dados.status = true;

    api.put(`/alunos/aluno/${ra}`,dados)
    reprovados();
  }

  return (
    <div className="listaAlunos">
      <h1>Alunos</h1>
      {alunosNaoAprovados.length == 0 && <h1>Ainda não possui alunos cadastrados</h1>}
      {alunosNaoAprovados.map((aluno, index) => (

        <div className="alunos">
          <div className="info-aluno">
            <span>
              <i className="fa-solid fa-graduation-cap"></i> {aluno.nome} - {aluno.curso}
            </span>
            <div className="acoes">
              <span className="ra">(RA:{aluno.ra})</span>
              <button onClick={() => { aprovarAluno(aluno.ra) }}>Aprovar</button>
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
export default ListaAlunosNaoCadatrado;

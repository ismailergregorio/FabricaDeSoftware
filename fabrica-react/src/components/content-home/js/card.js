import "../css/css-cards.css";
import { Link } from "react-router-dom";
import React, { useState, useEffect } from "react";
import api from "../../../services/api";
function Cards() {
  const [quantidadeDeAlunos, setQuantidadeDeAlunos] = useState([]);
  const [quantidadeDeProjetos, setQuantidadeDeProjetos] = useState([]);
  const [quantidadeDeGestores, setQuantidadeDeGestores] = useState([]);

  //Controle de quantidade de Projetos para o card de Projetos
  useEffect(() => {
    api
      .get("/alunos/alunos")
      .then((res) => {
        const filtrado = res.data.filter((alunos) => alunos.status === true);
        setQuantidadeDeAlunos(filtrado);
      })
      .catch((err) => {
        console.error("Erro ao buscar projetos:", err);
      });
  }, []);
  const quantidadeAlunos = quantidadeDeAlunos.length;

  //Controle de quantidade de Alunos para o card de alunos
  useEffect(() => {
    api
      .get("/projetos/getprojetos")
      .then((res) => {
        setQuantidadeDeProjetos(res.data);
      })
      .catch((err) => {
        console.error("Erro ao buscar projetos:", err);
      });
  }, []);
  const quantidadeProjetos = quantidadeDeProjetos.length;

  useEffect(() => {
    api
      .get("/gestores/gestores")
      .then((res) => {
        setQuantidadeDeGestores(res.data);
      })
      .catch((err) => {
        console.error("Erro ao buscar projetos:", err);
      });
  }, []);
  const quantidadedeGestores = quantidadeDeGestores.length;

  return (
    <div className="infoGeral">
      <link
        rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
      />
      <div class="cardinfoGeral" id="cardGestores">
        <Link to="/gestores">
          <div className="itensCard">
            <i className="fa-solid fa-people-roof"></i>
            <div className="info">
              <div className="txtCard">
                <h1>{quantidadedeGestores}</h1>
                <h2>Gestores</h2>
              </div>
            </div>
          </div>
        </Link>
      </div>
      <div className="cardinfoGeral" id="cardProjetos">
        <Link to="/alunos">
          <div className="itensCard">
            <i className="fa-solid fa-graduation-cap"></i>
            <div className="info">
              <div className="txtCard">
                <h1>{quantidadeAlunos}</h1>
                <h2>Alunos</h2>
              </div>
            </div>
          </div>
        </Link>
      </div>
      <div className="cardinfoGeral" id="cardAlunos">
        <Link to="/projetos">
          <div className="itensCard">
            <i className="fa-solid fa-sheet-plastic"></i>
            <div className="info">
              <div className="txtCard">
                <h1>{quantidadeProjetos}</h1>
                <h2>Projetos</h2>
              </div>
            </div>
          </div>
        </Link>
      </div>
    </div>
  );
}
export default Cards;

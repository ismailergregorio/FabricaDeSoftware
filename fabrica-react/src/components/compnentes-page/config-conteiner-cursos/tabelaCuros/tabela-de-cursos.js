import "./css-tabela-de-cuso.css";
import React, { useEffect, useState } from "react";
import axios from "axios";
import api from "../../../../services/api";
import { toast } from "react-toastify";

function TabelaDeCurso() {

  /* =========================
     CONTROLES E STATES
  ========================== */

  // Controle manual da abertura do formulário
  let abaDeCriacaoDeCurso = false;

  // Lista de cursos vindos da API
  const [cursos, setCursos] = useState([]);

  // Campos do formulário
  const [nomeDoCurso, setNomeDoCurso] = useState("");
  const [mensagem, setMensagem] = useState("");

  /* =========================
     BUSCA DE CURSOS
  ========================== */

  // Busca cursos cadastrados no backend
  const getCursos = async () => {
    api
      .get("/curso/curso")
      .then((res) => {
        setCursos(res.data);
      })
      .catch((err) => {
        toast.error("Erro:"+err.response.status+", Erro na operacão.")
      });
  };

  // Executa a busca ao montar o componente
  useEffect(() => {
    getCursos();
  }, []);

  /* =========================
     DELETAR CURSO
  ========================== */

  function DeletarCurso(codigoDoCurso) {
    const confirmar = window.confirm(
      `Tem certeza que deseja deletar o curso ${codigoDoCurso}?`
    );
    if (!confirmar) return;

    api
      .delete(`/curso/curso/${codigoDoCurso}`)
      .then(() => {
        // Remove o curso deletado do state
        setCursos((prevCurso) =>
          prevCurso.filter(
            (curso) => curso.codigoDoCurso !== codigoDoCurso
          )
        );
      })
      .catch((error) => {
        console.error("Erro ao deletar item:", error);
        toast.error("Erro:"+error.response.status+", Erro na operacão.")
      });
  }

  /* =========================
     CRIAÇÃO DE CURSO
  ========================== */

  const FormularioDeEnvioDeCursos = async (e) => {
    e.preventDefault();

    // Validação simples
    if (!nomeDoCurso.trim()) {
      setMensagem("Preencha o campo.");
      return;
    }

    try {
      const dados = { nomeDoCurso };

      await api.post(
        "/curso/curso",
        dados
      );

      await getCursos();
      setMensagem("Categoria criada com sucesso!");
      setNomeDoCurso("");
    } catch (error) {
      console.error(error);
      toast.error("Erro:"+error.response.status+", Erro na operacão.")
    }
  };

  /* =========================
     ABERTURA DO FORMULÁRIO
  ========================== */

  function AbriFormularioDeCriacaoDeCurso() {
    const aba = document.getElementById(
      "fomularioDeEnviodeCurso"
    );
    const botao = document.getElementById(
      "btnAdicionarCurso"
    );

    if (!abaDeCriacaoDeCurso) {
      aba.style.display = "block";
      botao.innerText = "Cancelar";
      abaDeCriacaoDeCurso = true;
    } else {
      aba.style.display = "none";
      botao.innerText = "Adicionar";
      abaDeCriacaoDeCurso = false;
      setNomeDoCurso("");
      setMensagem("");
    }
  }

  /* =========================
     RENDER
  ========================== */

  return (
    <div className="tabelaCursos">
      {/* Cabeçalho */}
      <div className="haderCursos">
        <h1>Categoria Cursos</h1>
        <button
          id="btnAdicionarCurso"
          onClick={AbriFormularioDeCriacaoDeCurso}
        >
          Adicionar
        </button>
      </div>

      {/* Formulário de criação */}
      <div
        className="fomularioEnviodeCurso"
        id="fomularioDeEnviodeCurso"
      >
        <form
          id="formularioEnvio"
          onSubmit={FormularioDeEnvioDeCursos}
        >
          <div className="imputC">
            <label>
              Nome do Curso:
              <input
                type="text"
                value={nomeDoCurso}
                onChange={(e) =>
                  setNomeDoCurso(e.target.value)
                }
              />
            </label>
          </div>

          <div className="btnEnvio">
            <button type="submit" className="save">
              Salvar
            </button>
            <h1>{mensagem}</h1>
          </div>
        </form>
      </div>

      {/* Lista de cursos */}
      <div className="ContentCursos">
        {cursos.map((curso) => (
          <div className="curso" key={curso.codigoDoCurso}>
            <div className="textCurso">
              <h1>{curso.codigoDoCurso}:</h1>
              <h1>{curso.nomeDoCurso}</h1>
            </div>

            <button
              onClick={() =>
                DeletarCurso(curso.codigoDoCurso)
              }
            >
              Deletar
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default TabelaDeCurso;

import "./css-tabela-img-cursos.css";
import React, { useState, useEffect } from "react";
import { CarregandoImagem } from "../../../../utils/funcoes";
import api from "../../../../services/api";
import { toast } from "react-toastify";

function TabelaImgCursos() {

  /* =========================
     STATES PRINCIPAIS
  ========================== */

  // Lista de imagens vindas da API
  const [imagens, setImagens] = useState([]);

  // Imagem temporária para pré-visualização
  const [img_teste, setImg_teste] = useState(null);

  // Controle do formulário (variável simples)
  let stadoCanpoDeTexto = false;

  /* =========================
     BUSCA DE IMAGENS
  ========================== */

  // Busca as imagens cadastradas no backend
  const getImagens = async () => {
    api
      .get("/imagemcurso/imagemcurso")
      .then((res) => {
        setImagens(res.data);
      })
      .catch((err) => {
        console.error("Erro ao buscar os dados", err);
        toast.error("Erro:"+err.response.status+", Erro na operacão.")
      });
  };

  // Executa a busca ao montar o componente
  useEffect(() => {
    getImagens();
  }, []);

  /* =========================
     DELETAR IMAGEM
  ========================== */

  function DeletImagen(id) {
    const confirmar = window.confirm(
      "Tem certeza que deseja deletar esta imagem?"
    );
    if (!confirmar) return;

    api
      .delete(`/imagemcurso/imagemcurso/${id}`)
      .then(() => {
        // Remove a imagem deletada do state
        setImagens((prevImagens) =>
          prevImagens.filter((img) => img.codigoImagem !== id)
        );
      })
      .catch((error) => {
        console.error("Erro ao deletar item:", error);
        toast.error("Erro:"+error.response.status+", Erro na operacão.")
      });
  }

  /* =========================
     FORMULÁRIO DE ENVIO
  ========================== */

  const [altImagem, setNomeDaImagem] = useState("");
  const [linkImagemCurso, setLinkDaImagem] = useState("");
  const [mensagem, setMensagem] = useState("");

  // Envio de nova imagem
  const FormularioDeEnvioDeImages = async (e) => {
    e.preventDefault();

    // Validação simples
    if (!altImagem.trim() || !linkImagemCurso.trim()) {
      setMensagem("Preencha todos os campos.");
      return;
    }

    try {
      const dados = { altImagem, linkImagemCurso };

      await api.post(
        "/imagemcurso/imagemcurso",
        dados
      );

      setMensagem("Imagem enviada com sucesso!");
      setNomeDaImagem("");
      setLinkDaImagem("");
      setImg_teste(null);

      getImagens();
      AdicionarImagem(); // fecha o formulário
    } catch (error) {
      console.error(error);
      toast.error("Erro:"+error.response.status+", Erro na operacão.")
    }
  };

  /* =========================
     CONTROLE DE ABERTURA
     DO FORMULÁRIO
  ========================== */

  function AdicionarImagem() {
    const formulario = document.getElementById(
      "formulatioDeEnvioDeImagemCurso"
    );
    const botao = document.getElementById("btnAdicionarImgCurso");
    const imgPreview = document.getElementById("id_img_teste");
    const itensSave = document.getElementById("id_itensSave");

    const estilo = window.getComputedStyle(itensSave);
    const estado = estilo.display;

    // Abre formulário
    if (estado === "none") {
      itensSave.style.display = "flex";
      formulario.style.display = "block";
      imgPreview.style.display = "block";
      botao.innerText = "Cancelar";
      stadoCanpoDeTexto = true;
    }
    // Fecha formulário
    else {
      itensSave.style.display = "none";
      formulario.style.display = "none";
      imgPreview.style.display = "none";
      botao.innerText = "Adicionar";
      setNomeDaImagem("");
      setLinkDaImagem("");
      setImg_teste(null);
      stadoCanpoDeTexto = false;
    }
  }

  /* =========================
     RENDER
  ========================== */

  return (
    <div className="tabelaImg">
      {/* Cabeçalho */}
      <div className="haderConteinerImagens">
        <h1>Categoria Cursos</h1>
        <button id="btnAdicionarImgCurso" onClick={AdicionarImagem}>
          Adicionar
        </button>
      </div>

      {/* Formulário */}
      <div
        className="formImagensCurso"
        id="formulatioDeEnvioDeImagemCurso"
      >
        <form onSubmit={FormularioDeEnvioDeImages}>
          <div className="itensSave" id="id_itensSave">
            <label>
              Nome do Curso:
              <input
                type="text"
                id="altImagem"
                value={altImagem}
                onChange={(e) => setNomeDaImagem(e.target.value)}
              />
            </label>

            <label>
              Link Imagem:
              <input
                type="text"
                value={linkImagemCurso}
                id="linkImagemCurso"
                onChange={(e) => {
                  setLinkDaImagem(e.target.value);
                  CarregandoImagem(
                    e,
                    "btn-salvar-img-categoria",
                    setImg_teste
                  );
                }}
              />
            </label>
          </div>

          <div className="btnEnvio">
            <button
              type="submit"
              className="save"
              id="btn-salvar-img-categoria"
              disabled
            >
              Salvar
            </button>
            <h1>{mensagem}</h1>
          </div>
        </form>
      </div>

      {/* Lista de imagens */}
      <div className="conteinerImgs">
        
        {/* Pré-visualização */}
        <div className="Img-teste" id="id_img_teste">
          <img
            src={
              img_teste ||
              "https://images.pexels.com/photos/1029622/pexels-photo-1029622.jpeg"
            }
            alt="Pré-visualização"
          />
          <div className="botonImage">
            <button
              disabled
              className="btn_delete_Img_categoria"
            >
              Deletar
            </button>
          </div>
        </div>
        {imagens.map((imagen) => (
          <div className="Img" key={imagen.codigoImagem}>
            <img
              src={imagen.linkImagemCurso}
              alt={imagen.altImagem}
            />
            <div className="botonImage">
              <button
                onClick={() =>
                  DeletImagen(imagen.codigoImagem)
                }
              >
                Deletar
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default TabelaImgCursos;

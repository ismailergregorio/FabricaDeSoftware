// Importa o CSS do carrossel
import "./css-carrossel.css";

// Importa e registra os elementos Web Components do Swiper
import { register } from "swiper/element/bundle";

// React e Hooks
import React, { useState, useEffect } from "react";

// Estilos padrão do Swiper
import "swiper/css";

// Biblioteca para requisições HTTP
import api from "../../../services/api";

import { toast } from "react-toastify";

// Registra os Web Components necessários do Swiper
register();


// --------------------------------------------------------------------
// Função para exibir ou esconder o formulário de adicionar nova imagem
// --------------------------------------------------------------------
function AdicionarImagenNova() {
  const formularioDeEnvioDeLink = document.getElementById(
    "formularioSemImagen"
  );
  const btnImagenCarrocel = document.getElementById("btnSemImagen");

  if (!formularioDeEnvioDeLink || !btnImagenCarrocel) {
    console.error("Elementos não encontrados.");
    return;
  }

  const estilo = window.getComputedStyle(formularioDeEnvioDeLink);
  const displayAtual = estilo.display;

  if (displayAtual === "none") {
    formularioDeEnvioDeLink.style.display = "flex";
    btnImagenCarrocel.innerHTML = "Cancelar";
  } else {
    formularioDeEnvioDeLink.style.display = "none";
    btnImagenCarrocel.innerHTML = "Editar";
  }
}

// --------------------------------------------------------------------
// COMPONENTE PRINCIPAL DO CARROSSEL
// --------------------------------------------------------------------
function Carrossel() {
  const [mensagem, setMensagem] = useState(); // mensagem de feedback
  const [imagensCarrosel, setImagensCarrosel] = useState([]); // lista de imagens
  const [imagem, setImagem] = useState(null);

  // --------------------------------------------------------------------
  // Função para exibir ou esconder o formulário de adicionar nova imagem
  // --------------------------------------------------------------------
  const estadoFormularioDeCarrosel = "none";
  function AdicionarImagenNova() {
    const formularioDeEnvioDeLink = document.getElementById(
      "formularioSemImagen"
    );
    const btnImagenCarrocel = document.getElementById("btnSemImagen");

    if (!formularioDeEnvioDeLink || !btnImagenCarrocel) {
      console.error("Elementos não encontrados.");
      return;
    }

    const estilo = window.getComputedStyle(formularioDeEnvioDeLink);
    const displayAtual = estilo.display;

    if (displayAtual === "none") {
      formularioDeEnvioDeLink.style.display = "flex";
      btnImagenCarrocel.innerHTML = "Cancelar";
    } else {
      formularioDeEnvioDeLink.style.display = "none";
      btnImagenCarrocel.innerHTML = "Editar";
      setImagem(null);
      document.getElementById("input_imagem_nova").value = ""
    }
  }

  // --------------------------------------------------------------------
  // Função para exibir ou esconder o formulário de edição de imagem
  // --------------------------------------------------------------------
  function AtulizarImagemCarrocel(codigo) {
    const formularioDeEnvioDeLink = document.getElementById("form" + codigo);
    const btnImagenCarrocel = document.getElementById("btn" + codigo);

    if (!formularioDeEnvioDeLink || !btnImagenCarrocel) {
      console.error("Elementos não encontrados.");
      return;
    }

    // Verifica o display atual
    const estilo = window.getComputedStyle(formularioDeEnvioDeLink);
    const displayAtual = estilo.display;

    // Alterna entre mostrar/ocultar o formulário
    if (displayAtual === "none") {
      formularioDeEnvioDeLink.style.display = "flex";
      btnImagenCarrocel.innerHTML = "Cancelar";
    } else {
      formularioDeEnvioDeLink.style.display = "none";
      btnImagenCarrocel.innerHTML = "Editar";
    }
  }

  function CarregaImagCarrossel(event, id, link) {
    const url = event.target.value.trim();
    const btn = document.getElementById(id);
    
    const listaAtualizada = imagensCarrosel.map((e) => {
      if (e.codigoImagem === id) {
        if (url != id && url.length > 0) {
          document.getElementById("id" + e.codigoImagem).disabled = false;
        }
        return { ...e, linkImagenCarrocel: link };
      }
      return e;
    });


    setImagensCarrosel(listaAtualizada);

  }

  function CarregandoImagem(event, id) {
    const url = event.target.value.trim();
    const btn = document.getElementById(id);

    if (url === "") {
      setImagem(null); // remove a imagem
      document.getElementById("btn-salvar").disabled = true;
      return;
    }

    // cria um objeto de imagem para validar o link
    const img = new Image();
    img.src = url;

    img.onload = () => {
      setImagem(url); // link é válido → mostra a imagem
      document.getElementById("btn-salvar").disabled = false;
    };

    img.onerror = () => {
      setImagem(null); // link inválido → não mostra nada
    };
  }

  // ---------------------------------------------------------------
  // Função que busca as imagens da API
  // ---------------------------------------------------------------
  const buscarImagens = async () => {
    api
      .get("/carrocel/carrocel_imagens")
      .then((res) => {
        setImagensCarrosel(res.data); // atualiza estado
      })
      .catch((err) => {
        console.error("Erro na busca da imagen", err);
        toast.error("Erro:"+err.response.status+", Erro na operacão.")
      });
  };

  // Carrega imagens ao iniciar o componente
  useEffect(() => {
    buscarImagens();
  }, []);

  // -----------------------------------------------------------------
  // Atualiza o link de uma imagem específica
  // -----------------------------------------------------------------
  const AtulizacaoDeImagem = async (codigoImagem) => {
    const input = document.getElementById("imput" + codigoImagem);
    const novoLink = input.value;

    if (!novoLink.trim()) {
      setMensagem("Por Favor insira um link!");
      return;
    }

    try {
      await api.put(`/carrocel/path/${codigoImagem}`, {
        linkImagenCarrocel: novoLink,
      });
    } catch (error) {
      console.error("Erro ao atualizar a imagem:", error);
      toast.error("Erro:"+error.response.status+", Erro na operacão.")
    }
  };

  // -----------------------------------------------------------------
  // Deleta uma imagem do carrossel
  // -----------------------------------------------------------------
  const DeletarImagem = async (codigo) => {
    try {
      await api.delete(`/carrocel/path/${codigo}`);
      setMensagem("Imagem deletada!");

      // Remove localmente sem recarregar tudo
      setImagensCarrosel((prev) =>
        prev.filter((img) => img.codigoImagem !== codigo)
      );
    } catch (error) {
      console.error("Erro ao atualizar a imagem:", error);
      toast.error("Erro:"+error.response.status+", Erro na operacão.")
    }
  };

  // -----------------------------------------------------------------
  // Estados e funções para adicionar nova imagem
  // -----------------------------------------------------------------
  const [linkImagemNova, setLinkImagemNova] = useState();
  const [ativado, setAtivado] = useState(true);

  // Adiciona nova imagem via POST
  const AddNovaImagem = async () => {
    const input = document.getElementById("input_imagem_nova");
    const novoLink = input.value;
    setLinkImagemNova(novoLink);

    const dados = {
      linkImagenCarrocel: novoLink,
      imagenAtivadaDesativada: ativado,
    };

    try {
      if (novoLink != "") {
        await api.post("/carrocel/addimagen", dados);
        input.value = "";
        await buscarImagens();
        setImagem(null);
      }
    } catch (error) {
      console.error("Erro ao atualizar a imagem:", error);
      toast.error("Erro:"+error.response.status+", Erro na operacão.")
    }
  };

  // -----------------------------------------------------------------
  // JSX DO COMPONENTE
  // -----------------------------------------------------------------
  return (
    <div className="carrossel">
      {/* Cabeçalho do carrossel */}
      <div className="heder-carrossel">
        <h2>Editar Imagens Carrossel</h2>

        {/* Parece ser um formulário, mas está escrito "from" */}
        <form className="quantidade-imagem-carrossel">
          <h4>Quantidade de Imagens</h4>
          <input type="number" id="quantidadeIMG" />
        </form>
      </div>

      {/* Container do Swiper */}
      <swiper-container
        slides-per-view="1"
        loop="true"
        autoplay="true"
        pagination="true"
        style={{ width: "100%", height: "300px" }}
      >
        {/* Renderiza cada imagem do carrossel */}
        {imagensCarrosel.map((imagenCarrosel) => (
          <swiper-slide>
            <div className="btnAtulisarCarroselEforlario">
              {/* Botão para exibir formulário */}
              <button
                className="btn-atualizar-txt"
                onClick={() =>
                  AtulizarImagemCarrocel(imagenCarrosel.codigoImagem)
                }
                id={"btn" + imagenCarrosel.codigoImagem}
              >
                Editar
              </button>

              {/* Formulário de edição */}
              <form
                id={"form" + imagenCarrosel.codigoImagem}
                className="FormAtulizarImgemCarrosel"
                onSubmit={(e) => {
                  e.preventDefault();
                  AtulizacaoDeImagem(imagenCarrosel.codigoImagem);
                }}
              >
                <div>
                  <label
                    htmlFor="Input_Carrosel_Formulario"
                    id="Input_Carrosel_Formulario"
                  >
                    URL da Imagem:
                  </label>
                  <input
                    type="text"
                    id={"imput" + imagenCarrosel.codigoImagem}
                    placeholder="URL"
                    defaultValue={imagenCarrosel.linkImagenCarrocel}
                    onChange={(e) =>
                      CarregaImagCarrossel(e, imagenCarrosel.codigoImagem, e.target.value)
                    }
                  />
                </div>

                <button type="submit" className="btn-atualizar-txt" id={"id" + imagenCarrosel.codigoImagem} disabled>
                  Salvar
                </button>

                <button
                  className="btn-atualizar-txt"
                  onClick={() => DeletarImagem(imagenCarrosel.codigoImagem)}
                >
                  Deletar
                </button>

                <h1>{mensagem}</h1>
              </form>
            </div>

            {/* Imagem exibida no slide */}
            <img
              id={"id" + imagenCarrosel.codigoImagem}
              src={imagenCarrosel.linkImagenCarrocel}
              alt="Imagem Carrossel"
            />
          </swiper-slide>
        ))}

        {/* Slide "sem imagem" */}
        <swiper-slide className="CarrocelSemImagen">
          <div className="form-carrosel">
            <h1>Sem Imagem</h1>

            <button onClick={AdicionarImagenNova} id="btnSemImagen">
              Adicionar Imagen
            </button>

            <form
              className="FormSemImagen"
              id="formularioSemImagen"
              onSubmit={(e) => {
                e.preventDefault();
                AddNovaImagem();
              }}
            >
              <label>Digite o link da Imagen</label>

              <input
                type="text"
                placeholder="link da Imagen"
                defaultValue={linkImagemNova}
                id="input_imagem_nova"
                onChange={(e) => CarregandoImagem(e, "btn-salvar")}
              />
              <button type="submit" id="btn-salvar" disabled>
                Salvar
              </button>
            </form>
          </div>
          <img
            src={
              imagem ||
              "https://images.pexels.com/photos/1029622/pexels-photo-1029622.jpeg"
            }
            alt="Imagem Carrossel"
          />
        </swiper-slide>
      </swiper-container>
    </div>
  );
}

// Exporta o componente
export default Carrossel;

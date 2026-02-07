import "./css-informacaodafabrica.css";
import { register } from "swiper/element/bundle";
import React, { useState, useEffect } from "react";
import "swiper/css";
import { habilitarBtn, CarregandoImagem } from "../../../utils/funcoes";
import api from "../../../services/api";
import { toast } from "react-toastify";

// Registra componentes do Swiper (carrossel)
register();

function InformacaoDaFabrica() {
  // Códigos de configuração que vêm da API
  const codigoDeConfiguracaoTextoInicial = "Configuracao Texto Inicial";
  const codigoDeConfiguracaoImagenInicial = "Configuracao Imagem Inicial";

  // Estado para mensagens de erro / avisos
  const [mensagem, setMensagem] = useState("");

  // Lista de configurações recebidas da API
  const [configuracoes, setConfiguracoes] = useState([]);

  // Busca os dados da API
  const getConfig = async () => {
    api
      .get("/config/config")
      .then((res) => {
        setConfiguracoes(res.data);
      })
      .catch((err) => {
        console.error("Erro ao solicitar os dados", err);
      });
  };

  // Dados separados, já filtrados por código
  const [img_sobre, setImg_sobre] = useState(null);
  const [text_sobre, setText_sobre] = useState(null);

  // Separa CONF3458 (imagem) e CONF3190 (texto)
  function separarDados() {
    const img = configuracoes.find(
      (e) => e.nomeConfig === codigoDeConfiguracaoImagenInicial,
    );
    if (img) setImg_sobre(img.valorSalvo);

    const text = configuracoes.find(
      (e) => e.nomeConfig === codigoDeConfiguracaoTextoInicial,
    );
    if (text) setText_sobre(text.valorSalvo);
  }

  // 1ª execução: busca dados uma vez
  useEffect(() => {
    getConfig();
  }, []);

  // Quando receber os dados da API → separa
  useEffect(() => {
    if (configuracoes.length > 0) {
      separarDados();
    }
  }, [configuracoes]);

  // Transformar lista de configs em objeto acessível por chave
  const configMap = configuracoes.reduce((acc, item) => {
    acc[item.codigoDaConfguracao] = item.valorSalvo;
    return acc;
  }, {});

  // Estado auxiliar para pré-visualização da imagem
  const [imgg, setImg] = useState(configMap.CONF3458);

  // Estado do texto de edição
  const [Input_Descripton, setInput_Descripton] = useState("");

  // Envia atualização do texto
  const funcaoDeEnvioDeAtulizacaoTexto = async () => {
    try {
      const dados = { valorSalvo: Input_Descripton };
      await api.put(
        `/config/config/${codigoDeConfiguracaoTextoInicial}`,
        dados,
      );
      getConfig();
    } catch (error) {
      console.error(error.response.status);
      if (error.response.status === 404) {
        const resposta = window.confirm(
          "Ops ouve um erro na atulisação do iten deseja recrialo?",
        );
        if (resposta) {
          criarConfiguracao(codigoDeConfiguracaoTextoInicial, Input_Descripton);
          getConfig();
        }
      }
      toast.error("Erro:"+error.response.status+", Erro na operacão.")
    }
  };

  async function criarConfiguracao(nomeConfg, valor) {
    try {
      const dados = { nomeConfig: nomeConfg, valorSalvo: valor };

      await api.post(`/config/config`, dados);
      getConfig();
    } catch (error) {
      console.error(error);
    }
  }

  // Estado da imagem de edição
  const [Input_Imagem, setInput_Imagem] = useState("");

  // Envia atualização da imagem
  const funcaoDeEnvioDeAtulizacaoImagen = async (e) => {
    e.preventDefault();

    if (!Input_Imagem.trim()) {
      setMensagem("Preencha todos os campos.");
      return;
    }

    try {
      const dados = { valorSalvo: Input_Imagem };
      await api.put(
        `/config/config/${codigoDeConfiguracaoImagenInicial}`,
        dados,
      );
      getConfig();
    } catch (error) {
      console.error(error.response.status);
      if (error.response.status === 404) {
        const resposta = window.confirm(
          "Ops ouve um erro na atulisação do iten deseja recrialo?",
        );
        if (resposta) {
          criarConfiguracao(codigoDeConfiguracaoImagenInicial, Input_Imagem);
          getConfig();
        }
      }

      toast.error("Erro:"+error.response.status+", Erro na operacão.")
    }
  };

  // 🔧 Mostra/esconde campo de edição do texto (DOM manual)
  function EditarTexto() {
    const textDefinido = document.getElementById("Descripton");
    const inputText = document.getElementById("formularioDeAtulizacaoDeTexte");
    const btnCancelar = document.getElementById("btn-atualizar-txt");
    const btnAtulizarText = document.getElementById("AtualizarText");

    const estadoAtual = inputText.style.display;

    if (estadoAtual === "none" || estadoAtual === "") {
      btnAtulizarText.style.display = "block";
      textDefinido.style.display = "none";
      inputText.style.display = "block";
      btnCancelar.innerText = "Cancelar";
    } else {
      btnAtulizarText.style.display = "none";
      textDefinido.style.display = "block";
      inputText.style.display = "none";
      btnCancelar.innerText = "Editar";
    }
  }

  // 🔧 Mostra/esconde campo de edição da imagem (DOM manual)
  let stetBtnImagenEditor = false;
  function EditarImagen() {
    const conteinerEditorImagen = document.getElementById("FormAtulizarImagen");
    const btnImagem = document.getElementById("btn-img");

    if (!stetBtnImagenEditor) {
      conteinerEditorImagen.style.display = "flex";
      btnImagem.innerHTML = "Cancelar";
      stetBtnImagenEditor = true;
    } else {
      conteinerEditorImagen.style.display = "none";
      btnImagem.innerHTML = "Editar Imagem";
      stetBtnImagenEditor = false;
    }
  }

  return (
    <div className="informacao">
      <div className="heder-carrossel" id="textSobre">
        <h2>Editar Campo Sobre</h2>
      </div>

      <div className="conteudo-informacao">
        {/* SEÇÃO DO TEXTO */}
        <div className="text">
          <div className="info-text">
            <h1>Fábrica de Software – Projeto Acadêmico</h1>

            {/* Texto principal */}
            <h2 id="Descripton">{text_sobre || "Carregando texto..."}</h2>

            {/* Formulário oculto para edição */}
            <div
              className="formularioDeAtulizacaoDeTexte"
              id="formularioDeAtulizacaoDeTexte"
            >
              <form
                id="formularioDeTextoInicial"
                onSubmit={funcaoDeEnvioDeAtulizacaoTexto}
              >
                <label htmlFor="Input_Descripton">Texto de Apresentação:</label>

                <textarea
                  id="Input_Descripton"
                  name="Input_Descripton"
                  rows="10"
                  cols="40"
                  value={Input_Descripton}
                  onChange={(e) => {
                    setInput_Descripton(e.target.value);
                    habilitarBtn(
                      e.target.value,
                      configMap.codigoDeConfiguracaoTextoInicial,
                      "AtualizarText",
                    );
                  }}
                  placeholder={text_sobre}
                />

                <h1>{mensagem}</h1>
              </form>
            </div>

            {/* Botões */}
            <div className="btns-infomacao">
              <button
                className="btn-atualizar-txt"
                id="btn-atualizar-txt"
                onClick={EditarTexto}
              >
                Editar
              </button>

              <button
                type="submit"
                id="AtualizarText"
                className="btn-atualizar-txt"
                onClick={funcaoDeEnvioDeAtulizacaoTexto}
                style={{ display: "none" }}
              >
                Salvar
              </button>
            </div>
          </div>
        </div>

        {/* SEÇÃO DA IMAGEM */}
        <div className="Img-informacao-fabrica">
          <img
            src={
              img_sobre ||
              "https://images.pexels.com/photos/1029622/pexels-photo-1029622.jpeg"
            }
            alt="Logo-Fabrica-de-Software"
          />

          <div className="EditorDeImagen">
            <button
              id="btn-img"
              className="btn-atualizar-txt"
              onClick={EditarImagen}
            >
              Editar Imagem
            </button>

            <form
              id="FormAtulizarImagen"
              onSubmit={funcaoDeEnvioDeAtulizacaoImagen}
            >
              <div>
                <label htmlFor="Input_Imagem">URL da Imagem:</label>

                <input
                  type="text"
                  name="Input_Imagem"
                  placeholder={img_sobre}
                  value={Input_Imagem}
                  onChange={(e) => {
                    setInput_Imagem(e.target.value);
                    CarregandoImagem(e, "btn-salva-imagem", setImg_sobre);
                  }}
                />
              </div>

              <button
                className="btn-atualizar-txt"
                id="btn-salva-imagem"
                type="submit"
                disabled
              >
                Salvar
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default InformacaoDaFabrica;

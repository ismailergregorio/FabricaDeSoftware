import { useEffect, useState } from "react";
import "../formulario-gestores/css-formulario-criacao-gestores.css";
// import "../../../pages/css-master.css"
import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import api from "../../../services/api";
import { toast } from "react-toastify";

function FormularioGestores() {

  const retornoGestores = useNavigate();
  const [projetos, setProjetos] = useState([]);
  const [listaProjetos, setListaProjetos] = useState([]);

  const { codigoGestor } = useParams();
  const [gestor, setGestor] = useState({});

  // campos do formulário
  const [nome, setNome] = useState("");
  const [curso, setCurso] = useState("");
  const [descricao, setDescricao] = useState("");
  const [url, setUrl] = useState("");
  const [projeto, setProjeto] = useState(null);

  const [btnAddProjetos, setBtnAddProjetos] = useState(false);
  const [TituloStado, setTituloStado] = useState("Salvar");
  const [img_alternativa, setImg_alternativa] = useState(
    "https://images.pexels.com/photos/1029622/pexels-photo-1029622.jpeg",
  );

  // Buscar gestor
  const getGestor = () => {
    api
      .get(`/gestores/gestor/${codigoGestor}`)
      .then((res) => {
        setGestor(res.data);
      })
      .catch((err) => {
        console.error("Dados Não Encontrado", err);
        toast.error(err);
      });
  };

  // Buscar projetos
  const getProjeto = () => {
    api
      .get("/projetos/getprojetos")
      .then((res) => {
        setProjetos(res.data);
      })
      .catch((err) => {
        console.error("Erro na busca dos Projetos", err);
        toast.error(err);
      });
  };

  // Chama APIs
  useEffect(() => {
    if (codigoGestor) {
      getGestor();
      setTituloStado("Atualisar");
    }
    getProjeto();
  }, [codigoGestor]);

  // Preenche os inputs com dados do gestor quando carregar
  useEffect(() => {
    if (gestor) {
      setNome(gestor.name || "");
      setCurso(gestor.cursoResposavel || "");
      setDescricao(gestor.descricao || "");
      setUrl(gestor.linkImagenGestor || "");
    }
  }, [gestor]);

  // Monta lista de projetos do gestor quando ambos (gestor + projetos) já tiverem carregado
  useEffect(() => {
    if (gestor?.projetos && projetos.length > 0) {
      const lista = gestor.projetos
        .map((codigo) => projetos.find((p) => p.codigoProjeto === codigo))
        .filter((p) => p); // remove nulls
      setListaProjetos(lista);
    }
  }, [gestor, projetos]);

  // abrir/fechar lista de projetos
  const [stado, setStado] = useState("fa-regular fa-plus");
  function abriAddFormularioA() {
    const c = document.getElementById("ListaProjetosJestores");

    if (!btnAddProjetos) {
      c.style.display = "flex";
      setBtnAddProjetos(true);
      setStado("fa-solid fa-minus");
    } else {
      c.style.display = "none";
      setBtnAddProjetos(false);
      setStado("fa-regular fa-plus");
    }
  }
  //PUT
  const putGestores = async () => {
    const codigo = listaProjetos.flat().map((item) => item.codigoProjeto);
    const dados = {
      name: nome,
      descricao: descricao,
      cursoResposavel: curso,
      linkImagenGestor: url,
      projetos: codigo,
    };
    await api
      .put(`/gestores/gestor/${codigoGestor}`, dados)
      .then((res) => {toast.success(`Gestor Atulizado ${codigoGestor}`) })
      .catch((err) => {
        console.error("Erro:", err);
        toast.error(err);
      });

    retornoGestores("/gestores");
  };

  // POST
  const postGestores = async () => {
    const dados = {
      name: nome,
      descricao: descricao,
      cursoResposavel: curso,
      linkImagenGestor: url,
      projetos: listaProjetos.map((projeto) => projeto.codigoProjeto),
    };
    await api
      .post("/gestores/addgestores", dados)
      .then((res) => console.log("Resposta:", res.data))
      .catch((err) => {
        console.error("Erro:", err);
        toast.error(err);
      });

    retornoGestores("/gestores");
  };

  // Adicionar projeto na lista
  const addNovaListaProjetosGestor = (codigo) => {
    const projetoSelecionado = projetos.find((f) => f.codigoProjeto === codigo);
    if (
      projetoSelecionado &&
      !listaProjetos.some((p) => p.codigoProjeto === codigo)
    ) {
      setListaProjetos([...listaProjetos, projetoSelecionado]);
    }
  };

  // Remover projeto da lista
  const removeNovaLista = (codigo) => {
    const projetoSelecionadoRemovido = listaProjetos.filter(
      (f) => f.codigoProjeto !== codigo,
    );
    setListaProjetos(projetoSelecionadoRemovido);
  };

  // desabilitar botões já selecionados
  useEffect(() => {
    projetos.forEach((desabilitar) => {
      const desativarBtn = document.getElementById(desabilitar.codigoProjeto);

      if (!desativarBtn) return;

      // Reset
      desativarBtn.disabled = false;
      desativarBtn.style.backgroundColor = "";
      desativarBtn.style.color = "";
      desativarBtn.style.cursor = "pointer";

      listaProjetos.forEach((item) => {
        if (desabilitar.codigoProjeto === item.codigoProjeto) {
          desativarBtn.disabled = true;
          desativarBtn.style.backgroundColor = "gray";
          desativarBtn.style.color = "white";
          desativarBtn.style.cursor = "not-allowed";
        }
      });
    });
  }, [listaProjetos, projetos]);
  const [estadoAbaImag, setAstadoAbaImag] = useState(false);
  function imgAtivada() {
    if (url.length > 0 && url !== img_alternativa) {
      setAstadoAbaImag(true);
    } else {
      setAstadoAbaImag(false);
    }
  }

  async function deletarGestor() {
    try {
      const resposta = window.confirm("Deseja realmente Deletar este Gestor ?");
      if (resposta) {
        await api.delete(`/gestores/gestor/${codigoGestor}`);
        toast.success("Gestor Deletado!");
        retornoGestores("/gestores")
      }
    } catch (e) {
      toast.error("Não foi possivel realisar a operação, " + e.response.status);
      retornoGestores("/gestores");
    }
  }

  useEffect(() => {
    imgAtivada();
  }, [url]);

  return (
    <div className="content">
      <h2 id="h2_titulo_formulario">Cadastro de Gestores</h2>
      <form
        onSubmit={(e) => {
          e.preventDefault();
          if (codigoGestor) {
            putGestores();
          } else {
            postGestores();
          }
        }}
      >
        <label htmlFor="nome">Nome do Gestor</label>
        <input
          type="text"
          id="nome"
          value={nome}
          onChange={(e) => {
            setNome(e.target.value);
          }}
          name="nome"
          required
        />

        <label htmlFor="curso">Curso Responsável</label>
        <input
          type="text"
          id="curso"
          value={curso}
          onChange={(e) => {
            setCurso(e.target.value);
          }}
          name="curso"
          required
        />

        <label htmlFor="descricao">Descrição</label>
        <textarea
          id="descricao"
          value={descricao}
          onChange={(e) => {
            setDescricao(e.target.value);
          }}
          name="descricao"
          required
        ></textarea>

        <div className="addMaisG">
          <label htmlFor="professor">Adicionar Projeto</label>
          <div className="textBtnAdicionar">
            <h2>Adicionar:</h2>
            <button
              alt="Adicionar"
              type="button"
              className={`BtnFormate_1 ${stado}`}
              id="btnAddProjeto"
              onClick={(e) => {
                e.preventDefault();
                abriAddFormularioA();
              }}
            ></button>
          </div>
        </div>

        <div className="listaDeItens" id="ListaProjetosJestores">
          {projetos.map((projeto, index) =>
            projeto ? (
              <button
                className="btnSelector"
                key={index}
                id={projeto.codigoProjeto}
                onClick={(e) => {
                  e.preventDefault();
                  addNovaListaProjetosGestor(projeto.codigoProjeto);
                }}
              >
                <h1>{projeto.nomeDoProjeto}</h1>
                <h1>Cod:{projeto.codigoProjeto}</h1>
              </button>
            ) : null,
          )}
        </div>

        <div className="inputsLista">
          {listaProjetos.map((i) => (
            <div className="select" key={i.codigoProjeto}>
              <div className="TextSelect">
                <h1>{i.nomeDoProjeto}</h1>
                <h1>{i.codigoProjeto}</h1>
              </div>
              <button
                onClick={(e) => {
                  e.preventDefault();
                  removeNovaLista(i.codigoProjeto);
                }}
                className="fa-solid fa-xmark"
                id="btn_itens"
              ></button>
            </div>
          ))}
        </div>

        <label htmlFor="url">URL Imagen</label>
        <input
          type="text"
          id="url"
          value={url}
          onChange={(e) => {
            setUrl(e.target.value);
          }}
          name="url"
          required
        />
        {estadoAbaImag && (
          <div className="img_div">
            <img
              className="img_Gestor"
              src={url || img_alternativa}
              alt="Img Gestor"
            />
          </div>
        )}

        <button type="submit" id="btnGestor">{TituloStado}</button>
        {(localStorage.getItem("roles") === "ADMIN" || localStorage.getItem("USER_N1") === "ADMIN") && codigoGestor &&
          (<button id={"btnDeletarGestor"} onClick={() => { deletarGestor() }}>Deletar</button>)}
      </form>
    </div>
  );
}

export default FormularioGestores;

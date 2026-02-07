import "./css-formulario-projetos.css";
import react, { useState, useEffect } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import InputItens from "./ComponenteAdicionadoFormulario/inputItens";
import api from "../../../services/api";
import { toast } from "react-toastify";

function FormularioProjetos() {
  const { codigoProjeto } = useParams();
  const retornoProjetos = useNavigate();
  const hoje = new Date().toISOString().split("T")[0];

  const [dadosProjeto, setDadosProjeto] = useState({});

  const [nomeFormularioProjetos, setDadosFormulario] = useState("");
  const [descricaoFormularioProjetos, setDescricaoFormularioProjetos] =
    useState("");
  const [areaDeComnhecimento, setAreaDeComnhecimento] = useState("");
  const [dataDeInicioDoProjeto, setDataDeInicioDoProjeto] = useState("");
  const [dataDoFimDoProjeto, setDataDoFimDoProjeto] = useState("");
  const [linkGit, setLinkGit] = useState("");
  const [linkImage, setLinkImage] = useState("");

  const [alunosAdicionados, setAlunosAdicionados] = useState([]);
  const [gestoresAdicionados, setGestoresAdicionados] = useState([]);
  const [areaDeConhecimento, setAreaDeConhecimento] = useState([]);

  /* ===================== BUSCA PROJETO (EDIÇÃO) ===================== */
  useEffect(() => {
    if (codigoProjeto) {
      getProjeto();
    }
  }, [codigoProjeto]);

  const getProjeto = async () => {
    try {
      const res = await api.get(`/projetos/getprojetos/${codigoProjeto}`);
      setDadosProjeto(res.data);
    } catch (err) {
      console.error("Erro ao buscar projeto", err);
      toast.error("Erro ao buscar projeto" + err);
    }
  };

  async function getAreaDeConhecimento() {
    try {
      const res = await api.get(`/curso/curso`);
      setAreaDeConhecimento(res.data);
    } catch (err) {
      console.error("Erro ao buscar projeto", err);
      toast.error("Erro na bucas de Areas de Conhecimento" + err);
    }
  }

  /* ===================== PREENCHE FORMULÁRIO ===================== */
  useEffect(() => {
    if (codigoProjeto && dadosProjeto) {
      setDadosFormulario(dadosProjeto.nomeDoProjeto || "");
      setDescricaoFormularioProjetos(dadosProjeto.descricaoDoProjeto || "");
      setAreaDeComnhecimento(dadosProjeto.areaDeConhecimento || "");
      setDataDeInicioDoProjeto(dadosProjeto.dataDeInicioDoProjeto || "");
      setDataDoFimDoProjeto(dadosProjeto.dataDoFimDoProjeto || "");
      setLinkGit(dadosProjeto.linkGit || "");
      setLinkImage(dadosProjeto.linkImage || "");

      setAlunosAdicionados(dadosProjeto.alunosParticipantesDoProjeto || []);
      setGestoresAdicionados(dadosProjeto.profesorOrientador || []);
    }
  }, [dadosProjeto]);

  /* ===================== LISTAS ===================== */
  const [aluno, setAlunos] = useState([]);
  const [gestores, setGestores] = useState([]);
  const [imagensCurso, setImagensCurso] = useState([]);

  useEffect(() => {
    getAlunos();
    getGestores();
    getImagesCursos();
    getAreaDeConhecimento();
  }, []);

  const getAlunos = async () => {
    const res = await api.get("/alunos/alunos");
    setAlunos(res.data);
  };

  const getGestores = async () => {
    const res = await api.get("/gestores/gestores");
    setGestores(res.data);
  };

  const getImagesCursos = async () => {
    const res = await api.get("/imagemcurso/imagemcurso");
    setImagensCurso(res.data);
  };

  /* ===================== SALVAR (CRIAR / EDITAR) ===================== */
  const salvarProjeto = async () => {
    const dados = {
      nomeDoProjeto: nomeFormularioProjetos,
      descricaoDoProjeto: descricaoFormularioProjetos,
      areaDeConhecimento: areaDeComnhecimento,
      dataDeInicioDoProjeto: dataDeInicioDoProjeto,
      dataDoFimDoProjeto: dataDoFimDoProjeto,
      alunosParticipantesDoProjeto: alunosAdicionados,
      profesorOrientador: gestoresAdicionados,
      linkGit: linkGit,
      linkImage: linkImage,
    };

    try {
      if (codigoProjeto) {
        await api.put(`/projetos/${codigoProjeto}`, dados);
      } else {
        await api.post("/projetos/addprojetos", dados);
      }

      retornoProjetos("/projetos");
    } catch (err) {
      console.error("Erro ao salvar projeto", err);
      toast.error("Erro ao salvar projeto" + err);
    }
  };
  /* ===================== DELETAR PROJETO ===================== */
  async function deletarProjeto() {
    try {
      const resposta = window.confirm("Deseja realmente deletar este projeto?");
      if (resposta) {
        await api.delete(`/projetos/${codigoProjeto}`);
        toast.success("Projeto Deletado!");
        retornoProjetos("/projetos");
      }
    } catch (e) {
      toast.error("Não foi possivel realisar a operação, " + e);
      retornoProjetos("/projetos");
    }
  }

  /* ===================== RENDER ===================== */
  return (
    <div className="content">
      <h2>Cadastro de Projeto</h2>

      <form
        onSubmit={(e) => {
          e.preventDefault();
          salvarProjeto();
        }}
      >
        <label>Nome do Projeto</label>
        <input
          type="text"
          value={nomeFormularioProjetos}
          onChange={(e) => setDadosFormulario(e.target.value)}
          required
        />

        <label>Descrição</label>
        <textarea
          value={descricaoFormularioProjetos}
          onChange={(e) => setDescricaoFormularioProjetos(e.target.value)}
          required
        />

        <label>Area de Conhecimento</label>
        <select
          value={areaDeComnhecimento}
          onChange={(e) => setAreaDeComnhecimento(e.target.value)}
          required
        >
          <option value="">Selecione um projeto</option>
          {areaDeConhecimento.map((area) => (
            <option key={area.codigoDoCurso} value={area.nomeDoCurso}>
              {area.nomeDoCurso}
            </option>
          ))}
        </select>

        <div className="duplo">
          <div>
            <label>Data de Início</label>
            <input
              type="date"
              value={dataDeInicioDoProjeto}
              min={hoje}
              onChange={(e) => {
                setDataDeInicioDoProjeto(e.target.value);

                // se a data final for menor, limpa
                if (dataDoFimDoProjeto && e.target.value > dataDoFimDoProjeto) {
                  setDataDoFimDoProjeto("");
                }
              }}
              required
            />
          </div>

          <div>
            <label>Data de Término</label>
            <input
              type="date"
              min={dataDeInicioDoProjeto || hoje}
              value={dataDoFimDoProjeto}
              onChange={(e) => setDataDoFimDoProjeto(e.target.value)}
              disabled={!dataDeInicioDoProjeto}
              required
            />
          </div>
        </div>

        <InputItens
          Titulo={"Profesor/Orientador:"}
          dadosEntrada={gestores}
          chave1={"codigoGestor"}
          chave2={"name"}
          dadosInceridos={dadosProjeto.profesorOrientador}
          onChange={setGestoresAdicionados}
        />

        <InputItens
          Titulo={"Aluno:"}
          dadosEntrada={aluno}
          chave1={"ra"}
          chave2={"nome"}
          dadosInceridos={dadosProjeto.alunosParticipantesDoProjeto}
          onChange={setAlunosAdicionados}
        />

        <label>Link do Repositório</label>
        <input
          type="url"
          value={linkGit}
          onChange={(e) => setLinkGit(e.target.value)}
        />

        <label>Imagem Inicial</label>
        <div className="imgSelect">
          {imagensCurso.map((img) => (
            <div className="Select" key={img.codigoImagem}>
              <label>
                <img src={img.linkImagemCurso} alt={img.altImagem} />

                <input
                  type="radio"
                  name="imagensCursos"
                  value={img.linkImagemCurso}
                  checked={linkImage === img.linkImagemCurso}
                  onChange={(e) => setLinkImage(e.target.value)}
                />
              </label>
            </div>
          ))}
        </div>

        <button type="submit" id="btnSalvarAlteraProjeto">
          {codigoProjeto ? "Salvar Alterações" : "Criar Projeto"}
        </button>
        {((codigoProjeto && localStorage.getItem("roles") == "ADMIN") ||
          localStorage.getItem("roles") == "USER_N1") && (
          <button
            id="btnDeleteProjeto"
            onClick={() => {
              deletarProjeto();
            }}
          >
            Deletar
          </button>
        )}
      </form>
    </div>
  );
}

export default FormularioProjetos;

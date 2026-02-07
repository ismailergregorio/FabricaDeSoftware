import "./css-formulario-alunos.css";
import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../../../services/api";
import { toast } from "react-toastify";

function FormularioAlunos() {
  const navigate = useNavigate();
  const { ras } = useParams();

  // Lista geral de projetos
  const [projetos, setProjetos] = useState([]);

  const [cursos, setCursos] = useState([]);

  // Aluno carregado (edição)
  const [aluno, setAluno] = useState(null);

  // Estados do formulário (controlados)
  const [ra, setRa] = useState("");
  const [emailInstitucional, setEmailInstitucional] = useState("");
  const [nome, setNome] = useState("");
  const [curso, setCurso] = useState("");
  const [motivoDaInscricao, setMotivoDaInscricao] = useState("");
  const [projetoSelecionado, setProjetoSelecionado] = useState("");

  // 🔹 Buscar projetos
  const getProjetos = async () => {
    try {
      const res = await api.get("/projetos/getprojetos");
      setProjetos(res.data);
    } catch (erro) {
      toast.error("Erro na Busca " + erro.response.status);
    }
  };

  const getCursos = async () => {
    try {
      const res = await api.get("/curso/curso");
      setCursos(res.data);
    } catch (erro) {
      toast.error("Erro na Busca " + erro.response.status);
    }
  };

  // 🔹 Buscar aluno (edição)
  const getAluno = async () => {
    try {
      const res = await api.get(`/alunos/aluno/${ras}`);
      setAluno(res.data);
    } catch (erro) {
      toast.error("Erro na Busca " + erro.response.status);
    }
  };

  // Inicialização
  useEffect(() => {
    getProjetos();
    getCursos();
    if (ras) getAluno();
  }, [ras]);

  // Preencher formulário quando aluno carregar
  useEffect(() => {
    if (aluno) {
      setRa(aluno.ra || "");
      setEmailInstitucional(aluno.emailInstitucional || "");
      setNome(aluno.nome || "");
      setCurso(aluno.curso || "");
      setMotivoDaInscricao(aluno.motivoDaInscricao || "");
      setProjetoSelecionado(aluno.projetoSelecionado || "");
    }
  }, [aluno]);

  // 🔹 POST
  const postAluno = async () => {
    const hoje = new Date().toISOString().split("T")[0];

    const dados = {
      ra,
      emailInstitucional,
      nome,
      curso,
      motivoDaInscricao,
      projetoSelecionado,
      dataInscricao: hoje,
    };

    await api.post("/alunos/addalunos", dados);
    navigate("/alunos");
  };

  // 🔹 PUT
  const putAluno = async () => {
    const dados = {
      ra,
      emailInstitucional,
      nome,
      curso,
      motivoDaInscricao,
      projetoSelecionado,
      status: true,
    };

    await api.put(`/alunos/aluno/${ras}`, dados);
    navigate("/alunos");
  };

  async function deletarAluno() {
    try {
      const resposta = window.confirm("Deseja realmente Deletar este Aluno ?");
      if (resposta) {
        await api.delete(`/alunos/aluno/${ras}`);
        toast.success("Aluno Deletado!");
        navigate("/alunos");
      }
    } catch (e) {
      toast.error("Não foi possivel realisar a operação, " + e.response.status);
      navigate("/alunos");
    }
  }

  return (
    <div className="content">
      <h2>{ras ? "Editar Aluno" : "Cadastro de Aluno"}</h2>

      <form
        onSubmit={(e) => {
          e.preventDefault();
          ras ? putAluno() : postAluno();
        }}
      >
        <label>RA</label>
        <input
          type="number"
          value={ra}
          onChange={(e) => setRa(e.target.value)}
          required
        />

        <label>E-mail Institucional</label>
        <input
          type="email"
          value={emailInstitucional}
          onChange={(e) => setEmailInstitucional(e.target.value)}
          required
        />

        <label>Nome</label>
        <input
          type="text"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
          required
        />

        <label>Curso</label>
        <select
          value={curso}
          onChange={(e) => setCurso(e.target.value)}
          required
        >
          <option value="">Selecione um projeto</option>
          {cursos.map((c) => (
            <option key={c.codigoDoCurso} value={c.nomeDoCurso}>
              {c.nomeDoCurso}
            </option>
          ))}
        </select>

        <label>Motivo da Inscrição</label>
        <textarea
          value={motivoDaInscricao}
          onChange={(e) => setMotivoDaInscricao(e.target.value)}
          required
        />

        <label>Projeto</label>
        <select
          value={projetoSelecionado}
          onChange={(e) => setProjetoSelecionado(e.target.value)}
          required
        >
          <option value="">Selecione um projeto</option>
          {projetos.map((p) => (
            <option key={p.codigoProjeto} value={p.codigoProjeto}>
              {p.codigoProjeto}: {p.nomeDoProjeto}
            </option>
          ))}
        </select>

        <button id="btnAtualisarDeletar" type="submit">
          {ras ? "Atualizar" : "Criar"}
        </button>
        {ras && localStorage.getItem("roles") && (
          <button
            id="btnDeletarAlunos"
            onClick={() => {
              deletarAluno();
            }}
          >
            Deletar
          </button>
        )}
      </form>
    </div>
  );
}

export default FormularioAlunos;

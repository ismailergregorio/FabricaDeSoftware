import { useEffect, useState } from "react";
import "./css-formularioUsuario.css";
import api from "../../../../services/api";
import { toast } from "react-toastify";
import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";

export default function FormularioUsuarii() {
  const navigate = useNavigate();
  const rolesLocal = localStorage.getItem("roles");
  const { id } = useParams();

  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [senhaAlterada, setSenhaAlterada] = useState("");
  const [roles, setRoles] = useState("");

  const [usuario, setUsuario] = useState(null);

  async function getUser() {
    try {
      const response = await api.get(`/user/${id}`);
      setUsuario(response.data);
    } catch (erro) {
      if (erro.response?.status === 401) {
        toast.error("Sessão expirada. Faça login novamente.");
        localStorage.removeItem("token");
        navigate("/", { replace: true });
      } else {
        toast.error("Erro na busca do usuário");
      }
    }
  }

  const [ativado, setAtivado] = useState(false);
  function VerificaCheckd(e) {
    setAtivado(e.target.checked);
  }

  useEffect(() => {
    if (id) {
      getUser();
    }
  }, [id]);

  useEffect(() => {
    if (usuario) {
      setNome(usuario.usuario || "");
      setEmail(usuario.login || "");
      setRoles(usuario.role || "");
    }
  }, [usuario]);

  async function postUser() {
    const dados = {
      nome: nome,
      login: email,
      senha: senha,
      roles: roles,
    };
    try {
      await api.post("/user", dados);
      toast.success("Usuario criado!");
      navigate("/admin");
    } catch (e) {
      console.error(e, "erro na criação do usuario");
      toast.error("Erro, ao criar usuario, " + e);
    }
  }

  async function putUser() {
    const dadosComSenha = {
      nome: nome,
      login: email,
      senhaAtual: senha,
      novaSenha: senhaAlterada,
      roles: roles,
    };

    const dadosSemSenha = {
      nome: nome,
      login: email,
      roles: roles,
    };

    try {
      await api.put(`/user/${id}`, ativado ? dadosComSenha : dadosSemSenha);
      toast.success("Usuario Atualisado!");
      navigate("/admin");
    } catch (e) {
      console.error(e, "erro na criação do usuario");
      toast.error("Erro na operação, verifique a senha ou usuario", e.response.status);
    }
  }

  async function deletarUsuario() {
    try {
      const resposta = window.confirm("Deseja continuar?");
      if (resposta) {
        await api.delete(`/user/${id}`);
        toast.success("Usuario Deletado!");
        navigate("/admin");
      }
    } catch (e) {
      console.error(e, "erro na criação do usuario");
      toast.error("Não foi possivel realisar a operação, " + e);
      navigate("/admin");
    }
  }

  const selectRoles = [
    { codigo: "01", nome: "ADMIN" },
    { codigo: "02", nome: "USER_N1" },
    { codigo: "03", nome: "USER_N2" },
    { codigo: "04", nome: "USER" },
  ];

  return (
    <div className="content">
      <form
        onSubmit={(e) => {
          e.preventDefault();
        }}
      >
        <label>Nome</label>
        <input
          type="Text"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
          required
        />

        <label>E-mail</label>
        <input
          type="text"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <div id="ss">
          <label id="ss">{id ? "Altera Senha" : "Senha"}</label>
          {id && (
            <div className="butonAlteraPassword" id="idLabel">
              <h1>Altera Senha:</h1>
              <input
                type="checkbox"
                id="chk"
                checked={ativado}
                onChange={VerificaCheckd}
              ></input>
              <label for="chk" className="switch">
                <span className="slider"></span>
              </label>
            </div>
          )}
        </div>
        <input
          className="input-padrao"
          type="password"
          value={senha}
          placeholder={id ? "Senha Atual" : "Senha"}
          disabled={id && !ativado}
          onChange={(e) => setSenha(e.target.value)}
          required
        />
        {id && ativado && (
          <input
            className="input-padrao"
            type="password"
            placeholder="Nova Senha"
            value={senhaAlterada}
            onChange={(e) => setSenhaAlterada(e.target.value)}
            required
          />
        )}

        <label>Nivel de Asseço</label>
        <select
          value={roles}
          onChange={(e) => setRoles(e.target.value)}
          required
          disabled={rolesLocal === "ADMIN" ? false : true}
        >
          <option value="">Selecione um projeto</option>
          {selectRoles.map((c) => (
            <option key={c.codigo} value={c.nome}>
              {c.nome}
            </option>
          ))}
        </select>
        <button
          type="submit"
          onClick={() => {
            id ? putUser() : postUser();
          }}
        >
          {id ? "Salvar Alteração" : "Salvar"}
        </button>
        {id && (rolesLocal === "ADMIN" || rolesLocal === "USER_1") && (
          <button
            id="DeletarUsuario"
            onClick={() => {
              deletarUsuario();
            }}
          >
            Deletar
          </button>
        )}
      </form>
    </div>
  );
}

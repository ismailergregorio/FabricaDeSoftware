import { useEffect, useState } from "react";
import "../css-abas-lista.css";
import { Link } from "react-router-dom";
import api from "../../../../services/api";
import { toast } from "react-toastify";
import LabelInfo from "../../../label-info/label-info";

function AbasLista2() {
  const [alunos, setAlunos] = useState([]);
  const getAlunos = async () => {
    try {
      const res = await api.get("/alunos/alunos");
      const filtrados = res.data.filter((x) => x.status === false);
      setAlunos(filtrados);
    } catch (erro) {
      toast.error("Erro na Busca "+ erro.response.status)
    }
  };

  useEffect(() => {
    getAlunos();
  }, []);

  return (
    <div className="heder-tabela-aba">
      <LabelInfo nome={"Alunos"} rota={"/formulario/aluno"}/>
      <div className="aba">
        <Link to="/alunos" className="abas" id="Ativo">
          <h1>Incritos</h1>
        </Link>
        <Link to="/alunos/nao-aprovado" className="abas">
          <h1 id="txt1">Aguardando</h1>
          <h1 id="txt2">{alunos.length}</h1>
        </Link>
      </div>
    </div>
  );
}
export default AbasLista2;

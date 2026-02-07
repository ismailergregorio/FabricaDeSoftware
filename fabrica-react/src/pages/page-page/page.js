import React from "react";
import "./css-page.css";
import "../css-master.css";
import NavBar from "../../components/nav-bar/nav-bar";
import Title from "../../components/title/tile";
import Carrossel from "../../components/compnentes-page/carossel/carrossel";
import InformacaoDaFabrica from "../../components/compnentes-page/informacaoDaFabrica/informacaodafabrica";
import ConfigConteinerCurso from "../../components/compnentes-page/config-conteiner-cursos/config-conteiner-cursos";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";
function CongPage() {
  const navigate = useNavigate();
  const roles = localStorage.getItem("roles");

  useEffect(() => {
    if (roles !== "ADMIN" && roles !== "USER_N1") {
      toast.warning("Esta sessão é somente para usuários ADMIN ou USER_N1");
      navigate("/"); // ou /login
    }
  }, [roles, navigate]);
  return (
    <main>
      <NavBar />
      <section>
        <Title Titulo={"Configuração Pagina Principal"} />
        <div className="Conteudo">
          <Carrossel />
          <InformacaoDaFabrica />
          <ConfigConteinerCurso />
        </div>
      </section>
    </main>
  );
}
export default CongPage;

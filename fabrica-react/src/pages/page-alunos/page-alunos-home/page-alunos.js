import "./page-css-alunos.css";
import NavBar from "../../../components/nav-bar/nav-bar";
import Title from "../../../components/title/tile";
import CabecalhoTabela from "../../../components/conponents-alunos/cabecalho-abas/cabecalho-tabela";
import AbasLista1 from "../../../components/conponents-alunos/abas-lista/tabela-aba1/abas-lista1";
import TabelaAbas from "../../../components/conponents-alunos/tabela-abas/tabela-abas";
function PageAlunos() {
  return (
    <main>
      <NavBar />
      <section>
        <Title Titulo={"Alunos"} />
        <div className="Conteudo">
          <div className="hederTabela">
            {/* <CabecalhoTabela /> */}
            <AbasLista1 />
            <TabelaAbas />
          </div>
        </div>
      </section>
    </main>
  );
}
export default PageAlunos;

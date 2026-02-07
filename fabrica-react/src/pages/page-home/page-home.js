import NavBar from "../../components/nav-bar/nav-bar";
import Title from "../../components/title/tile";
import Cards from "../../components/content-home/js/card";
import ListaAlunos from "../../components/conponents-alunos/lista-alunos/list-alunos";
import ListaProjetos from "../../components/conponents-projetos/lista-projetos/list-projetos";
import "../css-master.css"
function Home() {
  return (
    <main>
      <NavBar />
      <section>
        <Title Titulo={"Home"} />
        <div className="Conteudo">
          <Cards />
          <div className="infoDados">
            <ListaProjetos />
            <ListaAlunos />
          </div>
        </div>
      </section>
    </main>
  );
}
export default Home;

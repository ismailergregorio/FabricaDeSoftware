import "./page-css-alunos.css";
import NavBar from "../../../components/nav-bar/nav-bar";
import Title from "../../../components/title/tile";
import FormularioAlunos from "../../../components/conponents-alunos/formulario-cadastro-de-alunos/formulario-alunos";

function PageAlunosFormulario() {
  return (
    <main>
      <NavBar />
      <section>
        <Title Titulo={"Alunos"} />
        <div className="Conteudo">
          <FormularioAlunos />
        </div>
      </section>
    </main>
  );
}
export default PageAlunosFormulario;

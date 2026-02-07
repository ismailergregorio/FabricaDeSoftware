import "./page-css-formulario-projeto.css"
import NavBar from "../../../components/nav-bar/nav-bar";
import Title from "../../../components/title/tile";
import FormularioProjetos from "../../../components/conponents-projetos/formulario-projetos/formulario-projetos";

function ProjetosFormulario() {
  return (
    <main>
      <NavBar/>
      <section>
        <Title Titulo={"Projetos"}/>
        <div className="Conteudo">
            <FormularioProjetos/>
        </div>
      </section>
    </main>
  );
}
export default ProjetosFormulario;

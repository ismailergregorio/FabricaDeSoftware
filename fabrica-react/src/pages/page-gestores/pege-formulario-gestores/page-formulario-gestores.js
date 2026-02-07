import "./css-page-formulario-gestores.css"
import FormularioGestores from "../../../components/components-gestores/formulario-gestores/formulario-criacao-de-gestores"
import { Link } from "react-router-dom"
import NavBar from "../../../components/nav-bar/nav-bar"
import Title from "../../../components/title/tile"

function PageFormularioGestores(){
    return(
        <main>
      <NavBar />
      <section>
        <Title Titulo={"Gestores"} />
        <div className="Conteudo">
        <FormularioGestores/>
        </div>
      </section>
    </main>
    )
}
export default PageFormularioGestores;
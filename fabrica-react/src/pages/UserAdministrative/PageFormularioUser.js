import NavBar from "../../components/nav-bar/nav-bar";
import Title from "../../components/title/tile";
import FormularioUsuario from "../../components/componentes-UserAdministrative/ListaDeUsuarios/CoponenteFormularioUsuarios/FormularioUsuario";
export default function PageFormularioUser() {
  return (
    <main>
      <NavBar />
      <section>
        <Title Titulo={"Adminstrador"} />
        <div className="Conteudo">
          <FormularioUsuario />
        </div>
      </section>
    </main>
  );
}

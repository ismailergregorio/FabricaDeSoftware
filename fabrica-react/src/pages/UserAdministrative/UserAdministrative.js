import ListaUser from "../../components/componentes-UserAdministrative/ListaDeUsuarios/ListaUser";
import NavBar from "../../components/nav-bar/nav-bar";
import Title from "../../components/title/tile";
export default function UseAdministrative() {
  return (
    <main>
      <NavBar />
      <section>
        <Title Titulo={"Adminstrador"} />
        <div className="Conteudo">
         <ListaUser/>
        </div>
      </section>
    </main>
  );
}

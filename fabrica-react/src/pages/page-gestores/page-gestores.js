import React from "react";
import "./css-page-gestores.css";
import "../css-master.css"
import NavBar from "../../components/nav-bar/nav-bar";
import Title from "../../components/title/tile";
import LabelInfo from "../../components/label-info/label-info";
import ItenGestores from "../../components/components-gestores/intens-da-lista-gestores/itens-lista-gestores";


function PageGestores() {
  return (
    <main>
      <NavBar />
      <section>
        <Title Titulo={"Gestores"} />
        <div className="Conteudo">
        <LabelInfo rota={"/formulario/gestores"} nome={"Gestores"}/>
        <ItenGestores/>
        </div>
      </section>
    </main>
  );
}
export default PageGestores;

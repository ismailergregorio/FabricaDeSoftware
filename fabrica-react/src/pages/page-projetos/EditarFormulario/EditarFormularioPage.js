import React from "react";
import EditaFormulario from "../../../components/conponents-projetos/formulario-projetos/EditarFormulario/EditarFormulario";
import NavBar from "../../../components/nav-bar/nav-bar"
import Title from "../../../components/title/tile"
import { Link } from "react-router-dom";


function EditarProjetoFormularioPage() {
    return (
        <main>
            <NavBar />
            <section>
                <Title Titulo={"Projetos"} />
                <div className="Conteudo">
                    <EditaFormulario/>
                </div>
            </section>
        </main>
    );
}

export default EditarProjetoFormularioPage;
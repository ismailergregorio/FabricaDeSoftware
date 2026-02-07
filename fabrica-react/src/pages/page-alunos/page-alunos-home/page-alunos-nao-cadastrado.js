import './page-css-alunos.css'
import NavBar from "../../../components/nav-bar/nav-bar";
import Title from "../../../components/title/tile";
import CabecalhoTabela from "../../../components/conponents-alunos/cabecalho-abas/cabecalho-tabela";
import AbasLista2 from '../../../components/conponents-alunos/abas-lista/tabela-aba2/abas-lista2';
import TabelaAbasNaoCadastrado from '../../../components/conponents-alunos/tabela-abas-nao-cadastrado/tabela-abas-nao-cadastrado';
function PageAlunosNaoAprovado() {
    return (
        <main>
            <NavBar />
            <section>
                <Title Titulo={"Alunos"} />
                <div className="Conteudo">
                    <div className="hederTabela">
                        {/* <CabecalhoTabela/> */}
                        <AbasLista2/>
                        <TabelaAbasNaoCadastrado/>
                    </div>                    
                </div>
            </section>
        </main>
    );
}
export default PageAlunosNaoAprovado;
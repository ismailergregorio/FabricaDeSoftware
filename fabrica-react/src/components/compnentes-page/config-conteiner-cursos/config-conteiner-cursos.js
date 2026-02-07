import "./css-config-conteiner-cursos.css"
import TabelaImgCursos from "./tabelaImgCursos/tabela-img-Cursos";
import TabelaDeCurso from "./tabelaCuros/tabela-de-cursos";


function ConfigConteinerCurso(){
 return(
  <div className="ConteinerTabela">
    <TabelaImgCursos/>
    <TabelaDeCurso/>
  </div>
)
}

export default ConfigConteinerCurso;
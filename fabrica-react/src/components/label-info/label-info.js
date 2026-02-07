import "./css-label-info.css"
import { Link } from "react-router-dom";
function LabelInfo({rota,nome}){
    return(
        <div className="label-info">
            <div className="label">
                <Link to={rota}><i className="fa-solid fa-plus"></i> Adicionar {nome}</Link>
            </div>
        </div>
    );

}
export default LabelInfo;
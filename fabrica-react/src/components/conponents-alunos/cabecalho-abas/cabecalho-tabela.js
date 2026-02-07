import { useEffect, useState } from 'react';
import './css-cabecalho-tabela.css'
import api from '../../../services/api';

function CabecalhoTabela() {
    const [quantidadeAlunos,setQuantidadeAlunos] = useState([]);

    useEffect(()=>{
        api.get("/alunos/alunos").then((res)=>{
            setQuantidadeAlunos(res.data);
        }).catch((err)=>{
            console.log("Erro na Busca",err)
        })
    },[])

    return (
        <div>
            <div className="head-alunos">
                <h1>Alunos Cadastrados</h1>
                <h2>Total de Alunos:{quantidadeAlunos.length}</h2>
            </div>
        </div>
    );
}
export default CabecalhoTabela;
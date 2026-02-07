import React from "react";
import { Routes, Route } from "react-router-dom";
import PrivateRoute from "./PrivateRoute";

import Login from "../pages/Login/login";
import Home from "../pages/page-home/page-home";
import Projetos from "../pages/page-projetos/projetos-home/page-projeto";
import ProjetosFormulario from "../pages/page-projetos/projetos-formulario/page-formulario-projeto";
import PageAlunos from "../pages/page-alunos/page-alunos-home/page-alunos";
import PageAlunosNaoAprovado from "../pages/page-alunos/page-alunos-home/page-alunos-nao-cadastrado";
import PageAlunosFormulario from "../pages/page-alunos/page-lunos-formulario/page-alunos-formulario";
import PageGestores from "../pages/page-gestores/page-gestores";
import PageFormularioGestores from "../pages/page-gestores/pege-formulario-gestores/page-formulario-gestores";
import Page from "../pages/page-page/page"
import UseAdministrative from "../pages/UserAdministrative/UserAdministrative";
import PageFormularioUser from "../pages/UserAdministrative/PageFormularioUser";
function Rotas() {
  return (
    <Routes>

      {/* 🔓 LOGIN */}
      <Route path="/" element={<Login />} />

      {/* 🔐 SISTEMA */}
      <Route path="/home" element={<PrivateRoute><Home /></PrivateRoute>} />
      <Route path="/admin" element={<PrivateRoute><UseAdministrative/></PrivateRoute>}/>
      <Route path="/admin/add" element={<PrivateRoute><PageFormularioUser/></PrivateRoute>}/>
      <Route path="/admin/:id" element={<PrivateRoute><PageFormularioUser/></PrivateRoute>}/>
      <Route path="/projetos" element={<PrivateRoute><Projetos /></PrivateRoute>} />
      <Route path="/projetos/formulario" element={<PrivateRoute><ProjetosFormulario /></PrivateRoute>} />
      <Route path="/projetos/formulario/:codigoProjeto" element={<PrivateRoute><ProjetosFormulario /></PrivateRoute>} />

      <Route path="/alunos" element={<PrivateRoute><PageAlunos /></PrivateRoute>} />
      <Route path="/alunos/nao-aprovado" element={<PrivateRoute><PageAlunosNaoAprovado /></PrivateRoute>} />
      <Route path="/formulario/aluno" element={<PrivateRoute><PageAlunosFormulario /></PrivateRoute>} />
      <Route path="/formulario/aluno/:ras" element={<PrivateRoute><PageAlunosFormulario /></PrivateRoute>} />

      <Route path="/gestores" element={<PrivateRoute><PageGestores /></PrivateRoute>} />
      <Route path="/formulario/gestores" element={<PrivateRoute><PageFormularioGestores /></PrivateRoute>} />
      <Route path="/formulario/gestores/:codigoGestor" element={<PrivateRoute><PageFormularioGestores /></PrivateRoute>} />
      <Route path="/page" element={<PrivateRoute><Page /></PrivateRoute>}/>

    </Routes>
  );
}

export default Rotas;

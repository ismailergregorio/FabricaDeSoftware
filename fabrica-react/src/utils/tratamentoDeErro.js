import { ToastContainer, toast } from 'react-toastify';
export default function ErrosGat(tipo){
 if(tipo === "erro"){
  return () => toast.error("Erro");
 }
}
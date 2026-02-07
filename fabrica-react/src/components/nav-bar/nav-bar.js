import { useState } from "react";
import "./style-nav-bar.css";
import { Link } from "react-router-dom";
import { useEffect } from "react";

function NavBar() {
  const roles = localStorage.getItem("roles");
  const [stadoMenu, setStadoMenu] = useState(false);

  function stMenu() {
    if (stadoMenu) {

      setStadoMenu(!stadoMenu);
    } else {

      setStadoMenu(!stadoMenu);
    }
  }

  // toggle do menu (somente mobile)
  const toggleMenu = () => {
    if (window.innerWidth >= 768) return;
    setStadoMenu((prev) => !prev);
  };

  // fecha menu ao clicar em um item (mobile)
  const fecharMenuMobile = () => {
    if (window.innerWidth < 768) {
      setStadoMenu(false);
    }
  };

  // fecha automaticamente ao ir para desktop
  useEffect(() => {
    const handleResize = () => {
      if (window.innerWidth > 768) {
        setStadoMenu(true);
        console.log("entro tela maior");
      }
      else{
        setStadoMenu(false);
      }
    };
    window.addEventListener("resize", handleResize);
    handleResize();
    return () => window.removeEventListener("resize", handleResize);
  }, []);

  return (
    <header className="menu">
      <div className="logo-BtnMenu">
        <link
          rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
        />

        <Link to="/">
          <img
            src="https://unisales.br/wp-content/uploads/2020/03/Logo-UniSales-Branca_Horizontal.png"
            alt="logo"
          />
        </Link>
        <button onClick={() => stMenu()}>Menu</button>
      </div>
      {stadoMenu && (
        <ul className={"listaNave" ? "listaNave aberto" : "listaNave fechado"}>
          <li className="navItem">
            <Link to="/home" className="textNave">
              <i className="fa-solid fa-house"></i>Home
            </Link>
          </li>
          <li className="navItem">
            <Link to="/projetos" className="textNave">
              <i className="fa-solid fa-sheet-plastic"></i>Projetos
            </Link>
          </li>
          <li className="navItem">
            <Link to="/alunos" className="textNave">
              <i className="fa-solid fa-graduation-cap"></i>Alunos
            </Link>
          </li>
          <li className="navItem">
            <Link to="/gestores" className="textNave">
              <i className="fa-solid fa-people-roof"></i>Gestores
            </Link>
          </li>
          <li className="navItem">
            {(roles === "ADMIN" || roles === "USER_N1") && (
              <Link to="/page" className="textNave">
                <i className="fa-solid fa-file"></i>Page
              </Link>
            )}
          </li>
        </ul>
      )}
    </header>
  );
}

export default NavBar;

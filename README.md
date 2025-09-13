# 🏭 Fábrica de Software - UniSales

Bem-vindo ao repositório do site da **Fábrica de Software da UniSales**, uma iniciativa acadêmica que promove o desenvolvimento de soluções digitais por estudantes dos cursos de tecnologia.

## 🚀 Visão Geral

Este projeto é um **protótipo funcional** do site da Fábrica de Software, desenvolvido com foco em:

- Usabilidade e responsividade.
- Identidade visual alinhada à UniSales.
- Incentivo à participação de novos alunos e visibilidade dos projetos.

## 📌 Funcionalidades

- 🔎 **Barra de navegação** com campo de pesquisa integrado à API.
- 🎠 **Carrossel de imagens** dinâmico carregado via Spring Boot API.
- 💼 **Sessão de Projetos** com cards dinâmicos (imagem, descrição e link para GitHub).
- 👥 **Sessão "Quem Somos"** com carrossel de membros fictícios da equipe.
- 🏭 **Sessão "Sobre a Fábrica"** (texto + imagem institucional).
- 📝 **Formulário de Cadastro** integrado para novos participantes.
- 📱 **Design responsivo** adaptado para desktop e mobile.
- 🔗 **Rodapé personalizado** com links institucionais e redes sociais.

## 🖼️ Capturas de Tela

*(adicione prints reais do site rodando)*  
- Carrossel inicial  
- Sessão de Projetos  
- Sessão de Membros  

## 🛠️ Tecnologias Utilizadas

- **Frontend**: HTML5, CSS3, Bootstrap 5, JavaScript (modularizado).
- **Backend/API**: Spring Boot (Java).
- **Banco de Dados**: PostgreSQL (armazenando projetos, membros, imagens).
- **Ferramentas**: Fetch API para consumo do backend.

## 📁 Estrutura de Pastas

    Site da Fabrica/
    ├── css/
    │ ├── Style.css
    │ └── img/ # imagens utilizadas
    ├── js/
    │ ├── busca.js # lógica da barra de pesquisa
    │ ├── cadastro.js # lógica do formulário de cadastro
    │ ├── cards.js # renderização de cards genéricos
    │ ├── carrousel.js # carrossel dinâmico via API
    │ ├── equipe.js # membros da fábrica
    │ ├── main.js # ponto central para inicialização
    │ ├── projetos.js # cards de projetos
    │ ├── search.js # autocomplete de pesquisa
    │ ├── sobre.js # seção "Sobre a Fábrica"
    │ └── Script.js # funções auxiliares
    ├── busca.html # página de resultados da busca
    ├── index.html # página inicial
    ├── Iniciar.bat # script para rodar localmente
    └── README.md

## ⚙️ Como Rodar Localmente

1. Clone este repositório:
   ```bash
   git clone https://github.com/MapaInterativohub/MapaInterativo

2. Certifique-se de que o backend (Spring Boot) esteja rodando em:
    http://localhost:8080

3. Abra o index.html no navegador ou use um servidor local:
    cd "Site da Fabrica"
    python -m http.server 8000

👨‍🎓 Público-Alvo

Estudantes da UniSales.

Professores/orientadores de projetos.

Comunidade externa e empresas interessadas em parcerias.

📬 Contato

📧 fabricadesoftware@unisales.br
 (email fictício)

🌐 Site da UniSales

📄 Licença

Este projeto é de uso acadêmico sob a licença MIT. Fique à vontade para reutilizar e contribuir!


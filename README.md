
# 🏭 Fábrica de Software - UniSales

Bem-vindo ao repositório do site da **Fábrica de Software da UniSales**, uma iniciativa acadêmica que promove o desenvolvimento de soluções digitais por estudantes dos cursos de tecnologia.

## 🚀 Visão Geral

Este projeto é um protótipo funcional do site da Fábrica de Software, desenvolvido com foco em usabilidade, identidade visual da instituição e incentivo à participação de novos alunos.

## 📌 Funcionalidades

- 🔎 **Barra de navegação** com menu, campo de busca e responsividade (Tailwind + Bootstrap).
- 🎠 **Carrossel de imagens** dinâmico via `carrossel.json`.
- 👥 **Sessão "Quem Somos"** com cards da equipe via `equipe.json`.
- 💼 **Sessão de Projetos** com cards dinâmicos via `projetos.json`.
- 🏭 **Sessão Sobre a Fábrica** (texto e imagem) carregada por `sobre.json`.
- 📝 **Formulário de Cadastro** que envia dados como JSON via `POST`.
- 📱 **Design responsivo** adaptado a diferentes dispositivos.
- 🔗 **Rodapé com redes sociais e links úteis**.

## 🖼️ Capturas de Tela

![Carrossel](./assets/img/carrossel1.png)  
![Cards dos Membros](./assets/img/membros1.png)  
![Projetos](./assets/img/projetos1.png)

> As imagens são ilustrativas e geradas com inteligência artificial ou recursos livres.

## 🛠️ Tecnologias Utilizadas

- HTML5
- CSS3 + TailwindCSS + Bootstrap 5
- JavaScript com `fetch`
- JSON (estrutura de dados)
- Python (para servidor local)

## 🧪 Em Desenvolvimento

- 🔐 Área administrativa com login e painel de gerenciamento.
- 💾 Integração com banco de dados (Firebase ou MySQL).
- 🛠️ Sistema de submissão e edição de projetos/membros.
- 📊 Painel de acompanhamento para professores.

## 📁 Estrutura de Pastas

```
├── index.html
├── cadastro.html
├── Style.css
├── Script.js
├── cadastro.js
├── projetos.json
├── equipe.json
├── sobre.json
├── carrossel.json
├── img/
│   └── (imagens utilizadas no site)
└── README.md
```

## ⚠️ Como rodar localmente com JSON

Navegadores não permitem `fetch()` de arquivos locais (`file://`) por segurança.

### ✅ Solução:

Se você tem Python instalado, execute:

```bash
cd "C:\Documents\GitHub\FabricaDeSoftware\Site da Fabrica"
python -m http.server 8000
```

Acesse no navegador:  
[http://localhost:8000](http://localhost:8000)

Ou utilize o arquivo `iniciar-servidor.bat` para facilitar.

## 👨‍🎓 Público-Alvo

- Estudantes da UniSales.
- Professores e orientadores de projetos.
- Empresas e comunidade interessadas em parcerias.

## 📬 Contato

Para dúvidas ou sugestões:

- 📧 fabricadesoftware@unisales.br *(email fictício)*
- 🌐 [Site da UniSales](https://unisales.br)

---

## 📄 Licença

Este projeto é de uso acadêmico sob a licença MIT. Fique à vontade para reutilizar e contribuir!

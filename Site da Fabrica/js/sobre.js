export async function carregarSobre() {
  try {
    const res = await fetch("http://localhost:8080/config/config");
    const data = await res.json();

    console.log("Configurações recebidas:", data);

    const container = document.getElementById("sobre-container");

    let imagem = "";
    let descricao = `
      A Fábrica de Software é um projeto acadêmico voltado para o desenvolvimento 
      prático de soluções tecnológicas, promovendo a integração entre teoria e prática,
      preparando os alunos para o mercado de trabalho.
    `;

    // data.forEach(config => {
    //   if (config.nomeConfig === "Configuracao Texto Inicial") {
    //     descricao = config.valorSalvo;
    //   }
    // });

    data.forEach(config => {

      if (config.nomeConfig === "Configuracao Texto Inicial") {
        descricao = config.valorSalvo;
      }

      if (config.nomeConfig === "Configuracao Imagem Inicial") {
        imagem = config.valorSalvo;
      }
    });

    container.innerHTML = `
  <h2 class="mb-4">Fábrica de Software – Projeto Acadêmico</h2>

  <div class="row align-items-center">

    <div class="col-md-6">
      <p style="font-size: 18px; line-height: 1.6;">
        ${descricao}
      </p>
    </div>

    <div class="col-md-6 d-flex justify-content-end">
      <img src="${imagem}" 
           alt="Imagem da Fábrica de Software"
           class="img-fluid rounded shadow"
           style="max-width: 75%; height: auto;">
    </div>

  </div>
`;

  } catch (err) {
    console.error("Erro ao carregar conteúdo da seção Sobre:", err);
  }
}
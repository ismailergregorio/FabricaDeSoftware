export async function carregarSobre() {
  try {
    const res = await fetch("http://localhost:8080/config/config");
    const data = await res.json();
    console.log(data);
    const container = document.getElementById("sobre-container");

    let codigo_titulo = "";
    let codigo_descricao = "";
    let codigo_imagem = "";
    let codigo_alt = "";

    data.forEach((config, index) => {
      if (config.codigoDaConfguracao === "CONF2883") {
        codigo_titulo = config.valorSalvo
      }
      if (config.codigoDaConfguracao == "CONF3212") {
        codigo_descricao = config.valorSalvo
      }
      if (config.codigoDaConfguracao == "CONF1181") {
        codigo_imagem = config.valorSalvo
        console.log(codigo_imagem);

      }
      if (config.codigoDaConfguracao == "CONF1181") {
        codigo_alt = config.nomeConfig
      }
    });
    
    container.innerHTML = `
      <h2 class="mb-4">${codigo_titulo} "</h2>
      <div class="row align-items-center">
        <div class="col-md-6"><p>${codigo_descricao}</p></div>
        <div class="col-md-6 text-center">
          <img src="${codigo_imagem}" alt="${codigo_alt}" class="img-fluid rounded shadow" style="width: 400px; height: 300px; position: relative; left: 100px;">
        </div>
      </div>
    `;
  } catch (err) {
    console.error("Erro ao carregar conteúdo da seção Sobre:", err);
  }
}

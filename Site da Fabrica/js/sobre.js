export async function carregarSobre() {
  try {
    const res = await fetch("http://localhost:8080/config/config");
    const data = await res.json();
    console.log(data);
    const container = document.getElementById("sobre-container");

    data.forEach((config, index) => {
      if (config.codigoDaConfguracao === "CONF6948") {
        container.innerHTML = `
      <h2 class="mb-4">${config.valorSalvo}</h2>`;
      }
      if (config.codigoDaConfguracao == "CONF6902") {
        container.innerHTML = `
       <div class="col-md-6 text-center">
          <img src="${config.valorSalvo}" alt="${config.alt}" class="img-fluid rounded shadow" style="width: 400px; height: 300px; position: relative; left: 100px;">
        </div>`;
      }
    });

    // container.innerHTML = `
    //   <h2 class="mb-4">${data.nomeConfig}</h2>
    //   <div class="row align-items-center">
    //     <div class="col-md-6"><p>${data.texto}</p></div>
    //     <div class="col-md-6 text-center">
    //       <img src="${data.imagem}" alt="${data.alt}" class="img-fluid rounded shadow" style="width: 400px; height: 300px; position: relative; left: 100px;">
    //     </div>
    //   </div>
    // `;
  } catch (err) {
    console.error("Erro ao carregar conteúdo da seção Sobre:", err);
  }
}

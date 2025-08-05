export async function carregarSobre() {
  try {
    const res = await fetch("http://localhost:8080/sobre");
    const data = await res.json();
    const container = document.getElementById("sobre-container");

    container.innerHTML = `
      <h2 class="mb-4">${data.titulo}</h2>
      <div class="row align-items-center">
        <div class="col-md-6"><p>${data.texto}</p></div>
        <div class="col-md-6 text-center">
          <img src="${data.imagem}" alt="${data.alt}" class="img-fluid rounded shadow" style="width: 400px; height: 300px; position: relative; left: 100px;">
        </div>
      </div>
    `;
  } catch (err) {
    console.error("Erro ao carregar conteúdo da seção Sobre:", err);
  }
}

export async function carregarProjetos() {
  try {
    const res = await fetch("http://localhost:8080/projetos/getprojetos");
    const dados = await res.json();
    console.log(dados);
    const cont = document.getElementById("project-carousel");
    cont.innerHTML = "";

    dados.forEach(p => {
      const card = document.createElement("div");
      card.className = "carousel-card card";
      card.innerHTML = `
        <img src="${p.linkImage}" alt="${p.nomeDoProjeto}"
             style="width:100%;height:180px;object-fit:cover;border-radius:8px 8px 0 0;">
        <div class="card-body text-center">
          <h5 class="card-title">${p.nomeDoProjeto}</h5>
         
          <a href="busca.html?q=${encodeURIComponent(p.nomeDoProjeto)}" class="btn btn-primary mt-2">Saiba mais</a>
        </div>
      `;
      cont.appendChild(card);
    });
  } catch (e) {
    console.error("Erro carregarProjetos:", e);
  }
}

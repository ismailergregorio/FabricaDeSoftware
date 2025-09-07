export async function carregarEquipe() {
  try {
    const res = await fetch("http://localhost:8080/gestores/gestores");
    const dados = await res.json();
    const cont = document.getElementById("team-carousel");
    cont.innerHTML = "";

    dados.forEach(m => {
      const div = document.createElement("div");
      div.className = "carousel-card";
      div.innerHTML = `
        <img src="${m.linkImagenGestor || './img/default.jpg'}" alt="${m.name}"
             style="width:100%;height:180px;object-fit:cover;border-radius:8px 8px 0 0;">
        <h3>${m.name}</h3>
      `;
      cont.appendChild(div);
    });
  } catch (e) {
    console.error("Erro carregarEquipe:", e);
  }
}

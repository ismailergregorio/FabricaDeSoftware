export async function carregarEquipe() {
  try {
    const res = await fetch("http://localhost:8080/page/gestores");
    const dados = await res.json();

    console.log("Gestores recebidos:", dados);

    const cont = document.getElementById("team-carousel");
    cont.innerHTML = "";

    dados.forEach(m => {
      const div = document.createElement("div");
      div.className = "carousel-card";

      // 🔥 Pega imagem do primeiro projeto, se existir
      let imagem = "./img/default.jpg";

      imagem = m.img || "./img/default.jpg";

      div.innerHTML = `
        <img src="${imagem}" alt="${m.name}"
             style="width:100%;height:180px;object-fit:cover;border-radius:8px 8px 0 0;">
        <h3>${m.name}</h3>
      `;

      cont.appendChild(div);
    });

  } catch (e) {
    console.error("Erro carregarEquipe:", e);
  }
}
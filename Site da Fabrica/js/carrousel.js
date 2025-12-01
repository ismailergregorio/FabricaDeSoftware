// Função para carregar dinamicamente as imagens do carrossel
export async function carregarCarrossel() {
  try {
    // Chamada para a API do Spring Boot
    const res = await fetch("http://localhost:8080/carrocel/carrocel_imagens");
    if (!res.ok) throw new Error("Erro HTTP: " + res.status);

    // Converte a resposta em JSON
    const slides = await res.json();

    // Seleciona os elementos do HTML onde o carrossel será renderizado
    const indicators = document.getElementById("carousel-indicators");
    const inner = document.getElementById("carousel-inner");

    // Limpa conteúdo antigo para não duplicar
    indicators.innerHTML = "";
    inner.innerHTML = "";

    // Percorre cada item vindo da API
    let activeIndex = 0; // controla qual será a primeira imagem ativa
    slides.forEach((item, index) => {
      // Só adiciona imagens que estão ativadas (imagenAtivadaDesativada === true)
      if (item.imagenAtivadaDesativada) {
        
        // Criando indicador (os "pontinhos" do carrossel)
        const btn = document.createElement("button");
        btn.type = "button";
        btn.setAttribute("data-bs-target", "#carouselExampleIndicators");
        btn.setAttribute("data-bs-slide-to", activeIndex);
        if (activeIndex === 0) btn.className = "active";
        indicators.appendChild(btn);

        // Criando o slide
        const slide = document.createElement("div");
        slide.className = "carousel-item" + (activeIndex === 0 ? " active" : "");
        slide.innerHTML = `
          <img src="${item.linkImagenCarrocel}" class="d-block w-100"
               alt="Imagem ${item.codigoImagem}"
               style="height: 700px; object-fit: cover;">
        `;
        inner.appendChild(slide);

        activeIndex++; // só incrementa para imagens ativas
      }
    });
  } catch (err) {
    console.error("Erro ao carregar carrossel:", err);
  }
}

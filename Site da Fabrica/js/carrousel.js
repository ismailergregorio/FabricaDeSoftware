export async function carregarCarrossel() {
  try {

    const res = await fetch("http://localhost:8080/carrocel/carrocel_imagens");

    if (!res.ok) {
      const text = await res.text();
      throw new Error("Erro HTTP: " + res.status + " - " + text);
    }

    const slides = await res.json();

    console.log("Slides recebidos:", slides);

    if (!Array.isArray(slides)) {
      throw new Error("Resposta não é um array");
    }

    const indicators = document.getElementById("carousel-indicators");
    const inner = document.getElementById("carousel-inner");

    indicators.innerHTML = "";
    inner.innerHTML = "";

    let activeIndex = 0;

    slides.forEach((item) => {

      if (item.imagenAtivadaDesativada === true) {

        const btn = document.createElement("button");
        btn.type = "button";
        btn.setAttribute("data-bs-target", "#carouselExampleIndicators");
        btn.setAttribute("data-bs-slide-to", activeIndex);
        if (activeIndex === 0) btn.className = "active";
        indicators.appendChild(btn);

        const slide = document.createElement("div");
        slide.className = "carousel-item" + (activeIndex === 0 ? " active" : "");

        slide.innerHTML = `
          <img src="${item.linkImagenCarrocel}" 
               class="d-block w-100"
               alt="Imagem ${item.codigoImagem}"
               style="height: 700px; object-fit: cover;">
        `;

        inner.appendChild(slide);
        activeIndex++;
      }
    });

  } catch (err) {
    console.error("Erro ao carregar carrossel:", err);
  }
}
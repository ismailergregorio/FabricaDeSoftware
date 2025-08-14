export async function carregarCarrossel() {
  try {
    const res = await fetch("http://localhost:8080/carrocel/carrocel_imagens");
    const slides = await res.json();

    console.log(res);

    const indicators = document.getElementById("carousel-indicators");
    const inner = document.getElementById("carousel-inner");

    indicators.innerHTML = "";
    inner.innerHTML = "";

    slides.forEach((item, index) => {
      const btn = document.createElement("button");
      btn.type = "button";
      btn.setAttribute("data-bs-target", "#carouselExampleIndicators");
      btn.setAttribute("data-bs-slide-to", index);
      if (index === 0) btn.className = "active";
      indicators.appendChild(btn);

      const slide = document.createElement("div");
      slide.className = "carousel-item" + (index === 0 ? " active" : "");
      slide.innerHTML = `
        <img src="${item.linkImagenCarrocel}" class="d-block w-100" alt="${item.alt}" style="height: 700px; object-fit: cover;">
      `;
      inner.appendChild(slide);
    });
  } catch (err) {
    console.error("Erro ao carregar carrossel:", err);
  }
}

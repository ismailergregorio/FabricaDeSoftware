function scrollCarousel(id, direction) {
  const carousel = document.getElementById(id);
  carousel.scrollBy({ left: direction * 300, behavior: 'smooth' });
}

function autoScrollCarousel(id) {
  const carousel = document.getElementById(id);
  let scrollDirection = 1;
  setInterval(() => {
    const maxScroll = carousel.scrollWidth - carousel.clientWidth;
    if (carousel.scrollLeft >= maxScroll || carousel.scrollLeft <= 0) {
      scrollDirection *= -1;
    }
    carousel.scrollBy({ left: scrollDirection * 300, behavior: 'smooth' });
  }, 4000);
}

async function carregarProjetos() {
  const res = await fetch('projetos.json');
  const dados = await res.json();
  const container = document.getElementById('project-carousel');
  container.innerHTML = '';

  dados.forEach(p => {
    const card = document.createElement('div');
    card.className = 'carousel-card card';
    card.innerHTML = `
      <img src="${p.imagem}" alt="${p.titulo}" style="width: 100%; height: 180px; object-fit: cover; border-radius: 8px 8px 0 0;">
      <div class="card-body">
        <h5 class="card-title">${p.titulo}</h5>
        <p class="card-text">${p.descricao}</p>
      </div>
    `;
    container.appendChild(card);
  });
}


async function carregarEquipe() {
  const res = await fetch('equipe.json');
  const dados = await res.json();
  const container = document.getElementById('team-carousel');
  container.innerHTML = '';
  dados.forEach(p => {
    container.innerHTML += `
      <div class="carousel-card">
        <img src="${p.foto}" style="width:200px;height:200px;border-radius:8px;" />
        <h3>${p.nome}</h3>
        <p>${p.cargo}</p>
      </div>`;
  });
}

window.onload = () => {
  carregarProjetos();
  carregarEquipe();
  autoScrollCarousel('project-carousel');
  autoScrollCarousel('team-carousel');
};

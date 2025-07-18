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
    container.innerHTML += `
      <div class="carousel-card">
        <h5>${p.titulo}</h5>
        <p>${p.descricao}</p>
      </div>`;
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

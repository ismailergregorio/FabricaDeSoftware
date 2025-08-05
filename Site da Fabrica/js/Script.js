// 1) Scroll manual (caso use botões prev/next)
function scrollCarousel(id, direction) {
  const carousel = document.getElementById(id);
  if (!carousel) return;
  carousel.scrollBy({ left: direction * 300, behavior: 'smooth' });
}

// 2) Scroll automático
function autoScrollCarousel(id) {
  const carousel = document.getElementById(id);
  if (!carousel) return;
  let dir = 1;
  setInterval(() => {
    const maxScroll = carousel.scrollWidth - carousel.clientWidth;
    if (carousel.scrollLeft >= maxScroll || carousel.scrollLeft <= 0) {
      dir *= -1;
    }
    carousel.scrollBy({ left: dir * 300, behavior: 'smooth' });
  }, 4000);
}

// 3) Carregar carrossel principal
async function carregarCarrossel() {
  try {
    const res = await fetch('http://localhost:8080/carrossel');
    const slides = await res.json();

    const indicators = document.getElementById('carousel-indicators');
    const inner = document.getElementById('carousel-inner');

    indicators.innerHTML = '';
    inner.innerHTML = '';

    slides.forEach((item, index) => {
      const btn = document.createElement('button');
      btn.type = 'button';
      btn.setAttribute('data-bs-target', '#carouselExampleIndicators');
      btn.setAttribute('data-bs-slide-to', index);
      if (index === 0) btn.className = 'active';
      indicators.appendChild(btn);

      const slide = document.createElement('div');
      slide.className = 'carousel-item' + (index === 0 ? ' active' : '');
      slide.innerHTML = `
        <img src="${item.imagem}" class="d-block w-100" alt="${item.alt}" style="height: 700px; object-fit: cover;">
      `;
      inner.appendChild(slide);
    });
  } catch (err) {
    console.error('Erro ao carregar carrossel:', err);
  }
}


// 4) Carregar seção Sobre
async function carregarSobre() {
  try {
    const res = await fetch('http://localhost:8080/sobre');
    const data = await res.json();
    const container = document.getElementById('sobre-container');

    container.innerHTML = `
      <h2 class="mb-4">${data.titulo}</h2>
      <div class="row align-items-center">
        <div class="col-md-6">
          <p>${data.texto}</p>
        </div>
        <div class="col-md-6 text-center">
          <img
            src="${data.imagem}"
            alt="${data.alt}"
            style="width: 400px; height: 300px; position: relative; left: 100px;"
            class="img-fluid rounded shadow"
          />
        </div>
      </div>
    `;
  } catch (err) {
    console.error('Erro ao carregar conteúdo da seção Sobre:', err);
  }
}


// 5) Carregar projetos da API
async function carregarProjetos() {
  try {
    const res = await fetch('http://localhost:8080/projetos/getprojetos');
    const dados = await res.json();
    const cont = document.getElementById('project-carousel');
    cont.innerHTML = '';

    dados.forEach(p => {
      const card = document.createElement('div');
      card.className = 'carousel-card card';
      card.innerHTML = `
        <img src="${p.linkImage}" alt="${p.nomeDoProjeto}"
             style="width:100%;height:180px;object-fit:cover;border-radius:8px 8px 0 0;">
        <div class="card-body text-center">
          <h5 class="card-title">${p.nomeDoProjeto}</h5>
          <p class="card-text">${p.descricaoDoProjeto}</p>
          <a href="busca.html?q=${encodeURIComponent(p.nomeDoProjeto)}" class="btn btn-primary mt-2">Saiba mais</a>
        </div>
      `;
      cont.appendChild(card);
    });
  } catch (e) {
    console.error('Erro carregarProjetos:', e);
  }
}

// 6) Carregar equipe da API
async function carregarEquipe() {
  try {
    const res = await fetch('http://localhost:8080/alunos/alunos');
    const dados = await res.json();
    const cont = document.getElementById('team-carousel');
    cont.innerHTML = '';

    dados.forEach(m => {
      const div = document.createElement('div');
      div.className = 'carousel-card';
      div.innerHTML = `
        <img src="${m.foto || './img/default.jpg'}"
             style="width:100%;height:180px;object-fit:cover;border-radius:8px 8px 0 0;">
        <h3>${m.nome}</h3><p>${m.curso}</p>
      `;
      cont.appendChild(div);
    });
  } catch (e) {
    console.error('Erro carregarEquipe:', e);
  }
}

// 7) Quando a página carrega
window.onload = () => {
  carregarCarrossel();
  carregarSobre();
  carregarProjetos();
  carregarEquipe();
  autoScrollCarousel('project-carousel');
  autoScrollCarousel('team-carousel');
};

// 1) Scroll manual (caso use botões prev/next)
function scrollCarousel(id, direction) {
  const carousel = document.getElementById(id);
  if (!carousel) return;
  carousel.scrollBy({ left: direction * 300, behavior: 'smooth' });
}

// 2) Scroll automático pra projetos e equipe
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

// 3) Carrega banner principal
async function carregarCarrossel() {
  try {
    const res    = await fetch('carrossel.json');
    const slides = await res.json();
    const indic  = document.getElementById('carousel-indicators');
    const inner  = document.getElementById('carousel-inner');
    indic.innerHTML = '';
    inner.innerHTML = '';
    slides.forEach((item,i) => {
      const btn = document.createElement('button');
      btn.type = 'button';
      btn.setAttribute('data-bs-target','#carouselExampleIndicators');
      btn.setAttribute('data-bs-slide-to',i);
      if (i===0) btn.classList.add('active');
      indic.appendChild(btn);
      const div = document.createElement('div');
      div.className = 'carousel-item' + (i===0? ' active':'');
      div.innerHTML = `<img src="${item.imagem}" class="d-block w-100" alt="${item.alt}" style="height:700px;object-fit:cover;">`;
      inner.appendChild(div);
    });
  } catch(e) {
    console.error('Erro carregarCarrossel:', e);
  }
}

// 4) Carrega seção Sobre
async function carregarSobre() {
  try {
    const res   = await fetch('sobre.json');
    const data  = await res.json();
    const cont  = document.getElementById('sobre-container');
    cont.innerHTML = `
      <h2>${data.titulo}</h2>
      <div class="row align-items-center">
        <div class="col-md-6"><p>${data.texto}</p></div>
        <div class="col-md-6 text-center">
          <img src="${data.imagem}" alt="${data.alt}"
               style="width:400px;height:300px;position:relative;left:100px;"
               class="img-fluid rounded shadow"/>
        </div>
      </div>`;
  } catch(e) {
    console.error('Erro carregarSobre:', e);
  }
}

// 5) Carrega Projetos
async function carregarProjetos() {
  try {
    const res   = await fetch('projetos.json');
    const dados = await res.json();
    const cont  = document.getElementById('project-carousel');
    cont.innerHTML = '';
    dados.forEach(p => {
      const card = document.createElement('div');
      card.className = 'carousel-card card';
      card.innerHTML = `
        <img src="${p.imagem}" alt="${p.titulo}"
             style="width:100%;height:180px;object-fit:cover;border-radius:8px 8px 0 0;">
        <div class="card-body">
          <h5 class="card-title">${p.titulo}</h5>
          <p class="card-text">${p.descricao}</p>
        </div>`;
      cont.appendChild(card);
    });
  } catch(e) {
    console.error('Erro carregarProjetos:', e);
  }
}

// 6) Carrega Equipe
async function carregarEquipe() {
  try {
    const res   = await fetch('equipe.json');
    const dados = await res.json();
    const cont  = document.getElementById('team-carousel');
    cont.innerHTML = '';
    dados.forEach(m => {
      const div = document.createElement('div');
      div.className = 'carousel-card';
      div.innerHTML = `
        <img src="${m.foto}" style="width:100%;height:180px;object-fit:cover;border-radius:8px 8px 0 0;">
        <h3>${m.nome}</h3><p>${m.cargo}</p>`;
      cont.appendChild(div);
    });
  } catch(e) {
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

import { carregarCarrossel } from "./carrousel.js";
import { carregarSobre } from './sobre.js';
import { carregarProjetos } from './projetos.js';
import { carregarEquipe } from './equipe.js';
import { inicializarBusca } from './search.js';

function autoScrollCarousel(id) {
  const carousel = document.getElementById(id);
  if (!carousel) return;
  let dir = 1;
  setInterval(() => {
    const maxScroll = carousel.scrollWidth - carousel.clientWidth;
    if (carousel.scrollLeft >= maxScroll || carousel.scrollLeft <= 0) {
      dir *= -1;
    }
    carousel.scrollBy({ left: dir * 300, behavior: "smooth" });
  }, 4000);
}

window.onload = () => {
  carregarCarrossel();
  carregarSobre();
  carregarProjetos();
  carregarEquipe();
  autoScrollCarousel("project-carousel");
  autoScrollCarousel("team-carousel");
  // Corrigido: IDs de busca agora batem com o HTML
  inicializarBusca('search-input', 'suggestions');
};

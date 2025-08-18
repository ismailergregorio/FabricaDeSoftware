document.addEventListener('DOMContentLoaded', () => {
  const input = document.getElementById('search-input');
  const suggestionsBox = document.getElementById('suggestions');
  const searchButton = document.getElementById('search-button');

  let projetos = [];

  fetch('http://localhost:8080/projetos/getprojetos')
    .then(res => res.json())
    .then(data => {
      projetos = data;
    });

  input.addEventListener('input', () => {
    const termo = input.value.toLowerCase().trim();
    suggestionsBox.innerHTML = '';

    if (termo.length === 0) {
      suggestionsBox.classList.add('hidden');
      return;
    }

    const resultados = projetos.filter(p =>
      p.titulo.toLowerCase().includes(termo)
    );

    if (resultados.length > 0) {
      resultados.forEach(p => {
        const item = document.createElement('div');
        item.className = 'px-2 py-1 hover:bg-gray-200 cursor-pointer';
        item.textContent = p.titulo;
        item.onclick = () => {
          window.location.href = `busca.html?q=${encodeURIComponent(p.titulo)}`;
        };
        suggestionsBox.appendChild(item);
      });
      suggestionsBox.classList.remove('hidden');
    } else {
      suggestionsBox.innerHTML = '<div class="px-2 py-1 text-gray-500">Nenhum resultado encontrado</div>';
      suggestionsBox.classList.remove('hidden');
    }
  });

  input.addEventListener('keydown', e => {
    if (e.key === 'Enter') {
      e.preventDefault();
      const termo = input.value.trim();
      if (termo) {
        window.location.href = `busca.html?q=${encodeURIComponent(termo)}`;
      }
    }
  });

  searchButton.addEventListener('click', () => {
    const termo = input.value.trim();
    if (termo) {
      window.location.href = `busca.html?q=${encodeURIComponent(termo)}`;
    }
  });
});
// Carregar projeto pela URL (busca.html?q=...)
const params = new URLSearchParams(window.location.search);
const termoBusca = params.get('q');

if (termoBusca) {
  fetch('http://localhost:8080/projetos/getprojetos')
    .then(res => res.json())
    .then(projetos => {
      const projeto = projetos.find(p =>
        p.titulo.toLowerCase() === termoBusca.toLowerCase()
      );

      const resultado = document.getElementById('resultado-busca');
      if (!resultado) return;

      if (projeto) {
        resultado.innerHTML = `
          <h2 class="text-2xl font-bold mb-4">${projeto.titulo}</h2>
          <img src="${projeto.imagem}" alt="${projeto.titulo}" class="mb-4" style="max-width: 600px;">
          <p class="mb-2">${projeto.descricao}</p>
          ${projeto.github ? `<p><strong>GitHub:</strong> <a href="${projeto.github}" target="_blank">${projeto.github}</a></p>` : ''}
          ${projeto.video ? `<p><strong>Vídeo:</strong> <a href="${projeto.video}" target="_blank">${projeto.video}</a></p>` : ''}
        `;
      } else {
        resultado.innerHTML = `<p class="text-red-500">Projeto "${termoBusca}" não encontrado.</p>`;
      }
    });
}


document.addEventListener('DOMContentLoaded', () => {
  const input = document.getElementById('search-input');
  const suggestionsBox = document.getElementById('suggestions');
  const searchButton = document.getElementById('search-button');

  let projetos = [];

  fetch('Projetos.json')
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

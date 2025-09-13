// busca.js
export function inicializarBusca(inputId, suggestionsId) {
  const input = document.getElementById(inputId);
  const suggestionsBox = document.getElementById(suggestionsId);
  const searchButton = document.getElementById('search-button');

  let projetos = [];

  // Busca os projetos da API
  fetch('http://localhost:8080/projetos/getprojetos')
    .then(res => res.json())
    .then(data => {
      projetos = data;
    })
    .catch(err => console.error("Erro ao buscar projetos:", err));

  // Sugestões automáticas enquanto o usuário digita
  input.addEventListener('input', () => {
    const termo = input.value.toLowerCase().trim();
    suggestionsBox.innerHTML = '';

    if (!termo) {
      suggestionsBox.classList.add('hidden');
      return;
    }

    const resultados = projetos.filter(p =>
      p.nomeDoProjeto.toLowerCase().includes(termo)
    );

    if (resultados.length > 0) {
      resultados.forEach(p => {
        const item = document.createElement('div');
        item.className = 'px-2 py-1 hover:bg-gray-200 cursor-pointer';
        item.textContent = p.nomeDoProjeto;
        item.onclick = () => {
          window.location.href = `busca.html?q=${encodeURIComponent(p.nomeDoProjeto)}`;
        };
        suggestionsBox.appendChild(item);
      });
      suggestionsBox.classList.remove('hidden');
    } else {
      suggestionsBox.innerHTML = '<div class="px-2 py-1 text-gray-500">Nenhum resultado encontrado</div>';
      suggestionsBox.classList.remove('hidden');
    }
  });

  // Botão de busca
  searchButton.addEventListener('click', () => {
    const termo = input.value.trim();
    if (termo) {
      window.location.href = `busca.html?q=${encodeURIComponent(termo)}`;
    }
  });
}

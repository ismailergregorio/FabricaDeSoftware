document.addEventListener('DOMContentLoaded', () => {
  const params = new URLSearchParams(window.location.search);
  const termoBusca = params.get('q');
  const container = document.getElementById('resultado');
  const input = document.getElementById('search-input');
  const suggestionsBox = document.getElementById('suggestions');
  const searchButton = document.getElementById('search-button');
  let projetos = [];

  // Carregar todos os projetos da API
  fetch('http://localhost:8080/projetos/getprojetos')
    .then(res => res.json())
    .then(data => {
      projetos = data;

      // Se veio com termo pela URL
      if (termoBusca) {
        buscarProjeto(termoBusca);
        input.value = termoBusca;
      }
    })
    .catch(err => {
      console.error('Erro ao buscar projetos da API:', err);
      container.innerHTML = `<p>Erro ao carregar os dados.</p>`;
    });

  // Função para exibir projeto exato
  function buscarProjeto(termo) {
    const projeto = projetos.find(p =>
      p.nomeDoProjeto.toLowerCase().trim() === termo.toLowerCase().trim()
    );

    if (projeto) {
      container.innerHTML = `
        <div class="card mx-auto" style="width: 400px;">
          <img src="${projeto.linkImage}" class="card-img-top" alt="${projeto.nomeDoProjeto}">
          <div class="card-body">
            <h5 class="card-title">${projeto.nomeDoProjeto}</h5>
            <p class="card-text">${projeto.descricaoDoProjeto}</p>
            <a href="${projeto.linkGit}" target="_blank" class="btn btn-dark">GitHub</a>
          </div>
        </div>
      `;
    } else {
      container.innerHTML = `<p>O projeto "<strong>${termo}</strong>" não foi encontrado.</p>`;
    }
  }

  // Sugestões enquanto digita
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
    } else {
      suggestionsBox.innerHTML = '<div class="px-2 py-1 text-gray-500">Nenhum resultado encontrado</div>';
    }
    suggestionsBox.classList.remove('hidden');
  });

  // Buscar ao pressionar Enter
  input.addEventListener('keydown', e => {
    if (e.key === 'Enter') {
      e.preventDefault();
      const termo = input.value.trim();
      if (termo) {
        window.location.href = `busca.html?q=${encodeURIComponent(termo)}`;
      }
    }
  });

  // Buscar ao clicar no botão
  searchButton.addEventListener('click', () => {
    const termo = input.value.trim();
    if (termo) {
      window.location.href = `busca.html?q=${encodeURIComponent(termo)}`;
    }
  });
});

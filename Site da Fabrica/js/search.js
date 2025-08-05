export function inicializarBusca(inputId, listaId) {
  const input = document.getElementById(inputId);
  const sugestoes = document.getElementById(listaId);

  input.addEventListener('input', async () => {
    const termo = input.value.trim().toLowerCase();
    sugestoes.innerHTML = '';

    if (termo.length === 0) return;

    try {
      const res = await fetch('http://localhost:8080/projetos/getprojetos');
      const projetos = await res.json();

      const filtrados = projetos.filter(p =>
        p.nomeDoProjeto.toLowerCase().includes(termo)
      );

      filtrados.forEach(p => {
        const li = document.createElement('li');
        li.textContent = p.nomeDoProjeto;
        li.classList.add('list-group-item', 'list-group-item-action');
        li.style.cursor = 'pointer';
        li.addEventListener('click', () => {
          window.open(`busca.html?q=${encodeURIComponent(p.nomeDoProjeto)}`, '_blank');
        });
        sugestoes.appendChild(li);
      });
    } catch (err) {
      console.error('Erro ao buscar projetos para sugestão:', err);
    }
  });

  // Ocultar sugestões ao clicar fora
  document.addEventListener('click', (e) => {
    if (!sugestoes.contains(e.target) && e.target !== input) {
      sugestoes.innerHTML = '';
    }
  });
}

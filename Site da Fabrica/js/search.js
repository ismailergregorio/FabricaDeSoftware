// search.js
export function inicializarBusca(inputId, suggestionsId) {
  const input = document.getElementById(inputId);
  const suggestions = document.getElementById(suggestionsId);

  if (!input || !suggestions) return;

  input.addEventListener("input", () => {
    const value = input.value.trim().toLowerCase();
    suggestions.innerHTML = "";

    if (value.length > 0) {
      const results = ["Projeto A", "Projeto B", "Membro X"].filter(item =>
        item.toLowerCase().includes(value)
      );

      results.forEach(result => {
        const item = document.createElement("button");
        item.className = "list-group-item list-group-item-action";
        item.textContent = result;
        suggestions.appendChild(item);
      });
    }
  });
}

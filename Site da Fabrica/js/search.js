
export async function inicializarBusca(inputId, suggestionsId) {

  const res = await fetch("http://localhost:8080/config/config");
  const data = await res.json();
 


  const input = document.getElementById(inputId);
  const suggestions = document.getElementById(suggestionsId);

  if (!input || !suggestions) return;

  input.addEventListener("input", () => {
    const value = input.value.trim().toLowerCase();
    
    suggestions.innerHTML = "";

    if (value.length > 0) {
      const itesns = data.map(item => item.codigoProjeto);
      console.log(itesns)
      results.forEach(result => {
        const item = document.createElement("button");
        item.nomeDoProjeto = "list-group-item list-group-item-action";
        item.textContent = result;
        suggestions.appendChild(item);
      });
    }
  });
}

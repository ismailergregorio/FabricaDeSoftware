document.getElementById('formulario').addEventListener('submit', function (e) {
  e.preventDefault();

  // Dados do formulário (opcional: você pode enviar para um backend depois)
  const nome = document.getElementById('nome').value;
  const curso = document.getElementById('curso').value;
  const habilidades = document.getElementById('habilidades').value;
  const disponibilidade = document.getElementById('disponibilidade').value;

  // Aqui você pode usar fetch() ou outro método para enviar os dados a um servidor

  alert("Inscrição enviada com sucesso! Em breve a equipe da Fábrica entrará em contato.");
  this.reset();
});

// Seleciona o form pelo ID
const form = document.getElementById('cadastro-form');

form.addEventListener('submit', async (e) => {
  e.preventDefault(); // evita recarregar a página

  // Monta o objeto a partir dos campos do form
  const data = {
    first_name: form.first_name.value.trim(),
    last_name:  form.last_name.value.trim(),
    email:      form.email.value.trim(),
    periodo:    form.periodo.value.trim(),
    matricula:  form.matricula.value.trim()
  };

  try {
    const response = await fetch('/admin', {     // ou sua rota de API
      method:  'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    });

    if (!response.ok) {
      throw new Error(`Erro ${response.status}`);
    }

    alert('Cadastro enviado com sucesso!');
    form.reset();

  } catch (err) {
    console.error('Falha no cadastro:', err);
    alert('Não foi possível enviar. Tente novamente.');
  }
});

document.getElementById("cadastro-form")
  .addEventListener("submit", async function (e) {
    e.preventDefault();

    const formData = new FormData(this);

    const dados = {
      nome: formData.get("nome"),
      ra: formData.get("ra"),
      emailInstitucional: formData.get("emailInstitucional"),
      curso: formData.get("curso"),
      motivo: formData.get("motivo")
    };

    try {
      const res = await fetch("http://localhost:8080/email/inscricao", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(dados)
      });

      if (res.ok) {
        alert("Inscrição enviada com sucesso!");
        this.reset();
      } else {
        alert("Erro ao enviar inscrição.");
      }

    } catch (err) {
      console.error(err);
      alert("Erro de conexão com o servidor.");
    }
});
export function habilitarBtn(valorDigitado, textoOriginal, id_btn) {
  const valor_form = String(valorDigitado).trim();
  const btn = document.getElementById(id_btn);
  if (!btn) return;

  btn.disabled = !(
    valor_form !== "" && valor_form !== String(textoOriginal).trim()
  );
}

export function CarregandoImagem(event, id_btn,setImg) {
  const url = event.target.value.trim();
  const btn = document.getElementById(id_btn);

  // atualiza o preview DA IMAGEM
  setImg(url);

  // habilita/desabilita botão caso queira
  if (btn) {
    btn.disabled = url === "";
    return false
  }else{
    btn.disabled=false
    return true
  }
}

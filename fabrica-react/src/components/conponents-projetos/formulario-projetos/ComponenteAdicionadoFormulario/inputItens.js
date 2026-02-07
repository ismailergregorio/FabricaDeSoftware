import { useEffect, useState } from "react";

function InputItens({
  Titulo,
  dadosEntrada,
  dadosInceridos,
  chave1,
  chave2,
  onChange,
}) {
  const [mostrarLista, setMostrarLista] = useState(false);
  const [itenSelecionado, setItenSelecionado] = useState([]);

  useEffect(() => {
    if (!Array.isArray(dadosInceridos)) return;

    setItenSelecionado((prev) => {
      const novos = dadosInceridos.filter((d) => !prev.includes(d));
      return [...prev, ...novos];
    });
  }, [dadosInceridos]);

  // Adiciona item (não duplica)
  function addItensSelecionado(iten) {
    setItenSelecionado((prev) => {
      if (prev.includes(iten)) return prev;
      return [...prev, iten];
    });
  }

  // Remove item selecionado
  function removeItem(iten) {
    setItenSelecionado((prev) => prev.filter((item) => item !== iten));
  }

  // Envia os itens selecionados para o componente pai
  useEffect(() => {
    if (onChange) {
      onChange(itenSelecionado);
    }
  }, [itenSelecionado, onChange]);

  return (
    <div className="addMais">
      {/* TOPO */}
      <div className="topco">
        <label htmlFor="professor">{Titulo}</label>

        <div className="textBtnAdicionar">
          <h2>Adicionar:</h2>

          <button
            type="button"
            onClick={() => setMostrarLista((prev) => !prev)}
          >
            <i className="fa-regular fa-plus"></i>
          </button>
        </div>
      </div>

      {/* LISTA DE SELEÇÃO */}
      {mostrarLista && (
        <div className="listaItens">
          {dadosEntrada.map((g) => {
            const valor = g[chave1];
            const nome = g[chave2];
            const jaSelecionado = itenSelecionado.includes(valor);

            return (
              <div
                className={jaSelecionado ? "item-desativado" : "item"}
                key={valor}
                onClick={() => addItensSelecionado(valor)}
                disabled={jaSelecionado}
              >
                <h1>{nome}</h1>
                <h1>{valor}</h1>
              </div>
            );
          })}
        </div>
      )}

      {/* ITENS SELECIONADOS */}
      <div className="listaItensSelecionados">
        {itenSelecionado.length === 0 && <p>Nenhum item selecionado</p>}

        {itenSelecionado.map((item, index) => (
          <div
            className="itenSelecionado"
            key={index}
            onClick={() => removeItem(item)}
          >
            {item}
            <div>❌</div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default InputItens;

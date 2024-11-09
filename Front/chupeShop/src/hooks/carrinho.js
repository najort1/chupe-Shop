const adicionarAoCarrinho = (produto) => {
    let carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];
    carrinho.push(produto);

    localStorage.setItem('carrinho', JSON.stringify(carrinho));
};


const obterCarrinho = () => {
    // Recuperar o carrinho do localStorage
    return JSON.parse(localStorage.getItem('carrinho')) || [];
};

const removerDoCarrinho = (produtoId) => {
  console.log("removendo do carrinho" + produtoId);
    // Recuperar o carrinho atual do localStorage
    let carrinho = obterCarrinho();

    // Remover o produto do carrinho
    carrinho = carrinho.filter(produto => produto.id !== produtoId);

    // Salvar o carrinho atualizado no localStorage
    localStorage.setItem('carrinho', JSON.stringify(carrinho));

    return carrinho;
};
  
const atualizarQuantidade = (produtoId, novaQuantidade) => {
    // Recuperar o carrinho atual do localStorage
    let carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];
  
    // Atualizar a quantidade do produto
    carrinho = carrinho.map(produto => {
      if (produto.produtoId === produtoId) {
        return { ...produto, quantidade: novaQuantidade };
      }
      return produto;
    });
  
    // Salvar o carrinho atualizado no localStorage
    localStorage.setItem('carrinho', JSON.stringify(carrinho));
};
  

export {
    adicionarAoCarrinho,
    obterCarrinho,
    removerDoCarrinho,
    atualizarQuantidade,
};

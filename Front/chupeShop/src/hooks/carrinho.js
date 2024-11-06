const adicionarAoCarrinho = (produto) => {
    let carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];

    for(const item of carrinho){
      
      if(item.produtoId === produto.produtoId){
        item.quantidade += produto.quantidade;
        item.preco += produto.preco * produto.quantidade;
        item.preco = parseFloat(item.preco.toFixed(2));
        localStorage.setItem('carrinho', JSON.stringify(carrinho));
        //
        return;
      }

    }
  
    carrinho.push(produto);
  
    localStorage.setItem('carrinho', JSON.stringify(carrinho));
};


const obterCarrinho = () => {
    // Recuperar o carrinho do localStorage
    return JSON.parse(localStorage.getItem('carrinho')) || [];
};

const removerDoCarrinho = (produtoId) => {
    // Recuperar o carrinho atual do localStorage
    let carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];
  
    // Filtrar o produto a ser removido
    carrinho = carrinho.filter(produto => produto.produtoId !== produtoId);
  
    // Salvar o carrinho atualizado no localStorage
    localStorage.setItem('carrinho', JSON.stringify(carrinho));
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

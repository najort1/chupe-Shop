import Header from "../Header";
import Footer from "../Footer";
import chupeta3 from "../../assets/download.jpeg";
import { useLocation } from "react-router-dom";
import { Image, Input } from "@nextui-org/react";
import { useState, useEffect } from "react";
import axios from "axios";
import * as carrinho from "../../hooks/carrinho.js";


const ProdutoDetalhes = ({usuarioLogado, setUsuariologado}) => {


  const [erro, setErro] = useState("");
  const location = useLocation();
  const { produto } = location.state;
  const [value, setValue] = useState(1);
  const produtoId = produto.id;
  const [headerKey, setHeaderKey] = useState(0); // Estado para forçar a re-renderização do Header


  const handleAdicionarProduto = async () => {
    try {
      const response = await axios.post(`http://localhost:8080/pedido/fazer-pedido`, {
        produtoId,
        quantidade: value,


      },
    {
      validateStatus: function (status) {
        return status <= 500;
      },
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      }
    });
      if(response.status === 200){
        setErro("Produto adicionado ao carrinho com sucesso");
        carrinho.adicionarAoCarrinho(response.data);
        setHeaderKey(prevKey => prevKey + 1); // Atualiza o estado para forçar a re-renderização do Header
      }else{
        setErro(response.data.detail);
      }
    } catch (error) {
      console.error(error);
      setErro("Ocorreu um erro ao adicionar o produto ao carrinho");
    }
  };




  return (
    <>
            <Header key={headerKey} UserLoggedIn={usuarioLogado} setUsuarioLogado={setUsuariologado}/>

      <div
        className="produtoDetalhes text-black grid grid-cols-2 grid-rows-1 gap-[50px] mt-20 xl:flex xl:justify-center xl:items-center xl:gap-[50px]"
      >
        <h1 className="erro text-2xl font-bold text-red-500">{erro}</h1>
        <div className="produtoDetalhesImagem">
          <Image isZoomed width={240} alt={produto.nome} src={produto.imagem} />
        </div>
        <div className="produtoDetalhesDescricao">
          <h1 className="text-2xl font-bold dark:text-white">
            {produto.nome} {produto.preco}
          </h1>
          <div className="botoes flex flex-col gap-4 mt-10">
          <button className="bg-primary-500 text-white py-2 px-4 rounded mt-10 w-full xl:w-full" onClick={handleAdicionarProduto}>Comprar</button>
          <Input
      type="number"
      max={produto.estoque}
      label="Quantidade"
      name="quantidade"
      placeholder="Digite a quantidade"
      value={value}
      onChange={(e) => setValue(e.target.value)}
      className="max-w-md"
      defaultValue="1"
    />
          </div>

        </div>
      </div>
      <div className="informacoesProduto flex-col">
        <p className="mt-10 mb-10 text-3xl text-center font-bold">
          Informações Produto
        </p>
        <p className="mt-2 ">{produto.descricao}</p>
      </div>
      <Footer />
    </>
  );
};

export default ProdutoDetalhes;

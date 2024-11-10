import Header from "../Header";
import Footer from "../Footer";
import chupeta3 from "../../assets/download.jpeg";
import { useLocation } from "react-router-dom";
import { Image, Input } from "@nextui-org/react";
import { useState, useEffect } from "react";
import axios from "axios";
import * as carrinho from "../../hooks/carrinho.js";
import { useNavigate } from "react-router-dom";

const ProdutoDetalhes = ({ usuarioLogado, setUsuariologado }) => {
  const [erro, setErro] = useState("");
  const location = useLocation();
  const { produto } = location.state;
  const [value, setValue] = useState(1);
  const produtoId = produto.id;
  const [headerKey, setHeaderKey] = useState(0); // Estado para forçar a re-renderização do Header
  const navigate = useNavigate();

  const converterTokenGoogle = async () => {
    const token = localStorage.getItem("token");
    const response = await axios.post(
      `http://localhost:8080/auth/google-login`,
      {
        token,
      },
      {
        validateStatus: function (status) {
          return status <= 500;
        },
      }
    );

    if (response.status === 200) {
      localStorage.setItem("token", response.data);
    }

    setErro(response.data.detail);
  };

  const modalAviso = (titulo, mensagem) => {
    return (
      <div className="modalOverlay fixed inset-0 bg-black bg-opacity-50 z-40 flex items-center justify-center">
        <div className="modalAviso bg-blue-500 text-black font-bold p-6 z-50 w-[60%]
          xl:w-[30%] rounded-lg shadow-2xl
        ">
          <div className="modalAvisoContent bg-white p-4 rounded-lg flex flex-col items-center gap-2">
            <h1 className="modalAvisoTitulo text-2xl font-bold">{titulo}</h1>
            <p className="modalAvisoMensagem">{mensagem}</p>
            <button className="modalAvisoBotao bg-primary-500 text-white p-2 rounded-md" onClick={() => setErro("")}>Fechar</button>
          </div>
        </div>
      </div>
    );
  };


  const handleAdicionarProduto = async () => {
    try {
      const response = await axios.post(
        `http://localhost:8080/pedido/fazer-pedido`,
        {
          produtoId,
          quantidade: value,
        },
        {
          validateStatus: function (status) {
            return status <= 500;
          },
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );
      if (response.status === 200) {
        setErro("Produto adicionado ao carrinho com sucesso");
        carrinho.adicionarAoCarrinho(response.data);
        setHeaderKey((prevKey) => prevKey + 1); // Atualiza o estado para forçar a re-renderização do Header
      }else if(response.data.detail.includes('JWT strings must contain exactly 2 period')){
        setErro("Faça login para adicionar o produto ao carrinho");
        setUsuariologado(false);
        localStorage.removeItem("token");
        navigate("/login");
        return;
      }
      
      else {
        if (
          response.data.detail.includes(
            "The parsed JWT indicates it was signed with the 'RS256'"
          )
        ) {
          await converterTokenGoogle();
          return await handleAdicionarProduto();
        }

        setErro(response.data.detail);
      }
    } catch (error) {
      console.error(error);
      setErro("Ocorreu um erro ao adicionar o produto ao carrinho");
    }
  };

  return (
    <>
      {erro && modalAviso("Aviso", erro)}
      <Header
        key={headerKey}
        UserLoggedIn={usuarioLogado}
        setUsuarioLogado={setUsuariologado}
      />

      <div className="produtoDetalhes text-black flex flex-row
      
      md:justify-center
      md:gap-10
      md:mt-10

      ">
        <div className="produtoDetalhesImagem">
          <Image width={240} alt={produto.nome} src={produto.imagem} radius="none"/>
        </div>
        <div className="produtoDetalhesDescricao">
          <h1 className="text-2xl font-bold text-center dark:text-white">
            {produto.nome} {produto.preco}
          </h1>
          <div className="botoes flex flex-col gap-4">
            <button
              className="bg-primary-500 text-white py-2 px-4 rounded mt-10 w-full xl:w-full"
              onClick={handleAdicionarProduto}
            >
              Comprar
            </button>
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

import Header from "../Header";
import Footer from "../Footer";
import chupeta3 from "../../assets/download.jpeg";
import { useLocation } from "react-router-dom";
import { Image } from "@nextui-org/react";

const ProdutoDetalhes = ({usuarioLogado, setUsuariologado}) => {
  const location = useLocation();
  const { produto } = location.state;

  return (
    <>
            <Header UserLoggedIn={usuarioLogado} setUsuarioLogado={setUsuariologado}/>

      <div
        className="produtoDetalhes text-black grid grid-cols-2 grid-rows-1 gap-[50px] mt-20 xl:flex xl:justify-center xl:items-center xl:gap-[50px]"
      >
        <div className="produtoDetalhesImagem">
          <Image isZoomed width={240} alt={produto.nome} src={produto.imagem} />
        </div>
        <div className="produtoDetalhesDescricao">
          <h1 className="text-2xl font-bold dark:text-white">
            {produto.nome} {produto.preco}
          </h1>
          <button
            className="bg-primary-500 text-white py-2 px-4 rounded mt-10 w-full xl:w-full"
          >
            Comprar
          </button>
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

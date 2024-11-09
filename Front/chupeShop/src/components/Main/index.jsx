import Header from "../Header";
import Footer from "../Footer";
import DetalheProduto from "../ProdutoDetalhes";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useEffect } from "react";

import { Card, CardBody, CardFooter, Image } from "@nextui-org/react";
import {PaginationItem, PaginationCursor} from "@nextui-org/react";
import { Pagination as PaginationNextUI } from "@nextui-org/react";


import { Swiper, SwiperSlide } from "swiper/react";
import { Pagination, A11y, Autoplay } from "swiper/modules";
import "swiper/css";
import "swiper/css/pagination";
import "swiper/css/autoplay";

// Ajuste o caminho conforme necessário
import chupeta1 from "../../assets/images.jpeg";
import chupeta2 from "../../assets/images (1).jpeg";
import chupeta3 from "../../assets/download.jpeg";
import chupeta4 from "../../assets/download (1).jpeg";
import chupeta5 from "../../assets/download (2).jpeg";

const Principal = ({produtos = [], usuarioLogado, setUsuariologado,setProdutos,paginaAtual,paginasTotais,setPaginaAtual}) => {
  const navigate = useNavigate();

  const fetchProdutos = async (page) => {
    page = page == 0 ? 0 : page - 1;
    const response = await axios.get(`http://localhost:8080/produtos/listar?page=${page}&size=10`, {
      validateStatus: function (status) {
        return status <= 500;
      }
    });
    setProdutos(response.data.content);
  };

  useEffect(() => {
    fetchProdutos(paginaAtual);
  }, [paginaAtual]);

  const handlePesquisa = async (e) => {
    const pesquisa = e.target.value;
    const response = await axios.get(`http://localhost:8080/produtos/listar/nome/?nome=${pesquisa}`,{
      validateStatus: function (status) {
        return status <= 500;
      }
    });


    setProdutos(response.data);
  }

  return (
    <>
            <Header UserLoggedIn={usuarioLogado} setUsuarioLogado={setUsuariologado}/>



      <main>
        <section className="carousel mt-7 border-b-4 ">

          <h1>
            <span className="text-2xl font-bold text-default-900">Principais Chupetas</span>
          </h1>

          <Swiper
            modules={[Pagination, A11y, Autoplay]}
            spaceBetween={50}
            slidesPerView={4}
            autoplay={{ disableOnInteraction: true }}
            pagination={{ clickable: true }}
          >
            <SwiperSlide>
              <img src={chupeta1} alt="Chupeta 1" />
            </SwiperSlide>
            <SwiperSlide>
              <img src={chupeta2} alt="Chupeta 2" />
            </SwiperSlide>
            <SwiperSlide>
              <img src={chupeta3} alt="Chupeta 3" />
            </SwiperSlide>
            <SwiperSlide>
              <img src={chupeta4} alt="Chupeta 4" />
            </SwiperSlide>
            <SwiperSlide>
              <img src={chupeta5} alt="Chupeta 5" />
            </SwiperSlide>
          </Swiper>
        </section>

        <div className="sessaoProdutos">
          <h1 className="tituloProdutos
          text-center mt-10 text-2xl font-bold text-default-900
          ">Produtos</h1>

          <input type="text" onChange={handlePesquisa} placeholder="Pesquise por uma chupeta especifica" name="pesquisaProduto" id="" className="pesquisaProduto
          
          w-full
          h-10
          border-2
          border-gray-300
          rounded-md
          mt-10
          p-4
          focus:outline-none
          focus:ring-2
          focus:ring-primary-500

          " />

          <section className="produtos gap-2 grid grid-cols-3 mt-20
            xl:grid-cols-10
            md:grid-cols-4
          ">
            {Array.isArray(produtos) && produtos.map((item, index) => (

              <div className="card" key={index}>
                <div className="card-image">
                <Image
                    shadow="sm"
                    radius="lg"
                    width="100%"
                    alt={item.nome}
                    className="w-full object-cover"
                    src={item.imagem}
                    onClick={() => navigate("/produto-detalhes", { state: { produto: item } })}
                  />
                </div>
                <div className="card-content bg-gray-300 rounded-md drop-shadow-xl">
                  <h1 className="titulo font-bold text-center dark:text-black">{item.nome}</h1>
                  <p className="preco text-center dark:text-black">R$ {item.preco}</p>
                  <p className="estoque text-center dark:text-black">Estoque: {item.estoque}</p>
                </div>
              </div>
            ))}
            
          </section>
          
          <div className="paginacao
          flex
          justify-center
          mt-10
          ">
            <PaginationNextUI loop showControls total={paginasTotais} initialPage={1} onChange={(page) => setPaginaAtual(page)} />

            </div>
        </div>
      </main>
    </>
    
  );
};

export default Principal;

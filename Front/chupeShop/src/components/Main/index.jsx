import Header from "../Header";
import Footer from "../Footer";
import DetalheProduto from "../ProdutoDetalhes";
import { useNavigate } from "react-router-dom";

import { Card, CardBody, CardFooter, Image } from "@nextui-org/react";

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

const Principal = ({produtos}) => {
  const navigate = useNavigate();
  return (
    <>
      <Header />

      <main>
        <section className="carousel mt-7 border-b-4">

          <h1>
            <span className="text-2xl font-bold text-default-900">Principais Chupetas</span>
          </h1>

          <Swiper
            modules={[Pagination, A11y, Autoplay]}
            spaceBetween={50}
            slidesPerView={3}
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
          <section className="produtos gap-2 grid grid-cols-3 mt-20
            xl:grid-cols-8
            md:grid-cols-4
          ">
            {produtos.map((item, index) => (
              <Card
                shadow="sm"
                key={index}
                isPressable
                onPress={() => navigate("/produto-detalhes", { state: { produto: item } })}
              >
                <CardBody className="overflow-visible p-0">
                  <Image
                    shadow="sm"
                    radius="lg"
                    width="100%"
                    alt={item.title}
                    className="w-full object-cover"
                    src={item.img}
                  />
                </CardBody>
                <CardFooter className="text-small justify-between">
                  <b>{item.title}</b>
                  <p className="text-default-500">{item.price}</p>
                </CardFooter>
              </Card>
            ))}
          </section>
        </div>
      </main>
      <Footer />
    </>
    
  );
};

export default Principal;

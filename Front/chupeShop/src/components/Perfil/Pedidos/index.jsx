import Header from "../../Header";
import Footer from "../../Footer";
import axios from "axios";
import { useState, useEffect } from "react";
import { Pagination as PaginationNextUI } from "@nextui-org/react";

const Pedidos = ({ usuarioLogado, setUsuariologado }) => {
  const [pedidos, setPedidos] = useState([]);
  const [erro, setErro] = useState("");

  const capturarPedidosFinalizados = async () => {
    const response = await axios.get("http://localhost:8080/pedido/pedidos-finalizados", {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
      validateStatus: function (status) {
        return status <= 500;
      },
    });

    if (response.status === 200) {
      setPedidos(response.data);
    } else {
      setErro(response.data.detail);
    }
  };

  const capturarTodosOsPedidos = async () => {
    const response = await axios.get("http://localhost:8080/pedido/meus-pedidos", {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
      validateStatus: function (status) {
        return status <= 500;
      },
    });

    if (response.status === 200) {
      setPedidos(response.data.content);
    } else {
      setErro(response.data.detail);
    }
  };


  useEffect(() => {
    capturarTodosOsPedidos();
  }, []);

  return (
    <>
      <Header setUsuarioLogado={setUsuariologado} usuarioLogado={usuarioLogado} />

      <div className="container mx-auto p-6 ">
        <h1 className="text-3xl font-bold mb-6 text-center">Meus Pedidos</h1>
        <div className="flex flex-col gap-10">
          {pedidos.map((pedido) => {
            return (
              <div key={pedido.id} className="bg-white p-4 rounded-lg shadow-2xl">
                <h3 className="text-2xl font-medium mb-2 text-center">Produtos</h3>
                <ul className="list-disc list-inside mb-4">
                  {pedido.produtos.map((produto) => {
                    return <li key={produto.id} className="text-lg">{produto.nome}</li>;
                  })}
                </ul>
                <h3 className="text-xl font-medium">Valor Total: <span className="font-bold">R$ {pedido.preco * pedido.quantidade}</span></h3>
                <h3 className="text-xl font-medium">Data: <span className="font-bold">{pedido.data.split("T")[0].split("-").reverse().join("/")}</span></h3>
                <h3 className="text-xl font-medium">
                  Status:{" "}
                  <span
                    className={`font-bold ${
                      pedido.status === "PENDENTE" ? "text-red-500" : pedido.status === "FINALIZADO" ? "text-green-500" : ""
                    }`}
                  >
                    {pedido.status}
                  </span>
                </h3>
              </div>
            );
          })}
        </div>
        <div
            className="paginacao
          flex
          justify-center
          mt-10
          "
          >
            <PaginationNextUI
              loop
              showControls
              total={2}
              initialPage={1}
              onChange={(page) => setPaginaAtual(page)}
            />
          </div>
      </div>

      <Footer />
    </>
  );
};

export default Pedidos;
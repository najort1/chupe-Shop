import { Sidebar } from "primereact/sidebar";
import { Button } from "primereact/button";
import { useState } from "react";
import boxicons from "boxicons";
import { Avatar } from "primereact/avatar";
const Header = () => {
  const [visible, setVisible] = useState(false);

  const handleOpenSidebar = () => {
    setVisible(true);
  };
  
  return (
    <>
      <Sidebar
        visible={visible}
        onHide={() => setVisible(false)}
        className="w-[60%] h-full relative shadow-2xl bg-[#718CB3] xl:w-[15%] overflow-auto
        md:w-[30%]
        "
        content={({ closeIcon, hide }) => (
          <div className="h-full flex flex-col justify-between">
            <div className="flex items-center p-4 md:justify-center">
              <img
                src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQIMDsWFBoBCZUhHBwy7G9G65_pJ2SZngH5BQ&s"
                alt="Usuario logado"
                className="usuario-logado-foto rounded-full w-16 object-cover"
              />

              <button
                onClick={hide}
                className="text-white text-3xl hover:text-gray-300"
              >
                <box-icon type="solid" name="x-circle"></box-icon>
              </button>

            </div>
            <div className="flex flex-col items-center gap-4">
              <div className="nav-item flex flex-row items-center gap-2 hover:text-gray-300">
                <box-icon type="solid" name="home"></box-icon>
                <a href="#" className="text-white text-xl">
                  Inicio
                </a>
              </div>
              <div className="nav-item flex flex-row items-center gap-2 hover:text-gray-300">
                <box-icon name="file-plus" type="solid"></box-icon>
                <a href="#" className="text-white text-xl">
                  Novo produto
                </a>
              </div>
              <div className="nav-item flex flex-row items-center gap-2 hover:text-gray-300">
                <box-icon name="spreadsheet"></box-icon>
                <a href="#" className="text-white text-xl">
                  Produtos
                </a>
              </div>
            </div>

            <div className="flex flex-row justify-center items-center p-4">
              <button className="botao-sair text-white font-2xl font-bold hover:text-gray-300 bg-red-600 w-full h-12">
                Sair
              </button>
            </div>
          </div>
        )}
      ></Sidebar>
      <header className="header-dashboard flex flex-row w-full shadow-xl p-2 items-center">
        <button
          className="abrir-side-bar hover:text-gray-300"
          onClick={() => setVisible(true)}
        >
          <box-icon name="menu" size="lg"></box-icon>
        </button>
        <h1 className="titulo-dashboard text-blue-800 text-2xl flex justify-center items-center font-bold m-auto">
          ChupeShop
        </h1>
      </header>
    </>
  );
};
export default Header;

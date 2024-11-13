import { Sidebar } from "primereact/sidebar";
import { Button } from "primereact/button";
import { useState } from "react";
import boxicons from "boxicons";
import { Avatar } from 'primereact/avatar';
const SaideBar = () => {

  const [visible, setVisible] = useState(true);

  const handleOpenSidebar = () => {
    setVisible(true);
  }


  return (
    <>
        <Sidebar visible={visible} onHide={() => setVisible(false)}>
          <div className="conteudo-sidebar bg-gray-200 h-dvh w-40 shadow-2xl flex flex-col">
            <div className="titulo-sidebar">
              <h1 className="titulo text-black text-center font-bold text-xl mb-4">ChupeChop</h1>
            </div>

            <div className="items-sidebar flex flex-col justify-center align-center">
            
                    <div className="item-sidebar flex flex-row">
                        {<box-icon name='home' ></box-icon>}
                        <a href="/" className="text-black text-sm font-bold">Dashboard</a>
                    </div>
                    <div className="item-sidebar flex flex-row">
                        {<box-icon name='task' ></box-icon>}
                        <a href="/" className="text-black text-sm font-bold">Cadastrar produto</a>
                    </div>
                    <div className="item-sidebar flex flex-row">
                        {<box-icon name='store' ></box-icon>}
                        <a href="/" className="text-black text-sm font-bold">Produtos</a>
                    </div>

            </div>

            <div className="botao-sair flex flex-col mt-12">
                <button className="bg-red-500 text-white font-bold p-2 rounded-md">Deslogar</button>
            </div>

          </div>
        </Sidebar>
        {!visible && (
        <Button
          icon={<box-icon name="menu" color="#00ffec" size='md'></box-icon>}
          onClick={handleOpenSidebar}
        />
      )}
    </>
  );
};

export default SaideBar;

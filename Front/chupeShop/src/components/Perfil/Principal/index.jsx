import Header from "../../Header";
import Footer from "../../Footer";
import { useState, useEffect } from "react";
import { Image } from "@nextui-org/react";
import { jwtDecode } from "jwt-decode";

const PrincipalPerfil = ({usuarioLogado, setUsuariologado}) => {
  const [nome, setNome] = useState("Chupetao");
  const [email, setEmail] = useState("adm@adm.com");
  const [foto, setFoto] = useState(
    "https://i.pinimg.com/564x/f3/6b/fa/f36bfa3b60559e7da0014f91250abf66.jpg"
  );
  const [desabilitado, setDesabilitado] = useState(true);
  const [cpf, setCpf] = useState("123.456.789-00");

  useEffect(() => {
    getUsuario();
  }, []);

  const getUsuario = () => {
    if (!localStorage.getItem("token")) {
      return;
    }

    const token = localStorage.getItem("token");
    const usuario = jwtDecode(token);
    setNome(usuario.nome);
    setEmail(usuario.sub);
    setFoto(usuario.imagem);
  };

  return (
    <>
        <Header UserLoggedIn={usuarioLogado} setUsuarioLogado={setUsuariologado} />

      <div className="upbar w-full bg-gray-900 p-4">
        <div className="upbar-content">
          <div className="upbar-menu flex flex-row gap-4 text-white justify-center text-xl font-bold">
            <a href="#" className="hover:text-gray-400">
              Meus pedidos
            </a>
            <a href="#" className="hover:text-gray-400">
              Endereços
            </a>
          </div>
        </div>
      </div>

      <div className="perfil-content absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-white p-4 rounded-lg shadow-2xl w-[80%]">
        <div className="perfil-info flex flex-col items-center justify-center">
          <h1 className="boas-vindas font-bold text-2xl text-center text-gray-800 mb-4">
            Bem vindo, {nome}
          </h1>
          <Image
            width={150}
            height={150}
            radius="full"
            src={foto}
            alt="Foto de perfil"
            className="mb-4"
          />

          <label htmlFor="nome" className="perfil-label text-gray-800 font-bold mt-4">Nome</label>
          <input
            type="text"
            name="nome"
            id="nome"
            className={`perfil-nome w-full p-2 bg-gray-200 text-gray-800 font-bold rounded-md mt-2 ${
              desabilitado ? "cursor-not-allowed" : ""
            }`}
            value={nome}
            onChange={(e) => setNome(e.target.value)}
            disabled={desabilitado}
          />
          <label htmlFor="email" className="perfil-label text-gray-800 font-bold mt-4">Email</label>
          <input
            type="text"
            name="email"
            id="email"
            className={`perfil-email w-full p-2 bg-gray-200 text-gray-800 font-bold rounded-md mt-2 ${
              desabilitado ? "cursor-not-allowed" : ""
            }`}
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            disabled={desabilitado}
          />
          <label htmlFor="cpf" className="perfil-label text-gray-800 font-bold mt-4">CPF</label>
          <input
            type="text"
            name="cpf"
            id="cpf"
            className={`perfil-cpf w-full p-2 bg-gray-200 text-gray-800 font-bold rounded-md mt-2 ${
              desabilitado ? "cursor-not-allowed" : ""
            }`}
            value={cpf}
            onChange={(e) => setCpf(e.target.value)}
            disabled={desabilitado}
          />

          <button
            className="perfil-editar bg-green-500 text-white p-2 rounded-md mt-4"
            onClick={() => setDesabilitado(!desabilitado)}
          >
            {desabilitado ? "Editar" : "Salvar"}
          </button>
        </div>
      </div>

      <Footer />
    </>
  );
};

export default PrincipalPerfil;

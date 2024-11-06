import Header from "../Header";
import Footer from "../Footer";
import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Cadastro = ({setUsuariologado}) => {
  const [erro, setErro] = useState("");
  const navigate = useNavigate();

  const cadastrar = async () => {
    const nome = document.querySelector("#nome").value;
    const email = document.querySelector("#email").value;
    const senha = document.querySelector("#senha").value;
    const cpf = document.querySelector("#cpf").value;

    if (!nome || !email || !senha || !cpf) {
      setErro("Preencha todos os campos!");
      return;
    }

    try {
      const respostaApi = await axios.post("http://localhost:8080/auth/cadastrar", {
        nome,
        email,
        senha,
        cpf,
      },{
        validateStatus: function (status) {
            return status <= 500;
        }
      });

      if(respostaApi.status === 200){
        const { token } = respostaApi.data;
        localStorage.setItem("token", token);
        setUsuariologado(true);
        navigate("/");
      }else{
        return setErro(respostaApi.data.description);
      }
      
    } catch (error) {
      setErro("Ocorreu um erro, tente novamente " + error);
    }
  };


  return (
    <>
      <Header />

      <div className="container dark:bg-[#1F2937] absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 flex flex-col h-[60%] w-[80%] justify-center bg-gray-200 p-4 drop-shadow-xl">
        <div className="titulo text-center mt-4 text-xl font-bold p-4">
          <h1>Cadastre-se e aproveite as melhores chupetas</h1>
        </div>

        <div className="container_inputs flex flex-col justify-center flex-grow drop-shadow-xl">
          <input
            type="text"
            placeholder="Nome"
            id="nome"
            className="mb-2 h-12 p-4 rounded border border-gray-300 dark:border-gray-700 dark:bg-gray-700 dark:text-white placeholder-gray-500 dark:placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-primary-500"
          />
          <input
            type="text"
            placeholder="E-mail"
            id="email"
            className="mb-2 h-12 p-4 rounded border border-gray-300 dark:border-gray-700 dark:bg-gray-700 dark:text-white placeholder-gray-500 dark:placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-primary-500"
          />
          <input
            type="password"
            placeholder="Senha"
            id="senha"
            className="mb-2 h-12 p-4 rounded border border-gray-300 dark:border-gray-700 dark:bg-gray-700 dark:text-white placeholder-gray-500 dark:placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-primary-500"
          />
          <input
            type="text"
            placeholder="Cpf"
            id="cpf"
            className="mb-2 h-12 p-4 rounded border border-gray-300 dark:border-gray-700 dark:bg-gray-700 dark:text-white placeholder-gray-500 dark:placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-primary-500"
          />
          <button className="mt-4 bg-secondary-500 text-white py-3 px-4 rounded hover:bg-secondary-600 transition-colors duration-300" onClick={cadastrar}>
            Cadastrar
          </button>
        </div>
        <p className="erro text-red-500 text-center mt-4 text-lg">{erro}</p>
      </div>
    </>
  );
};

export default Cadastro;

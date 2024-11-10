import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import DetalheProduto from './components/ProdutoDetalhes';
import Principal from './components/Main';
import LoginPage from './components/Login';
import Cadastro from './components/Cadastro';
import Carrinho from './components/Carrinho';
import PrincipalPerfil from './components/Perfil/Principal';
import Enderecos from './components/Perfil/Endereco';

import axios from 'axios';
import { useState,useEffect } from 'react';
import { jwtDecode } from 'jwt-decode';


function App() {
  
  const [produtos, setProdutos] = useState([]);
  const [usuarioLogado, setUsuarioLogado] = useState(false);
  const [paginas, setPaginas] = useState(0);
  const [paginaAtual, setPaginaAtual] = useState(0);
  const [isSessionExpired, setIsSessionExpired] = useState(false);



  useEffect(() => {
    if(localStorage.getItem("token")){
      const token = localStorage.getItem("token");
      const usuario = jwtDecode(token);
      setUsuarioLogado(true);
    }
  }, []);


  const validaJwt = (jwt) => {
    console.log("validando jwt"); 
    const token = jwtDecode(jwt);
    const dataExpiracao = token.exp * 1000; // Converter para milissegundos
    const dataAtual = new Date().getTime();
    if(dataAtual > dataExpiracao){
      console.log(`JWT EXPIRADO COM UMA DIFERENÇA DE ${dataAtual - dataExpiracao}`);
      setIsSessionExpired(true);
      setUsuarioLogado(false);
      localStorage.removeItem('token');
      localStorage.removeItem('usuario');
      localStorage.removeItem('carrinho');
      return false;
    }else{
      console.log(`JWT VÁLIDO COM UMA DIFERENÇA DE ${dataExpiracao - dataAtual}`);
      setIsSessionExpired(false);
      return true;
    }
};

  useEffect(() => {
    const intervalId = setInterval(() => {
      let JwtValido = validaJwt(localStorage.getItem('token'));

      if (!JwtValido) {
        setUsuarioLogado(false);
        setIsSessionExpired(true);
      }else if(!localStorage.getItem('token')){
        setUsuarioLogado(false);
      }
    }, 10000);

    return () => clearInterval(intervalId);
  }, []);
    



  const capturaProdutos = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/produtos/listar?limit=10?page=${paginaAtual}`);
      setProdutos(response.data.content);
      setPaginas(response.data.totalPages);
    } catch (error) {
      console.error(error);
      setProdutos([]);
    }
  }

  useEffect(() => {
    capturaProdutos();
  }, []);

  const elementoSessaoExpirada = () => {
    return (

      <>
        <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-red-500 text-white font-bold p-6 z-50
        ">

          <div className="tituloErro text-center text-2xl m-4">
            <h1>Sessão expirada!</h1>
          </div>

          <div className="mensagemErro mb-8">
            <p className="loginNovamente">Olá usuario sua sessão expirou, por favor faça login novamente</p>
          </div>

          <div className="botoes flex flex-row justify-center">
            <button className="bg-green-800 text-white p-2 rounded-md" onClick={() => setIsSessionExpired(false)}>Fechar</button>

          </div>

        </div>
      </>


    )

  }
  return (
    <Router>
      {isSessionExpired && elementoSessaoExpirada()}
      <Routes>
        <Route path="/" element={<Principal produtos={produtos} usuarioLogado={usuarioLogado} setUsuariologado={setUsuarioLogado} setProdutos={setProdutos} setPaginaAtual={setPaginaAtual} paginaAtual={paginaAtual} paginasTotais={paginas}/>} />
        <Route path="/produto-detalhes" element={<DetalheProduto usuarioLogado={usuarioLogado} setUsuariologado={setUsuarioLogado} />} />
        <Route path="/login" element={<LoginPage usuarioLogado={usuarioLogado} setUsuariologado={setUsuarioLogado} />} />
        <Route path="/cadastro" element={<Cadastro setUsuariologado={setUsuarioLogado} />} />
        <Route path="/carrinho" element={<Carrinho usuarioLogado={usuarioLogado} setUsuariologado={setUsuarioLogado} />} />
        <Route path="/perfil" element={<PrincipalPerfil usuarioLogado={usuarioLogado} setUsuariologado={setUsuarioLogado} />} />
        <Route path="/enderecos" element={<Enderecos usuarioLogado={usuarioLogado} setUsuariologado={setUsuarioLogado} />} />
      </Routes>
    </Router>
  );
}

export default App;

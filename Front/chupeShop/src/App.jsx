import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import DetalheProduto from './components/ProdutoDetalhes';
import Principal from './components/Main';
import LoginPage from './components/Login';
import axios from 'axios';
import { useState,useEffect } from 'react';


function App() {
  
  const [produtos, setProdutos] = useState([]);
  const [usuarioLogado, setUsuarioLogado] = useState(false);
  const [paginas, setPaginas] = useState(0);
  const [paginaAtual, setPaginaAtual] = useState(0);



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

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Principal produtos={produtos} usuarioLogado={usuarioLogado} setUsuariologado={setUsuarioLogado} setProdutos={setProdutos} setPaginaAtual={setPaginaAtual} paginaAtual={paginaAtual} paginasTotais={paginas}/>} />
        <Route path="/produto-detalhes" element={<DetalheProduto usuarioLogado={usuarioLogado} setUsuariologado={setUsuarioLogado} />} />
        <Route path="/login" element={<LoginPage usuarioLogado={usuarioLogado} setUsuariologado={setUsuarioLogado} />} />
      </Routes>
    </Router>
  );
}

export default App;

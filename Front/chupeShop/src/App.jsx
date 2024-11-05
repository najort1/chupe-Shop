import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import DetalheProduto from './components/ProdutoDetalhes';
import Principal from './components/Main';
import LoginPage from './components/Login';
import axios from 'axios';
import { useState,useEffect } from 'react';


function App() {
  
  const [produtos, setProdutos] = useState([]);


  const capturaProdutos = async () => {
    try {
      const response = await axios.get("https://fakestoreapi.com/products");
      setProdutos(response.data);
    } catch (error) {
      console.error(error);
    }
  }

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Principal />} />
        <Route path="/produto-detalhes" element={<DetalheProduto />} />
        <Route path="/login" element={<LoginPage />} />
      </Routes>
    </Router>
  );
}

export default App;

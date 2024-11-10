import Header from "../../Header";
import Footer from "../../Footer";
import axios from "axios";
import { useState, useEffect } from "react";

const Enderecos = ({usuarioLogado, setUsuariologado}) => {

    const [enderecos, setEnderecos] = useState([]);
    const [erro, setErro] = useState("");
    


  return (
    <>

    <Header setUsuarioLogado={setUsuariologado} usuarioLogado={usuarioLogado} />

    <Footer />
    </>
  );
};

export default Enderecos;

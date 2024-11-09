import * as carrinho from "../../hooks/carrinho.js";
import Header from "../Header/index.jsx";
import Footer from "../Footer/index.jsx";
import {Dropdown, DropdownTrigger, DropdownMenu, DropdownItem, Button} from "@nextui-org/react";
import boxicons from "boxicons";
import axios from "axios";

import React, { useState, useEffect } from 'react';



const Carrinho = ({usuarioLogado, setUsuariologado}) => {
    const itensCarrinho = carrinho.obterCarrinho();
    const [products, setProducts] = useState(itensCarrinho);
    console.log(products)


    const handleDeletePedido = async (id) => {
        
        const respostaApi = await axios.delete(`http://localhost:8080/pedido/deletar-pedido/${id}`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
            validateStatus: function (status) {
                return status <= 500;
            }
        });

        if (respostaApi.status === 204) {
            const novoCarrinho = products.filter((product) => product.id !== id);
            carrinho.removerDoCarrinho(id);
            return setProducts(novoCarrinho);
        }else{
            console.log(respostaApi.data.detail);
        }




    }
    
    return (
        <>
            <Header UserLoggedIn={usuarioLogado} setUsuarioLogado={setUsuariologado} />

            <div className="carrinho">
                <table className="carrinho-itens w-full text-left border-collapse">
                    <thead>
                        <tr className="bg-gray-200 dark:bg-gray-800">
                            <th className="p-2 border-b">Produto</th>
                            <th className="p-2 border-b">Quantidade</th>
                            <th className="p-2 border-b">Preço</th>
                            <th className="p-2 border-b">Subtotal</th>
                            <th className="p-2 border-b">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        {products.map((product) => (
                            <tr key={product.id} className="hover:bg-gray-100 dark:hover:bg-gray-800">
                                <td className="p-2 border-b flex items-center">
                                    <img src={product.imagem} alt={product.nome} className="w-16 h-16 object-cover mr-4 rounded-2xl" />
                                    <span>{product.nome}</span>
                                </td>
                                <td className="p-2 border-b">
                                    <input type="text" disabled value={product.quantidade} className="w-16 p-1 border rounded" />
                                </td>
                                <td className="p-2 border-b">
                                    {product.preco}
                                </td>
                                <td className="p-2 border-b">
                                    {product.preco * product.quantidade}
                                </td>
                                <td className="p-2 border-b">
                                    <Dropdown>
                                        <DropdownTrigger>
                                            <Button variant="bordered" >
                                            Ações
                                            </Button>
                                        </DropdownTrigger>
                                        <DropdownMenu aria-label="Static Actions">
                                            <DropdownItem key="finish">Finalizar</DropdownItem>
                                            <DropdownItem key="delete" className="text-danger" color="danger" onClick={() => handleDeletePedido(product.id)}>Remover</DropdownItem>
                                        </DropdownMenu>
                                    </Dropdown>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>

            <Footer />
        </>
    );
}

export default Carrinho;
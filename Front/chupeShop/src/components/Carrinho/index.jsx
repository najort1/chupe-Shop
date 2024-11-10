import * as carrinho from "../../hooks/carrinho.js";
import Header from "../Header/index.jsx";
import Footer from "../Footer/index.jsx";
import {Dropdown, DropdownTrigger, DropdownMenu, DropdownItem, Button} from "@nextui-org/react";
import boxicons from "boxicons";
import axios from "axios";
import { Image } from "@nextui-org/react";
import React, { useState, useEffect } from 'react';
import { TabletView, BrowserView, MobileView, isBrowser, isMobile, isTablet, isDesktop } from 'react-device-detect';


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

    const handleFinalizarPedido = async (id) => {  
        console.log(localStorage.getItem("token"))
        const respostaApi = await axios.get(`http://localhost:8080/pedido/finalizar-pedido/${id}`, {
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

            <div className="carrinho-container flex flex-col gap-10 mt-10 p-4 bg-gray-100 rounded-lg shadow-lg">
                {products.length === 0 && (
                    <h2 className="text-center font-bold text-3xl flex justify-center text-gray-700">Nenhum produto no carrinho</h2>
                )}
                
                {products.map((product) => (
                    <div key={product.id} className="carrinho-item flex flex-row gap-4 items-center justify-between p-4 bg-white rounded-lg shadow-md">
                        {isMobile && !isTablet && 
                            <MobileView>
                                <Image src={product.imagem} width="100" height="100" alt="Imagem do produto" className="rounded-lg" />
                            </MobileView>
                        }

                        {isTablet && isMobile &&
                                <Image src={product.imagem} width="150" height="150" alt="Imagem do produto" className="rounded-lg" />
                        }

                        <BrowserView>
                            <Image src={product.imagem} width="200" height="200" alt="Imagem do produto" className="rounded-lg" />
                        </BrowserView>

                        <div className="flex flex-col font-medium text-gray-800">
                            <h3 className="text-xl">{product.nome}</h3>
                            <p className="text-lg">R$ {product.preco}</p>
                            <p className="produto-total text-lg font-semibold">Total: R$ {product.total}</p>
                        </div>
                        <Dropdown>
                            <DropdownTrigger>
                                <Button variant="bordered" size="lg">
                                    Ações
                                </Button>
                            </DropdownTrigger>
                            <DropdownMenu aria-label="Static Actions">
                                <DropdownItem key="finish" className="text-success" color="success" onClick={() => handleFinalizarPedido(product.id)}>Finalizar</DropdownItem>
                                <DropdownItem key="delete" className="text-danger" color="danger" onClick={() => handleDeletePedido(product.id)}>Remover</DropdownItem>
                            </DropdownMenu>
                        </Dropdown>
                    </div>
                ))}
            </div>

            <Footer />
        </>
    );
}

export default Carrinho;
import 'boxicons';
import { Navbar as ChupeShopNav, NavbarBrand, NavbarContent, NavbarItem, Link, DropdownItem, DropdownTrigger, Dropdown, DropdownMenu, Avatar, Button } from "@nextui-org/react";
import { useState } from "react";
import ThemeSwitcher from '../ThemeSwitcher/ThemeSwitcher';
import useDarkMode from "../../hooks/useDarkMode.js";
import { useNavigate } from 'react-router-dom';
import { useEffect } from 'react';

const Header = ({ UserLoggedIn, setUsuarioLogado}) => {
  const [fotoUsuario, setFotoUsuario] = useState("https://i.pravatar.cc/150?u=a042581f4e29026704d");
  const [nomeUsuario, setNomeUsuario] = useState("Jason Hughes");
  const [emailUsuario, setEmailUsuario] = useState("adm@gmail.com");
  const isDarkMode = useDarkMode();

  const navigate = useNavigate();

  const navegarCadastro = () => {navigate('/cadastro');}
  const navegarLogin = () => { navigate('/login') }


  const capturarFotoENome = () => {
    if(!localStorage.getItem("user")) return;
    
    const user = JSON.parse(localStorage.getItem("user"));
    setFotoUsuario(user.picture);
    setNomeUsuario(user.name);
    setEmailUsuario(user.email);
  }

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    setUsuarioLogado(false);
    navigate('/');
  }


  useEffect(() => {
    capturarFotoENome();
  }
  , []);

  return UserLoggedIn ? (
    <ChupeShopNav shouldHideOnScroll={true}>
      <NavbarBrand>
        <ThemeSwitcher />
        <p className="font-bold text-inherit text-3xl">ChupeShop</p>
      </NavbarBrand>

      <NavbarContent as="div" justify="end">
        <Dropdown placement="bottom-end">
          <DropdownTrigger>
            <Avatar
              isBordered
              as="button"
              className="transition-transform"
              color="secondary"
              name={nomeUsuario}
              size="sm"
              src={fotoUsuario}
            />
          </DropdownTrigger>
          <DropdownMenu aria-label="Profile Actions" variant="flat">
            <DropdownItem key="profile" className="h-14 gap-2">
              <p className="font-semibold">Logado como</p>
              <p className="font-semibold">{nomeUsuario}</p>
            </DropdownItem>
            <DropdownItem key="logout" color="danger" onClick={handleLogout}>
              Log Out
            </DropdownItem>
          </DropdownMenu>
        </Dropdown>
      </NavbarContent>
    </ChupeShopNav>
  ) : (
    <ChupeShopNav isBordered>
      <NavbarBrand>
        <ThemeSwitcher />
        <p className="font-bold text-inherit text-3xl">ChupeShop</p>
      </NavbarBrand>
      <NavbarContent className="hidden sm:flex gap-4" justify="center">
        <NavbarItem isActive>
          <Link href="#" aria-current="page">
            Sobre
          </Link>
        </NavbarItem>
      </NavbarContent>
      <NavbarContent justify="end">
        <Dropdown>
          <DropdownTrigger>
            {isDarkMode ? (<box-icon name='user-circle' color='#ffffff'></box-icon>) : (
                <box-icon name='user-circle'></box-icon>)}
          </DropdownTrigger>
          <DropdownMenu variant="faded" aria-label="Dropdown menu with icons">
            <DropdownItem startContent={
              isDarkMode ? (<box-icon name='log-in-circle' color='#ffffff'></box-icon>) : (
                <box-icon name='log-in-circle'></box-icon>)} key="login" onClick={() => { }
            }>
              <Link
              onClick={navegarLogin}
              >
                Entrar
              </Link>
            </DropdownItem>
            <DropdownItem startContent={
              !isDarkMode ? (<box-icon name='user-plus' ></box-icon>) : (<box-icon color='#ffffff'name='user-plus' ></box-icon>)
            }>
              <Link
              onClick={navegarCadastro}
              >
                Cadastrar
              </Link>
            </DropdownItem>
          </DropdownMenu>
        </Dropdown>
      </NavbarContent>
    </ChupeShopNav>
  );
};

export default Header;
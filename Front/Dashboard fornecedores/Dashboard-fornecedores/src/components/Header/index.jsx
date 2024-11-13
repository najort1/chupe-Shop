import SaideBar from "../SideBar";

const Header = () => {
  return (
    <>
    <header className="header-dash w-dvh h-10 bg-blue-400 flex">
        <div className="container mx-auto flex flex-row items-center py-4">
    <SaideBar />
    <div className="logo">
            <a href="/" className="text-white text-2xl font-bold">
              ChupeChop
            </a>
          </div>
        </div>
      </header>
    </>
  );
};
export default Header;

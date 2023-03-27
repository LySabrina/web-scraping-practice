import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import './App.css';
import Navbar from "./layout/Navbar";
import Form from "./layout/Form";
import Home from "./layout/Home";
function App() {
  return (
    <div>
        <Navbar/>
        <Home/>
    </div>
  );
}

export default App;

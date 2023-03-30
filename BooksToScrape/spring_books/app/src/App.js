import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import './App.css';
import Navbar from "./layout/Navbar";
import Form from "./layout/Form";
import Home from "./layout/Home";
import Search from "./layout/Search";
import AddBook from "./layout/AddBook";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import ViewBook from "./layout/ViewBook";

function App() {
  return (
    <div>
       <Router>
        <Navbar/>
    
        <Routes>
          <Route exact path ="/" element = {<Home/>}/>
          <Route exact path = "/addBook" element = {<AddBook/>}/>
            <Route exact path = "/viewBook/:id" element={<ViewBook/>}/>
        </Routes>

       </Router>
       
    </div>
  );
}

export default App;

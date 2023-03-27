import React, { useEffect, useState } from "react";
import axios from "axios"; //used to get information?
import "../css/Home.css";
import Aside from "./Aside";

export default function Home() {
  const [book, setBooks] = useState([]);
  

  useEffect(() =>{
      loadBooks();
  }, []);


  const loadBooks = async() =>{
      const result = await axios.get("http://localhost:8080/book");
      setBooks(result.data);
  };

  

  return (
    <div className="container">
        <Aside/>
    <div className="py-4">
      <table className="table border shadow">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">Book Name</th>
            <th scope="col">Book URL</th>
            <th scope="col">PRICE</th>
          </tr> 
        </thead>
        <tbody>
          {
              book.map((book,index)=>(
                  <tr>
                  <th scope="row" key={index}>{index+1}</th>
                  <td>{book.name}</td>
                  <td>{book.url}</td>
                  <td>${book.price}</td>
                </tr>
              ))
          }
        </tbody>
      </table>
    </div>
  </div>
  
  );
}

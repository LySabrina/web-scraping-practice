import React, { useEffect, useState } from "react";
import axios from "axios"; //used to get information?
import "../css/Home.css";
import Aside from "./Aside";
import Search from "./Search";
import { Link } from 'react-router-dom';
export default function Home() {
  const [book, setBooks] = useState([]);

  useEffect(() => {
    loadBooks();
  }, []);

  const loadBooks = async () => {
    const result = await axios.get("http://localhost:8080/book");
    setBooks(result.data);
  };

  return (
    <div>
      <Search/>
      <div className="container-home">
        <Aside />
        <div className="py-4">
          <table className="table border shadow">
            <thead>
              <tr>
                <th scope="col">#</th>
                <th scope="col">Book Name</th>
                <th scope="col">Book Genre</th>
                <th scope="col">PRICE</th>
              </tr>
            </thead>
            <tbody>
              {book.map((book, index) => (
                <tr>
                  <th scope="row" key={index}>
                    {index + 1}
                  </th>
                  <td>
                    <Link to={`/viewBook/${book.id}`}> {book.name}</Link>
                    </td>
                  <td>{book.genre}</td>
                  <td>${book.price}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

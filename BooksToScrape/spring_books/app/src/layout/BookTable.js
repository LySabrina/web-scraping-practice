import {Link} from "react-router-dom";
import React from "react";

export default function BookTable(props){
    let book = props.book;
    return(
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
    );
}
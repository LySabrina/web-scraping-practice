import {Link} from "react-router-dom";
import React, { useEffect, useState } from "react";
import axios from "axios"; //used to get information?

export default function BookTable(props){
    // let book = props.book;
    let b = props.book;
    const [images, setImages] = useState([]);
    
    let book = b.map((a,i) => ({...a, imageSrc : images[i]}));
    // images do not match with the correct book title
    console.log(book);
  
  useEffect(() =>{
    loadImages();
  }, []);

  const loadImages = async () => {
    const result = await axios.get("http://localhost:8080/images");
    setImages(result.data);
  };
    
    return(
      <div className="py-4">
      <table className="table border shadow">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">Image</th>
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
              <img src={`data:image/jpeg;base64,${book.imageSrc}`}/>
              </td>
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
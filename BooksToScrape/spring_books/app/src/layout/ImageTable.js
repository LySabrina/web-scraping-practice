import React, { useEffect, useState } from "react";
import axios from "axios"; //used to get information?

export default function ImageTable() {
  const [images, setImages] = useState([]);

  useEffect(() =>{
    loadImages();
  }, []);

  const loadImages = async () => {
    const result = await axios.get("http://localhost:8080/images");
    setImages(result.data);
  };

  return <div>
    
    <table className="table border shadow">
        <thead>
          <tr>
            <th scope="col">IMAGES</th>
          </tr>
        </thead>
        <tbody>
          {
            images.map((image) =>(
              <tr>
                <td>
                  <img src={`data:image/jpeg;base64,${image}`}/>
                </td>
              </tr>
            ))
          }
        </tbody>
      </table>  
    </div>;
}

export default function Search(){
    const styleObj = {
        margin: "auto",
        marginTop : "2%",
        width:"50%",
        height:"20%"
    };

    const inputStyle = {
        margin: "auto",
        display: "block"
    }
    return(
      <div style={styleObj}>
          <input style={inputStyle} tupe="text" placeholder="Search Book"/>
      </div>
    );
}
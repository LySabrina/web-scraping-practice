import React from "react";

export default function Breadcrumb() {
  return (
    <div>
      <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
          <li class="breadcrumb-item">
            <a href="#">Home</a>
          </li>

          <li class="breadcrumb-item">
            <a href="#">Library</a>
          </li>

          <li class="breadcrumb-item active" aria-current="page">
            Data
          </li>
        </ol>
      </nav>
    </div>
  );
}

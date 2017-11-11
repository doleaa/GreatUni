import React, { Component } from 'react';
import './App.css';
import logoSkyscanner from './img/skyscannerlogo.png';


class App extends Component {
  render() {
    return (


      <div className="container">
        <div className="row">

        <div className="col-md-10">
        <h1 className="blending">TravelMate</h1>
        </div>
        <div className="col-md-2">
        <h2 className="blending">About  Us</h2>
        </div>

        </div>

        <div className="row">
          <div className="col-md-6 box left grow" ></div>
          <div className="col-md-6 box right grow "></div>
        </div>


        <div className="row">
          <div className="col-md-12">
          <h3 className="blending"> <img className="logoSkyscanner" src={logoSkyscanner} alt="Powered by Skyscanner"/></h3>
          </div>
        </div>



      </div>


















    );
  }
}

export default App;

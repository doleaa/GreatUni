import React, { Component } from 'react'
import Header from './../header/Header'
import MainPage from './../mainPage/MainPage'
import Footer from './../footer/Footer'
import './App.css';


class App extends Component {
  render() {
    return (
      <div className="container">

        <Header/>

        <MainPage/>

        <Footer/>

      </div>
    );
  }
}

export default App;

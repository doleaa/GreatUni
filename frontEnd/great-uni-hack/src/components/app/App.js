import React from 'react'
import Header from './../header/Header'
import MainPage from './../mainPage/MainPage'
import SearchGroup from './../search/SearchGroup'
import SearchSingle from './../search/SearchSingle'
import FlightDisplay from './../flightDisplay/FlightDisplay'
import Footer from './../footer/Footer'
import LocationChoice from './../locationchoice/LocationChoice'
import './App.css'



const App = () => (
      <div className="container">

        <Header/>

        <MainPage/>

        <Footer/>

      </div>
    )

export default App

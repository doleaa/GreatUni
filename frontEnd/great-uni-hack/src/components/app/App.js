import React from 'react'
import Header from './../header/Header'
import MainPage from './../mainPage/MainPage'
import SearchGroup from './../search/SearchGroup'
import SearchSingle from './../search/SearchSingle'
import FlightDisplay from './../flightDisplay/FlightDisplay'
import Footer from './../footer/Footer'
import LocationChoice from './../locationchoice/LocationChoice'
import './App.css'
import { connect } from 'react-redux'

const mapStateToProps = state => {
    return {
        searchType: state.searchType
    }
}

const DisconnectedApp = ({ searchType }) => {
    if (searchType === "") {
        return (
            <div className="container">

                <Header/>

                <MainPage/>

                <Footer/>

            </div>
        )
    }

    if (searchType === "single") {
        return (
            <div className="container">

                <Header/>

                <SearchSingle/>

                <Footer/>

            </div>
        )
    }

    if (searchType === "group") {
        return (
            <div className="container">

                <Header/>

                <LocationChoice/>

                <Footer/>

            </div>
        )
    }

}

const App = connect(mapStateToProps)(DisconnectedApp)

export default App

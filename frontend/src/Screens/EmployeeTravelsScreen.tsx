import React, { Component } from 'react';
//import './App.scss';
import { HashRouter as Router, Route, Link, Switch } from 'react-router-dom'
import EmployeePendingTravelTable from './../Tables/EmployeePendingTravelTable'
import EmployeeAcceptedTravelTable from './../Tables/EmployeeAcceptedTravelTable'
import EmployeeAppBar from '../AppBars/EmployeeAppBar'

class EmployeeTravelsScreen extends Component {
  render() {
    return (
        <div className="EmployeeTravelsScreen">
        <EmployeeAppBar></EmployeeAppBar>
        <EmployeePendingTravelTable></EmployeePendingTravelTable>
        <EmployeeAcceptedTravelTable></EmployeeAcceptedTravelTable>
        </div>
    );
  }
}

export default EmployeeTravelsScreen;

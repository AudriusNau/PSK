import React, { Component } from 'react';
//import './App.scss';
import { HashRouter as Router, Route, Link, Switch } from 'react-router-dom'
import EmployeePendingTravelTable from './../Tables/EmployeePendingTravelTable'
import EmployeeAcceptedTravelTable from './../Tables/EmployeeAcceptedTravelTable'

class EmployeeTravelsScreen extends Component {
  render() {
    return (
        <div className="EmployeeTravelsScreen">
        <EmployeePendingTravelTable></EmployeePendingTravelTable>
        <EmployeeAcceptedTravelTable></EmployeeAcceptedTravelTable>
        </div>
    );
  }
}

export default EmployeeTravelsScreen;

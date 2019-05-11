import React, { Component } from 'react';
import './App.scss';
import { HashRouter as Router, Route, Link, Switch } from 'react-router-dom'
import Login from './Login/Login';
import Table from './Tables/Table';
import EmployeeTravelsScreen from './Screens/EmployeeTravelsScreen'
import EmployeePendingTravelTable from './Tables/EmployeePendingTravelTable'
import EmployeeAcceptedTravelTable from './Tables/EmployeeAcceptedTravelTable'
import EmployeeProfileScreen from './Screens/EmployeeProfileScreen'
import Apartments from './Admin/Apartments';

class App extends Component {
  render() {
    return (
      /*<div className="App">
        <Login></Login>
        <Table></Table>
      </div>*/
      <Router>
      <div>
        <nav>
          <Link to="/">Login </Link><br/>
          <Link to="/table">Table </Link><br/>
          <Link to="/employeePendingTravelTable">EmployeePendingTravelTable </Link><br/>
          <Link to="/employeeAcceptedTravelTable">EmployeeAcceptedTravelTable </Link><br/>
          <Link to="/employeeTravelsScreen">EmployeeTravelsScreen </Link><br/>
          <Link to="/employeeProfileScreen">EmployeeProfileScreen </Link><br/>
          <Link to="/admin">Admin </Link><br/>
        </nav>
        <Switch>
          <Route exact path="/" component={Login} />
          <Route exact path="/table" component={Table} />
          <Route exact path="/employeePendingTravelTable" component={EmployeePendingTravelTable} />
          <Route exact path="/employeeAcceptedTravelTable" component={EmployeeAcceptedTravelTable} />
          <Route exact path="/employeeTravelsScreen" component={EmployeeTravelsScreen} />
          <Route exact path="/employeeProfileScreen" component={EmployeeProfileScreen} />
          <Route exact path="/admin" component={Apartments} />
        </Switch>
      </div>
    </Router>
    );
  }
}

export default App;

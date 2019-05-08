import React, { Component } from 'react';
import './App.scss';
import { HashRouter as Router, Route, Link, Switch } from 'react-router-dom'
import Login from './Login/Login';
import Table from './Tables/Table';
import EmployeeTravelsScreen from './Screens/EmployeeTravelsScreen'
import EmployeePendingTravelTable from './Tables/EmployeePendingTravelTable'
import EmployeeAcceptedTravelTable from './Tables/EmployeeAcceptedTravelTable'

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
          <Link to="/">Login </Link>
          <Link to="/table">Table </Link>
          <Link to="/employeePendingTravelTable">EmployeePendingTravelTable </Link>
          <Link to="/employeeAcceptedTravelTable">EmployeeAcceptedTravelTable </Link>
          <Link to="/employeeTravelsScreen">EmployeeTravelsScreen </Link>
        </nav>
        <Switch>
          <Route exact path="/" component={Login} />
          <Route exact path="/table" component={Table} />
          <Route exact path="/employeePendingTravelTable" component={EmployeePendingTravelTable} />
          <Route exact path="/employeeAcceptedTravelTable" component={EmployeeAcceptedTravelTable} />
          <Route exact path="/employeeTravelsScreen" component={EmployeeTravelsScreen} />
        </Switch>
      </div>
    </Router>
    );
  }
}

export default App;

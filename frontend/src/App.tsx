import React, { Component } from 'react';
import './App.scss';
import { HashRouter as Router, Route, Link, Switch } from 'react-router-dom'
import Login from './Login/Login';
import Table from './Tables/Table';
import EmployeeTravelsScreen from './Screens/EmployeeTravelsScreen'
import EmployeePendingTravelTable from './Tables/EmployeePendingTravelTable'
import EmployeeAcceptedTravelTable from './Tables/EmployeeAcceptedTravelTable'
import EmployeeProfileScreen from './Screens/EmployeeProfileScreen'
import OrganiserTravelsScreen from './Screens/OrganiserTravelsScreen'
import CreateTravel from './Forms/CreateTravel'
import MyComponent from "./MyComponent";

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
          <Link to="/employeeProfileScreen">EmployeeProfileScreen </Link>
          <Link to="/organiserTravelsScreen">OrganiserTravelsScreen </Link>
          <Link to="/createTravel">CreateTravel </Link>
          <Link to="/test">test </Link>
        </nav>
        <Switch>
          <Route exact path="/" component={Login} />
          <Route exact path="/table" component={Table} />
          <Route exact path="/employeePendingTravelTable" component={EmployeePendingTravelTable} />
          <Route exact path="/employeeAcceptedTravelTable" component={EmployeeAcceptedTravelTable} />
          <Route exact path="/employeeTravelsScreen" component={EmployeeTravelsScreen} />
          <Route exact path="/employeeProfileScreen" component={EmployeeProfileScreen} />
          <Route exact path="/organiserTravelsScreen" component={OrganiserTravelsScreen} />
          <Route exact path="/createTravel" component={CreateTravel} />
          <Route exact path="/test" component={MyComponent} />
        </Switch>
      </div>
    </Router>
    );
  }
}

export default App;

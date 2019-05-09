import React, { Component } from 'react';
import EmployeeAppBar from '../AppBars/EmployeeAppBar';
import EmployeeCalendar from '../Calendars/EmployeeCalendar'

class EmployeeProfileScreen extends Component {
    render() {
      return (
          <div className="EmployeeTravelsScreen">
          <EmployeeAppBar></EmployeeAppBar>
          <EmployeeCalendar></EmployeeCalendar>
          </div>
      );
    }
  }

  export default EmployeeProfileScreen;
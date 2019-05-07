import React, { Component } from 'react';
import './App.scss';
import Login from './Login/Login';
import Table from './Table/Table';

class App extends Component {
  render() {
    return (
      <div className="App">
        <Login></Login>
        <Table></Table>
      </div>
    );
  }
}

export default App;

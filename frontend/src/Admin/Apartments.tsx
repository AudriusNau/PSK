import React, { Component } from 'react';
import ApartmentsTable from './ApartmentsTable';

class Apartments extends Component {
  render() {
    return (
        <div className="Apartments">
        <ApartmentsTable></ApartmentsTable>
        </div>
    );
  }
}

export default Apartments;

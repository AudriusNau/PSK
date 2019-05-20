import React, {ReactModal} from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TablePagination from '@material-ui/core/TablePagination';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import Modal from 'react-modal';
import Apartment from './Apartment';
import axios from 'axios';
import styles from '../Login/Login.module.scss';
import SelectInput from '@material-ui/core/Select/SelectInput';

// Modal.setAppElement('#app-base');

const rows = [
  { id: 'name', numeric: false, disablePadding: true, label: 'Name'},
  { id: 'location', numeric: false, disablePadding: false, label: 'Location' },
  { id: 'room_count', numeric: true, disablePadding: false, label: 'Room count' },
];

class ApartmentTableHead extends React.Component {
  createSortHandler = property => event => {
    this.props.onRequestSort(event, property);
  };

  render() {
    return (
      <TableHead>
        <TableRow>
          {rows.map(
            row => (
              <TableCell
                key={row.id}
                align={'center'}
                padding={row.disablePadding ? 'none' : 'default'}
              />
            ),
            this,
          )}
        </TableRow>
      </TableHead>
    );
  }
}

class ApartmentTable extends React.Component {
  state = {
    selected: {},
    data: [],
    page: 0,
    rowsPerPage: 5,
    showModal: false
  };

  constructor () {
    super();
    
    axios.get('https://jsonblob.com/api/jsonBlob/391e08b9-7a2c-11e9-9927-b3c5ae395141')
    .then(response => {
      console.log(response.data.apartments)
      this.setState({data: response.data.apartments})
    })

    this.handleOpenModal = this.handleOpenModal.bind(this);
    this.handleCloseModal = this.handleCloseModal.bind(this);
  }

  handleChangePage = (event, page) => {
    this.setState({ page });
  };

  handleChangeRowsPerPage = event => {
    this.setState({ rowsPerPage: event.target.value });
  };

  // isSelected = id => this.state.selected.indexOf(id) !== -1;
  
  handleOpenModal (ap) {
    this.setState({ showModal: true, selected: ap });
  }
  
  handleCloseModal () {
    this.setState({ showModal: false });
  }

  render() {
    const { classes } = this.props;
    const { data, rowsPerPage, page } = this.state;
    const emptyRows = rowsPerPage - Math.min(rowsPerPage, data.length - page * rowsPerPage);

    const rowList = data.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
      .map(datum => {
        return (
          <TableRow
            hover
            onClick={() => this.handleOpenModal(datum)}
            tabIndex={-1}
            key={datum.id}
          >
            <TableCell align="left">{datum.name}</TableCell>
            <TableCell align="left">{datum.location}</TableCell>
            <TableCell align="left">{datum.room_count}</TableCell>
          </TableRow>
        );
      })

    return (
      <Paper className={classes.root}>
        <Modal
          isOpen={this.state.showModal}
          contentLabel="Details"
        >
          <button onClick={this.handleCloseModal}>Close Modal</button>
          <Apartment apartment={this.state.selected}></Apartment>
        </Modal>
        {/* <ApartmentTableHead></ApartmentTableHead> */}
        <div className={classes.tableWrapper}>
          <Table className={classes.table} aria-labelledby="tableTitle">
            <ApartmentTableHead
              rowCount={data.length}
            />
            <TableBody>
              {rowList}
              {emptyRows > 0 && (
                <TableRow style={{ height: 49 * emptyRows }}>
                  <TableCell colSpan={6} />
                </TableRow>
              )}
            </TableBody>
          </Table>
        </div>
        <TablePagination
          rowsPerPageOptions={[5, 10, 25]}
          component="div"
          count={data.length}
          rowsPerPage={rowsPerPage}
          page={page}
          backIconButtonProps={{
            'aria-label': 'Previous Page',
          }}
          nextIconButtonProps={{
            'aria-label': 'Next Page',
          }}
          onChangePage={this.handleChangePage}
          onChangeRowsPerPage={this.handleChangeRowsPerPage}
        />
      </Paper>
    );
  }
}

ApartmentTable.propTypes = {
  classes: PropTypes.object.isRequired,
};


// const Apartment2 = (apartmentData) => 

export default withStyles()(ApartmentTable);
import React from 'react';
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

let counter = 0;
function createData(name, location, room_count) {
  counter += 1;
  return { id: counter, name, location, room_count };
}

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
    selected: [],
    data: [
      createData('Devbridge apartments 1', 'Adress line 1', 6),
      createData('Devbridge apartments 2', 'Adress line 2', 6),
    ],
    page: 0,
    rowsPerPage: 5,
    showModal: false
  };

  constructor () {
    super();
    
    this.handleOpenModal = this.handleOpenModal.bind(this);
    this.handleCloseModal = this.handleCloseModal.bind(this);
  }

  handleChangePage = (event, page) => {
    this.setState({ page });
  };

  handleChangeRowsPerPage = event => {
    this.setState({ rowsPerPage: event.target.value });
  };

  isSelected = id => this.state.selected.indexOf(id) !== -1;
  
  handleOpenModal () {
    this.setState({ showModal: true });
  }
  
  handleCloseModal () {
    this.setState({ showModal: false });
  }

  render() {
    const { classes } = this.props;
    const { data, rowsPerPage, page } = this.state;
    const emptyRows = rowsPerPage - Math.min(rowsPerPage, data.length - page * rowsPerPage);

    return (
      <Paper className={classes.root}>
        <Modal
          isOpen={this.state.showModal}
          contentLabel="Details"
        >
          <button onClick={this.handleCloseModal}>Close Modal</button>
        </Modal>
        <ApartmentTableHead></ApartmentTableHead>
        <div className={classes.tableWrapper}>
          <Table className={classes.table} aria-labelledby="tableTitle">
            <ApartmentTableHead
              rowCount={data.length}
            />
            <TableBody>
              {
                data.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map(n => {
                  const isSelected = this.isSelected(n.id);
                  return (
                    <TableRow
                      hover
                      onClick={this.handleOpenModal}
                      aria-checked={isSelected}
                      tabIndex={-1}
                      key={n.id}
                      selected={isSelected}
                    >
                      <TableCell align="left">{n.name}</TableCell>
                      <TableCell align="left">{n.location}</TableCell>
                      <TableCell align="left">{n.room_count}</TableCell>
                    </TableRow>
                  );
                })}
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

export default withStyles()(ApartmentTable);
import React from 'react';
import { withStyles } from '@material-ui/core/styles';
import axios from 'axios';

class Apartment extends React.Component {
    state = {
        apartment: this.props.apartment,
        name: this.props.apartment.name,
        location: this.props.apartment.location,
        room_count: this.props.apartment.room_count
    };

    constructor(props) {
        super(props)
        this.setName = this.setName.bind(this);
        this.setLocation = this.setLocation.bind(this);
        this.setRoomCount = this.setRoomCount.bind(this);
    }

    update = () => {
        console.log("Update not yet implemented :(");

        axios.get('https://jsonblob.com/api/jsonBlob/391e08b9-7a2c-11e9-9927-b3c5ae395141')
        .then(response => {
            var aps = []
            response.data.apartments.forEach(element => {
                if (element.id == this.state.apartment.id) {
                    aps.push({
                        id: this.state.apartment.id,
                        location: this.state.location,
                        name: this.state.name,
                        room_count: this.state.room_count
                    })
                } else {
                    aps.push(element)
                }
            });            
            console.log(aps)

            axios.put(
                "https://jsonblob.com/api/jsonBlob/391e08b9-7a2c-11e9-9927-b3c5ae395141", 
                {apartments: aps}, 
                {headers: {"Content-Type": "application/json"}}
            )
        })
    }

    setName(event) {
        this.setState({name: event.target.value});
    }
    setLocation(event) {
        this.setState({location: event.target.value});
    }
    setRoomCount(event) {
        this.setState({room_count: event.target.value});
    }

    render() {
        const { name, location, room_count } = this.state;
        return (
            <div>
                <form  onSubmit={this.update}>
                    <input value={name} onChange={this.setName}/><br/>
                    <input value={location} onChange={this.setLocation}/><br/>
                    <input value={room_count} onChange={this.setRoomCount}/><br/>
                    <button>UPDATE</button><br/>
                </form>
            </div>
        );
    }
}

// Apartment2 = React.createElement()

export default withStyles()(Apartment);
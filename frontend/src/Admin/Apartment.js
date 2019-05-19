import React from 'react';
import { withStyles } from '@material-ui/core/styles';

class Apartment extends React.Component {
    name;
    adress;
    roomCount;

    login = () => {
        console.log("Update not yet implemented :(");
    }

    render() {
        return (
            <div>
                <form  onSubmit={this.submit}>
                    <input type="Name"/>name<br/>
                    <input type="Address"/>adress<br/>
                    <input type="Room count"/>roomCount<br/>
                    <button type="submit">UPDATE</button><br/>
                </form>
            </div>
        );
    }
}
export default withStyles()(Apartment);
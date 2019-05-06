import * as React from 'react';
import { ILoginProps } from './ILoginProps';
import styles from './Login.module.scss';

class Login extends React.Component {
  login = () => {
    console.log("Login not yet implemented :(");
  }

  public render() {
    return (
      <div className={`${styles.login}`}>
        <form  onSubmit={this.login}>
          <input type="email"/><br/>
          <input type="password"/><br/>
          <button type="submit">LOGIN</button><br/>
        </form>
      </div>
    );
  }
};

export default Login;

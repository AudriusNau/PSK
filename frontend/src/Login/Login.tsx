import * as React from 'react';
import { ILoginProps } from './ILoginProps';
import styles from './Login.module.scss';

const Login: React.SFC<ILoginProps> = (props) => {
  return (
    <div className={`${styles.login}`}>
      <h3>Initial Login</h3>
    </div>
  );
};

export default Login;

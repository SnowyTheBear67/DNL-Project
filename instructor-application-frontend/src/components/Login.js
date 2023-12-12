import React from "react";

const Login = () => {
  return (
    <div>
      <header className="head">
        <img
          src="https://www.cognixia.com/wp-content/uploads/2021/06/JUMP-cognixia.webp"
          alt=""
          className="head-img"
        />
        <h1>Instructor's Dashboard</h1>
      </header>
      <div className="login-form">
        <div className="grid-sepration">
          <form action="" className="border">
            <div>
              <label className="login-label">Email</label> <br />
              <input type="text" name="" id="" className="login-input" />
            </div>
            <div>
              <label className="login-label">Password</label>
              <br />
              <input type="password" className="login-input" />
            </div>
            <div>
              <button className="btn btn-primary">Login</button>
            </div>
          </form>
        </div>
        <div className="right">
          {/* <div className="collabera">hello</div> */}
          <div className="collabera">
            <h4
              style={{
                color: "white",
                display: "flex",
                justifyContent: "center",
              }}
            >
              Add Account
            </h4>
            <div>
              <label className="sign-up-label">Name</label>
              <input type="text" className="sign-up-input" />
            </div>
            <div>
              <label className="sign-up-label">Name</label>
              <input type="text" className="sign-up-input" />
            </div>
            <div>
              <label className="sign-up-label">Email</label>
              <input type="text" className="sign-up-input" />
            </div>
            <div>
              <label className="sign-up-label">Password</label>
              <input type="text" className="sign-up-input" />
            </div>
            <button className="btn btn-primary">Sign Up</button>
          </div>
          {/* <div className="collabera">hello</div> */}
        </div>
      </div>
    </div>
  );
};

export default Login;

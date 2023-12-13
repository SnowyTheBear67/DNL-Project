import React from "react";
import Api from "../api/Api";

const Login = (props) => {
  const {
    user,
    setUser,
    userLogin,
    setUserLogin,
    token,
    setToken,
    handleLoginFunc,
  } = props;

  const handleChangeUsername = (event) => {
    setUser({
      ...user,
      username: event.target.value,

      // [event.target.password]: event.target.value,
    });
  };
  const handleChangeFirstname = (event) => {
    setUser({
      ...user,
      firstName: event.target.value,
    });
  };
  const handleChangeLastname = (event) => {
    setUser({
      ...user,
      lastName: event.target.value,
    });
  };
  const handleChangePassword = (event) => {
    setUser({
      ...user,
      password: event.target.value,
    });
  };
  const handleSubmit = (event) => {
    event.preventDefault();
    Api.addUser(user);
    setUser({
      id: null,
      enabled: true,
      firstName: "",
      lastName: "",
      username: "",
      password: "",
      role: "ROLE_USER",
    });

    console.log("submitted");
    console.log(user);
    // setToken();
  };

  const handleSubmitLogin = (event) => {
    event.preventDefault();
    Api.loginUser(userLogin, setToken);
    setUserLogin({
      username: "",
      password: "",
    });
    // console.log(jwt);
    // setToken(jwt);
  };
  // const handleSubmitLogin = (event) => {
  //   event.preventDefault();
  //   Api.loginUser(userLogin)
  //     .then((token) => {
  //       setUserLogin({
  //         username: "",
  //         password: "",
  //       });
  //       // setToken(token); // Set the token in your application state
  //       console.log(token); // Token received from the API response
  //     })
  //     .catch((error) => {
  //       console.log(error);
  //       // Handle the error as needed (e.g., show an error message to the user)
  //     });
  // };

  const handleChangeUsernameLogin = (event) => {
    setUserLogin({
      ...userLogin,
      username: event.target.value,
    });
  };
  const handleChangePasswordLogin = (event) => {
    setUserLogin({
      ...userLogin,
      password: event.target.value,
    });
  };
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
          <form action="" className="border" onSubmit={handleSubmitLogin}>
            <div>
              <label className="login-label">Email</label> <br />
              <input
                type="text"
                name=""
                id=""
                className="login-input"
                value={userLogin.username}
                onChange={handleChangeUsernameLogin}
              />
            </div>
            <div>
              <label className="login-label">Password</label>
              <br />
              <input
                type="password"
                className="login-input"
                value={userLogin.password}
                onChange={handleChangePasswordLogin}
              />
            </div>
            <div>
              <button className="btn btn-primary" onClick={handleLoginFunc}>
                Login
              </button>
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
            <form onSubmit={handleSubmit}>
              <div>
                <label className="sign-up-label">First Name</label>
                <input
                  type="text"
                  className="sign-up-input"
                  value={user.firstName}
                  onChange={handleChangeFirstname}
                />
              </div>
              <div>
                <label className="sign-up-label">Last Name</label>
                <input
                  type="text"
                  className="sign-up-input"
                  value={user.lastName}
                  onChange={handleChangeLastname}
                />
              </div>
              <div>
                <label className="sign-up-label">Username</label>
                <input
                  type="text"
                  className="sign-up-input"
                  value={user.username}
                  onChange={handleChangeUsername}
                />
              </div>

              <div>
                <label className="sign-up-label">Password</label>
                <input
                  type="password"
                  className="sign-up-input"
                  value={user.password}
                  onChange={handleChangePassword}
                />
              </div>
              <button className="btn btn-primary">Sign Up</button>
            </form>
          </div>
          {/* <div className="collabera">hello</div> */}
        </div>
      </div>
    </div>
  );
};

export default Login;

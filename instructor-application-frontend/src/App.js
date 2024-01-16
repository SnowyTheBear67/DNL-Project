import { useState } from "react";
import "./App.css";
import Login from "./components/Login.js";
import Students from "./components/Students.js";
import Api from "./api/Api.js";
function App() {
  const [user, setUser] = useState({
    id: null,
    enabled: true,
    firstName: "",
    lastName: "",
    username: "",
    password: "",
    role: "ROLE_USER",
  });
  const [userLogin, setUserLogin] = useState({
    username: "",
    password: "",
  });
  const [isLoggedIn, setisLoggedIn] = useState(true);
  const [student, setStudent] = useState([]);
  const fetchAllStudents = (setStudent, username) => {
    debugger;
    Api.findStudentsInstructorUsername(setStudent, username);
    // .then((data) => {
    //   // setStudent(data);
    //   // console.log(student);
    // })
    // .catch((error) => {
    //   console.log(error);
    // });
  };
  // const handleLoginFunc = () => {
  //   debugger;
  //   if (userLogin.username && userLogin.password) {
  //     // fetchAllStudents();
  //     // console.log(student);
  //     Api.loginUser(userLogin);
  //     // setisLoggedIn(false);
  //   }
  // };

  return (
    <div>
      {isLoggedIn ? (
        <Login
          user={user}
          setUser={setUser}
          userLogin={userLogin}
          setUserLogin={setUserLogin}
          // handleLoginFunc={handleLoginFunc}
          setisLoggedIn={setisLoggedIn}
          fetchAllStudents={fetchAllStudents}
          setStudent={setStudent}
        />
      ) : (
        <Students
          student={student}
          setStudent={setStudent}
          setisLoggedIn={setisLoggedIn}
        />
      )}
    </div>
  );
}

export default App;

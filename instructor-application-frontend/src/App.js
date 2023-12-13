import { useEffect, useState } from "react";
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
  // const [token, setToken] = useState("");
  // const [student, setStudent] = useState([
  //   {
  //     id: 1,
  //     firstName: "Bryan De",
  //     lastName: " Los Santos",
  //     email: "bryan.santos@gmail.com",
  //     grade: 90,
  //     jumpClass: "Oct",
  //     notes: null,
  //   },
  //   {
  //     id: 2,
  //     firstName: "David",
  //     lastName: " Burch",
  //     email: "david.burch@gmail.com",
  //     grade: 91,
  //     jumpClass: "Oct",
  //     notes: null,
  //   },
  //   {
  //     id: 3,
  //     firstName: "Jake",
  //     lastName: " Earle",
  //     email: "jake.earl@gmail.com",
  //     grade: 92,
  //     jumpClass: "Oct",
  //     notes: null,
  //   },
  //   {
  //     id: 4,
  //     firstName: "Jose",
  //     lastName: " Rivera",
  //     email: "jose.river@gmail.com",
  //     grade: 93,
  //     jumpClass: "Oct",
  //     notes: null,
  //   },
  //   {
  //     id: 5,
  //     firstName: "David",
  //     lastName: " Burch",
  //     email: "david.burch@gmail.com",
  //     grade: 91,
  //     jumpClass: "Oct",
  //     notes: null,
  //   },
  //   {
  //     id: 6,
  //     firstName: "David",
  //     lastName: " Burch",
  //     email: "david.burch@gmail.com",
  //     grade: 91,
  //     jumpClass: "Oct",
  //     notes: null,
  //   },
  //   {
  //     id: 7,
  //     firstName: "David",
  //     lastName: " Burch",
  //     email: "david.burch@gmail.com",
  //     grade: 91,
  //     jumpClass: "Oct",
  //     notes: null,
  //   },
  //   {
  //     id: 8,
  //     firstName: "David",
  //     lastName: " Burch",
  //     email: "david.burch@gmail.com",
  //     grade: 91,
  //     jumpClass: "Oct",
  //     notes: null,
  //   },
  //   {
  //     id: 9,
  //     firstName: "David",
  //     lastName: " Burch",
  //     email: "david.burch@gmail.com",
  //     grade: 91,
  //     jumpClass: "Oct",
  //     notes: null,
  //   },
  //   {
  //     id: 10,
  //     firstName: "David",
  //     lastName: " Burch",
  //     email: "david.burch@gmail.com",
  //     grade: 91,
  //     jumpClass: "Oct",
  //     notes: null,
  //   },
  // ]);
  const [student, setStudent] = useState([]);
  const fetchAllStudents = () => {
    debugger;
    Api.getAllStudents(setStudent);
    // .then((data) => {
    //   // setStudent(data);
    //   // console.log(student);
    // })
    // .catch((error) => {
    //   console.log(error);
    // });
  };
  const handleLoginFunc = () => {
    // debugger;
    if (userLogin.username && userLogin.password) {
      fetchAllStudents();
      // console.log(student);
    }
  };

  return (
    <div>
      <Login
        user={user}
        setUser={setUser}
        userLogin={userLogin}
        setUserLogin={setUserLogin}
        handleLoginFunc={handleLoginFunc}
      />
      <Students student={student} setStudent={setStudent} />
    </div>
  );
}

export default App;

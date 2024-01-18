import Students from "../components/Students";

const URI = "http://localhost:8080";
const addInstructor = URI + "/api/instructors";
const loginInstructor = URI + "/authenticate";
const allStudents = URI + "/api/students";
// const allStudentsByInstructor =
//   URI + `/api/students/instructor/${userLogin.username}`;
const allStudentsByInstructor = (username) =>
  URI + `/api/students/instructor/${username}`;
const allInstructor = URI + `/api/instructors`;
const deleteStudent = (id) => URI + `/api/students/delete/${id}`;
const updateStudent = URI + "/api/students/update";
const addStudentEndPoint = URI + "/api/students/add";
const Api = {
  addUser: (user) => {
    fetch(addInstructor, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(user),
    })
      .then((result) => {
        console.log("Result");
        console.log(result);

        // console.log("")
        return result.json();
      })
      .then((data) => {
        console.log("Data");
        // Data goes here in state
        //     setUser(data);
        // debugger;
        // setId(data.id);
      })
      .catch((error) => {
        console.log(error);
      });
  },

  loginUser: (userLogin) => {
    // console.log("hello");
    return fetch(loginInstructor, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(userLogin),
    })
      .then((result) => {
        console.log("Result", result);
        if (!result.ok) {
          throw new Error(`HTTP error! Status: ${result.status}`);
        }
        return result.json();
      })
      .then((data) => {
        console.log("Data", data);

        if (data.jwt) {
          localStorage.setItem("jwtToken", data.jwt);
          // setisLoggedIn(false);
          // console.log(true);
          // debugger;
          return data.jwt; // Return the token from the response
        } else {
          throw new Error("Token not found in the response");
        }
      })
      .catch((error) => {
        console.error("Error", error);
        throw error; // Rethrow the error for handling in the calling function
      });
  },
  getAllInstructor: (setId) => {
    const storedToken = localStorage.getItem("jwtToken");
    const headers = {
      authorization: `Bearer ${storedToken}`,
    };
    fetch(allInstructor, {
      method: "GET",
      headers: headers,
    })
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        setId([data.id.length - 1]);
      })
      .catch((error) => {
        console.log(error);
      });
  },
  getAllStudents: (setStudent) => {
    const storedToken = localStorage.getItem("jwtToken");
    const headers = {
      authorization: `Bearer ${storedToken}`,
    };
    fetch(allStudents, {
      method: "GET",
      headers: headers,
    })
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        console.log(data);
        // return data;
        setStudent(data);
        // console.log(Student);
      })
      .catch((error) => {
        console.log(error);
      });
  },

  findStudentsInstructorUsername: (setStudent, username) => {
    const storedToken = localStorage.getItem("jwtToken");
    const headers = {
      authorization: `Bearer ${storedToken}`,
    };
    fetch(allStudentsByInstructor(username), {
      method: "GET",
      headers: headers,
    })
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        console.log(data);
        // return data;
        setStudent(data);
        // console.log(Student);
      })
      .catch((error) => {
        console.log(error);
      });
  },

  addStudent: (newStudent) => {
    const storedToken = localStorage.getItem("jwtToken");
    // const headers = {
    //   headers:  "Content-Type": "application/json" ,
    //   authorization: `Bearer ${storedToken}`,
    // };
    fetch(addStudentEndPoint, {
      method: "POST",

      headers: {
        "Content-Type": "application/json",

        authorization: `Bearer ${storedToken}`,
      },
      body: JSON.stringify(newStudent),
    })
      .then((result) => {
        debugger;
        console.log(result.json);
        return result.json();
      })
      .then((data) => {
        debugger;
        console.log(data);
      })
      .catch((error) => {
        console.log(error);
      });
  },

  deleteStudent: (id) => {
    const storedToken = localStorage.getItem("jwtToken");
    fetch(deleteStudent(id), {
      method: "DELETE",
      headers: {
        // "Content-Type": "application/json",
        authorization: `Bearer ${storedToken}`,
      },
    })
      .then((result) => {
        console.log("result ...");
        console.log(result);
        return result.json();
      })
      .then((data) => {
        console.log("data grabbed");
        // console.log(data);
      })
      .catch((error) => {
        console.log("error detected");
        // console.log(error);
      });
  },
  updateStudent: (updatedStudent) => {
    const storedToken = localStorage.getItem("jwtToken");
    fetch(updateStudent, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        authorization: `Bearer ${storedToken}`,
      },
      body: JSON.stringify(updatedStudent),
    })
      .then((data) => {
        console.log("data grabed");
      })
      .catch((error) => {
        console.log("Error");
      });
  },
};
export default Api;

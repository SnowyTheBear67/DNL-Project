import Students from "../components/Students";

const URI = "http://localhost:8080";
const addInstructor = URI + "/api/instructors";
const loginInstructor = URI + "/authenticate";
const allStudents = URI + "/api/students";

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
      })
      .catch((error) => {
        console.log(error);
      });
  },

  // loginUser: (userLogin, setToken) => {
  //   fetch(loginInstructor, {
  //     method: "POST",
  //     headers: { "Content-Type": "application/json" },
  //     body: JSON.stringify(userLogin),
  //   })
  //     .then((result) => {
  //       console.log("Result");
  //       console.log(result);
  //       return result.json();
  //     })
  //     .then((data) => {
  //       console.log("Data");
  //       console.log(data);
  //       // console.log(data.jwt);
  //       setToken(data.jwt);
  //     })
  //     .catch((error) => {
  //       console.log(error);
  //     });
  // },
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

  getAllStudents: (setStudent) => {
    const storedToken = localStorage.getItem("jwtToken");
    const headers = {
      "Content-Type": "application/json",
      Authorization: `Bearer ${storedToken}`,
    };
    fetch(allStudents, {
      method: "GET",
      headers: headers,
    })
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        // console.log(data);
        // return data;
        setStudent(data);
        // console.log(Student);
      })
      .catch((error) => {
        console.log(error);
      });
  },
};
export default Api;

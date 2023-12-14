import { useState } from "react";
import React from "react";

const Students = (props) => {
  const { student, setStudent } = props;
  const [isAddingStudent, setIsAddingStudent] = useState(false);
  const [newStudent, setNewStudent] = useState({
    firstName: "",
    lastName: "",
    jumpClass: "",
    grade: "",
    notes: "",
    email: "",
    instructorId: "",
  });
  const handleSaveNewStudent = () => {
    setStudent((prevStudents) => [...prevStudents, newStudent]);
    setNewStudent({
      firstName: "",
      lastName: "",
      jumpClass: "",
      grade: "",
      notes: "",
    });
    setIsAddingStudent(true);
  };
  const handleDelete = (id) => {
    const updatedDtudents = student.filter((student) => student.id !== id);
    setStudent(updatedDtudents);
  };
  const handleAddStudents = () => {
    debugger;
    setIsAddingStudent(!isAddingStudent);
  };
  return (
    <>
      <div className="dash-title">Welcome to the dashboard</div>
      ____________________________________________________________________________________________________________________________________________________________________________________________________________________________
      <div className="first-line">
        <div className="list">Your List Of Students</div>
        <div>
          <button className="btn btn-primary" onClick={handleAddStudents}>
            Add Student
          </button>
          <button className="btn btn-danger">Signout</button>
        </div>
      </div>
      {isAddingStudent && (
        <div className="grid-container">
          <div className="card border-info mb-3" style={{ maxWidth: "18rem" }}>
            <div className="card-header">
              <input
                type="text"
                placeholder="First Name"
                value={newStudent.firstName}
                onChange={(e) =>
                  setNewStudent({ ...newStudent, firstName: e.target.value })
                }
              />
              <input
                type="text"
                placeholder="Last Name"
                value={newStudent.lastName}
                onChange={(e) =>
                  setNewStudent({ ...newStudent, lastName: e.target.value })
                }
              />
              <input
                type="text"
                placeholder="Jump Class"
                value={newStudent.jumpClass}
                onChange={(e) =>
                  setNewStudent({ ...newStudent, jumpClass: e.target.value })
                }
              />
              <input
                type="text"
                placeholder="Grades"
                value={newStudent.grade}
                onChange={(e) =>
                  setNewStudent({ ...newStudent, grade: e.target.value })
                }
              />
              <input
                type="text"
                placeholder="Notes"
                value={newStudent.notes}
                onChange={(e) =>
                  setNewStudent({ ...newStudent, notes: e.target.value })
                }
              />
              <input
                type="text"
                placeholder="Email"
                value={newStudent.email}
                onChange={(e) =>
                  setNewStudent({ ...newStudent, email: e.target.value })
                }
              />
              <input
                type="text"
                placeholder="Instructor ID"
                value={newStudent.instructorId}
                onChange={(e) =>
                  setNewStudent({ ...newStudent, instructorId: e.target.value })
                }
              />
            </div>
            <div className="card-body">
              {/* Add input fields for other properties of the new student */}
            </div>
            <div className="btn-group" role="group" aria-label="Basic example">
              <button
                type="button"
                className="btn btn-primary"
                onClick={() => handleSaveNewStudent()}
              >
                Save
              </button>
            </div>
          </div>
        </div>
      )}
      <div className="grid-container">
        {student.map((student) => (
          <div className="card border-info mb-3 " style={{ maxWidth: "18rem" }}>
            <div
              key={student.id}
              className="card-header"
              style={{ display: "flex", justifyContent: "center" }}
            >
              {student.firstName + student.lastName}
            </div>
            <div className="card-body">
              <div
                style={{
                  display: "flex",
                  //   justifyContent: "space-evenly",
                  alignItems: "center",
                }}
              >
                <h5 className="card-title">Jump Class</h5>
                <p className="card-text" style={{ paddingLeft: "20px" }}>
                  {student.jumpClass}
                </p>
              </div>
              <div
                style={{
                  display: "flex",
                  //   justifyContent: "space-evenly",
                  alignItems: "center",
                }}
              >
                <h5 className="card-title">Grade</h5>
                <p className="card-text" style={{ paddingLeft: "65px" }}>
                  {student.grade}
                </p>
              </div>
              <div
                style={{
                  display: "flex",
                  //   justifyContent: "space-evenly",
                  alignItems: "center",
                }}
              >
                <h5 className="card-title">Notes</h5>
                <p className="card-text" style={{ paddingLeft: "65px" }}>
                  {student.notes}
                </p>
              </div>
            </div>
            <div className="btn-group" role="group" aria-label="Basic example">
              {/* <button type="button" class="btn btn-secondary">
                Add
              </button> */}
              <button
                type="button"
                className="btn btn-secondary"
                onClick={() => handleDelete(student.id)}
              >
                Delete
              </button>
              <button type="button" className="btn btn-secondary">
                Edit
              </button>
            </div>
          </div>
        ))}
      </div>
    </>
  );
};

export default Students;

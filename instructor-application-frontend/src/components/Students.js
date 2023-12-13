import React from "react";

const Students = (props) => {
  const { student, setStudent } = props;
  let dummyStudent = [];
  const handleDelete = (id) => {
    const updatedDtudents = student.filter((student) => student.id !== id);
    setStudent(updatedDtudents);
  };
  return (
    <>
      <div className="dash-title">Welcome to the dashboard</div>
      ____________________________________________________________________________________________________________________________________________________________________________________________________________________________
      <div className="list">Your List Of Students</div>
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
      <div className="list">All Student List</div>
    </>
  );
};

export default Students;

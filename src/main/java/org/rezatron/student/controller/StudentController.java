package org.rezatron.student.controller;

import org.rezatron.student.dto.StudentDTO;
import org.rezatron.student.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Student", description = "API for managing students")
public class StudentController {

    private final StudentService studentService;

    @Operation(summary = "Get all students", description = "Retrieve a list of all students")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of students",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class))})
    })
    @GetMapping
    public List<StudentDTO> getAllStudents() {
        log.info("Received request to fetch all students");
        return studentService.getAllStudents();
    }

    @Operation(summary = "Get a student by ID", description = "Retrieve a student by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of the student",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    @GetMapping("/{id}")
    public StudentDTO getStudentById(@PathVariable Long id) {
        log.info("Received request to fetch student with id: {}", id);
        return studentService.getStudentById(id);
    }

    @Operation(summary = "Create a new student", description = "Create a new student with the given details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful creation of the student",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        log.info("Received request to create a student: {}", studentDTO);
        StudentDTO createdStudent = studentService.createStudent(studentDTO);
        log.info("Student created with id: {}", createdStudent.getId());
        return ResponseEntity.ok(createdStudent);
    }

    @Operation(summary = "Update an existing student", description = "Update the details of an existing student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful update of the student",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        log.info("Received request to update student with id: {}", id);
        StudentDTO updatedStudent = studentService.updateStudent(id, studentDTO);
        log.info("Student updated with id: {}", updatedStudent.getId());
        return ResponseEntity.ok(updatedStudent);
    }

    @Operation(summary = "Delete a student", description = "Delete a student by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful deletion of the student"),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        log.info("Received request to delete student with id: {}", id);
        studentService.deleteStudent(id);
        log.info("Student deleted with id: {}", id);
        return ResponseEntity.noContent().build();
    }
}
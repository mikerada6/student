package org.rezatron.student.unit.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rezatron.student.controller.StudentController;
import org.rezatron.student.dto.StudentDTO;
import org.rezatron.student.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private StudentDTO studentDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        studentDTO = new StudentDTO(1L, "John Doe", "john@example.com", "Math");
    }

    @Test
    public void testGetAllStudents_Success() {
        List<StudentDTO> students = Arrays.asList(studentDTO);
        when(studentService.getAllStudents()).thenReturn(students);

        List<StudentDTO> result = studentController.getAllStudents();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    public void testGetStudentById_Success() {
        when(studentService.getStudentById(1L)).thenReturn(studentDTO);

        StudentDTO result = studentController.getStudentById(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(studentService, times(1)).getStudentById(1L);
    }

    @Test
    public void testCreateStudent_Success() {
        when(studentService.createStudent(any(StudentDTO.class))).thenReturn(studentDTO);

        ResponseEntity<StudentDTO> response = studentController.createStudent(new StudentDTO(null, "John Doe", "john@example.com", "Math"));

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getName());
        verify(studentService, times(1)).createStudent(any(StudentDTO.class));
    }

    @Test
    public void testUpdateStudent_Success() {
        when(studentService.updateStudent(eq(1L), any(StudentDTO.class))).thenReturn(studentDTO);

        ResponseEntity<StudentDTO> response = studentController.updateStudent(1L, new StudentDTO(null, "John Doe", "john@example.com", "Math"));

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getName());
        verify(studentService, times(1)).updateStudent(eq(1L), any(StudentDTO.class));
    }

    @Test
    public void testDeleteStudent_Success() {
        doNothing().when(studentService).deleteStudent(1L);

        ResponseEntity<Void> response = studentController.deleteStudent(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(studentService, times(1)).deleteStudent(1L);
    }
}

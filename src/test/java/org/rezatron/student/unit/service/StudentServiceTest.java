package org.rezatron.student.unit.service;




import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rezatron.student.dto.StudentDTO;
import org.rezatron.student.entity.Student;
import org.rezatron.student.exception.ResourceNotFoundException;
import org.rezatron.student.mapper.StudentMapper;
import org.rezatron.student.repository.StudentRepository;
import org.rezatron.student.service.StudentService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentService studentService;

    private StudentDTO studentDTO;
    private Student student;

    @BeforeEach
    public void setUp() {
        studentDTO = new StudentDTO(null, "John Doe", "john@example.com", "Math");
        student = new Student(1L, "John Doe", "john@example.com", "Math");
    }

    @Test
    public void testGetStudentById_Success() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentMapper.toDTO(student)).thenReturn(new StudentDTO(1L, "John Doe", "john@example.com", "Math"));

        StudentDTO foundStudent = studentService.getStudentById(1L);

        assertNotNull(foundStudent);
        assertEquals(1L, foundStudent.getId());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetStudentById_NotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> studentService.getStudentById(1L));
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateStudent_Success() {
        when(studentMapper.toEntity(any(StudentDTO.class))).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(student);
        when(studentMapper.toDTO(student)).thenReturn(new StudentDTO(1L, "John Doe", "john@example.com", "Math"));

        StudentDTO createdStudent = studentService.createStudent(studentDTO);

        assertNotNull(createdStudent);
        assertEquals(1L, createdStudent.getId());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void testCreateStudent_NullInput() {
        assertThrows(NullPointerException.class, () -> studentService.createStudent(null));
        verify(studentRepository, times(0)).save(any(Student.class));
    }

    @Test
    public void testUpdateStudent_Success() {
        Student updatedStudent = new Student(1L, "John Doe Updated", "john.updated@example.com", "Biology");
        StudentDTO updateDTO = new StudentDTO(null, "John Doe Updated", "john.updated@example.com", "Biology");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);
        when(studentMapper.toDTO(any(Student.class))).thenReturn(new StudentDTO(1L, "John Doe Updated", "john.updated@example.com", "Biology"));

        StudentDTO result = studentService.updateStudent(1L, updateDTO);

        assertNotNull(result);
        assertEquals("John Doe Updated", result.getName());
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(updatedStudent);
    }

    @Test
    public void testUpdateStudent_NotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> studentService.updateStudent(1L, studentDTO));
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(0)).save(any(Student.class));
    }

    @Test
    public void testUpdateStudent_NullInput() {
        assertThrows(org.rezatron.student.exception.ResourceNotFoundException.class, () -> studentService.updateStudent(1L, null));
        verify(studentRepository, times(0)).save(any(Student.class));
    }

    @Test
    public void testDeleteStudent_Success() {
        when(studentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(studentRepository).deleteById(1L);

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).existsById(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteStudent_NotFound() {
        when(studentRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> studentService.deleteStudent(1L));
        verify(studentRepository, times(1)).existsById(1L);
        verify(studentRepository, times(0)).deleteById(1L);
    }

    @Test
    public void testGetAllStudents_EmptyList() {
        when(studentRepository.findAll()).thenReturn(Collections.emptyList());

        List<StudentDTO> students = studentService.getAllStudents();

        assertNotNull(students);
        assertTrue(students.isEmpty());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllStudents_Success() {
        List<Student> students = Arrays.asList(student, new Student(2L, "Jane Doe", "jane@example.com", "Physics"));
        when(studentRepository.findAll()).thenReturn(students);
        when(studentMapper.toDTO(any(Student.class)))
                .thenReturn(new StudentDTO(1L, "John Doe", "john@example.com", "Math"))
                .thenReturn(new StudentDTO(2L, "Jane Doe", "jane@example.com", "Physics"));

        List<StudentDTO> studentDTOList = studentService.getAllStudents();

        assertNotNull(studentDTOList);
        assertEquals(2, studentDTOList.size());
        verify(studentRepository, times(1)).findAll();
    }
}
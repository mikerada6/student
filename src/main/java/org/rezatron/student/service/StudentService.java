package org.rezatron.student.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.rezatron.student.dto.StudentDTO;
import org.rezatron.student.entity.Student;
import org.rezatron.student.exception.ResourceNotFoundException;
import org.rezatron.student.mapper.StudentMapper;
import org.rezatron.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public List<StudentDTO> getAllStudents() {
        log.info("Fetching all students");
        List<StudentDTO> students = studentRepository.findAll().stream()
                .map(studentMapper::toDTO)
                .collect(Collectors.toList());
        log.info("Fetched {} students", students.size());
        return students;
    }

    public StudentDTO getStudentById(Long id) {
        log.info("Fetching student with id: {}", id);
        return studentMapper.toDTO(studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Student not found with id: {}", id);
                    return new ResourceNotFoundException("Student not found with id: " + id);
                }));
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        log.info("Creating a new student: {}", studentDTO);
        Student student = studentMapper.toEntity(studentDTO);
        StudentDTO createdStudent = studentMapper.toDTO(studentRepository.save(student));
        log.info("Student created with id: {}", createdStudent.getId());
        return createdStudent;
    }

    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        log.info("Updating student with id: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Student not found with id: {}", id);
                    return new ResourceNotFoundException("Student not found with id: " + id);
                });
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setCourse(studentDTO.getCourse());
        StudentDTO updatedStudent = studentMapper.toDTO(studentRepository.save(student));
        log.info("Student updated with id: {}", updatedStudent.getId());
        return updatedStudent;
    }

    public void deleteStudent(Long id) {
        log.info("Deleting student with id: {}", id);
        if (!studentRepository.existsById(id)) {
            log.error("Student not found with id: {}", id);
            throw new ResourceNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
        log.info("Student deleted with id: {}", id);
    }
}
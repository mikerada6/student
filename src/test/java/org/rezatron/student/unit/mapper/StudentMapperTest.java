package org.rezatron.student.unit.mapper;


import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.rezatron.student.dto.StudentDTO;
import org.rezatron.student.entity.Student;
import org.rezatron.student.mapper.StudentMapper;

import static org.junit.jupiter.api.Assertions.*;

public class StudentMapperTest {

    private StudentMapper studentMapper = Mappers.getMapper(StudentMapper.class);

    @Test
    public void testToDTO() {
        Student student = new Student(1L, "John Doe", "john@example.com", "Math");

        StudentDTO studentDTO = studentMapper.toDTO(student);

        assertNotNull(studentDTO);
        assertEquals(1L, studentDTO.getId());
        assertEquals("John Doe", studentDTO.getName());
        assertEquals("john@example.com", studentDTO.getEmail());
        assertEquals("Math", studentDTO.getCourse());
    }

    @Test
    public void testToEntity() {
        StudentDTO studentDTO = new StudentDTO(1L, "John Doe", "john@example.com", "Math");

        Student student = studentMapper.toEntity(studentDTO);

        assertNotNull(student);
        assertEquals(1L, student.getId());
        assertEquals("John Doe", student.getName());
        assertEquals("john@example.com", student.getEmail());
        assertEquals("Math", student.getCourse());
    }

    @Test
    public void testToDTO_Null() {
        StudentDTO studentDTO = studentMapper.toDTO(null);

        assertNull(studentDTO);
    }

    @Test
    public void testToEntity_Null() {
        Student student = studentMapper.toEntity(null);

        assertNull(student);
    }
}
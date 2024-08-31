package org.rezatron.student.integration;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.rezatron.student.dto.StudentDTO;
import org.rezatron.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    public void testGetAllStudents() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertNotNull(content);
    }

    @Test
    public void testCreateStudent() throws Exception {
        StudentDTO studentDTO = new StudentDTO(null, "Jane Doe", "jane@example.com", "Physics");
        StudentDTO createdStudentDTO = new StudentDTO(1L, "Jane Doe", "jane@example.com", "Physics");

        when(studentService.createStudent(any(StudentDTO.class))).thenReturn(createdStudentDTO);

        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Jane Doe\",\"email\":\"jane@example.com\",\"course\":\"Physics\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Jane Doe"));
    }

    @Test
    public void testGetStudentById() throws Exception {
        StudentDTO studentDTO = new StudentDTO(1L, "John Doe", "john@example.com", "Math");

        when(studentService.getStudentById(1L)).thenReturn(studentDTO);

        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testUpdateStudent() throws Exception {
        StudentDTO updateDTO = new StudentDTO(null, "John Doe Updated", "john.updated@example.com", "Biology");
        StudentDTO updatedStudentDTO = new StudentDTO(1L, "John Doe Updated", "john.updated@example.com", "Biology");

        when(studentService.updateStudent(any(Long.class), any(StudentDTO.class))).thenReturn(updatedStudentDTO);

        mockMvc.perform(put("/api/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe Updated\",\"email\":\"john.updated@example.com\",\"course\":\"Biology\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe Updated"));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        Mockito.doNothing().when(studentService).deleteStudent(1L);

        mockMvc.perform(delete("/api/students/1"))
                .andExpect(status().isNoContent());
    }
}

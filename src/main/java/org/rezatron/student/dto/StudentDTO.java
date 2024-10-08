package org.rezatron.student.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "The unique identifier of the student, auto-generated by the system")
    private Long id;

    @Schema(description = "The name of the student", example = "John Doe", required = true)
    private String name;

    @Schema(description = "The email of the student", example = "johndoe@example.com", required = true)
    private String email;

    @Schema(description = "The course the student is enrolled in", example = "Computer Science", required = true)
    private String course;
}
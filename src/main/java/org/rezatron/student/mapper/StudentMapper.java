package org.rezatron.student.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rezatron.student.dto.StudentDTO;
import org.rezatron.student.entity.Student;


@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    StudentDTO toDTO(Student student);

    Student toEntity(StudentDTO studentDTO);
}
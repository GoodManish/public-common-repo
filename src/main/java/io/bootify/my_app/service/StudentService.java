package io.bootify.my_app.service;

import io.bootify.my_app.domain.Student;
import io.bootify.my_app.domain.Subject;
import io.bootify.my_app.model.StudentDTO;
import io.bootify.my_app.repos.StudentRepository;
import io.bootify.my_app.repos.SubjectRepository;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Transactional
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public StudentService(final StudentRepository studentRepository, final SubjectRepository subjectRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<StudentDTO> findAll() {
        return studentRepository.findAll(Sort.by("id"))
                .stream()
                .map(student -> mapToDTO(student, new StudentDTO()))
                .collect(Collectors.toList());
    }

    public StudentDTO get(final Long id) {
        return studentRepository.findById(id)
                .map(student -> mapToDTO(student, new StudentDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final StudentDTO studentDTO) {
        final Student student = new Student();
        mapToEntity(studentDTO, student);
        return studentRepository.save(student).getId();
    }

    public void update(final Long id, final StudentDTO studentDTO) {
        final Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(studentDTO, student);
        studentRepository.save(student);
    }

    public void delete(final Long id) {
        studentRepository.deleteById(id);
    }

    private StudentDTO mapToDTO(final Student student, final StudentDTO studentDTO) {
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setDateOfBirth(student.getDateOfBirth());
        studentDTO.setStudentSubjects(student.getStudentSubjectSubjects() == null ? null : student.getStudentSubjectSubjects().stream()
                .map(subject -> subject.getId())
                .collect(Collectors.toList()));
        return studentDTO;
    }

    private Student mapToEntity(final StudentDTO studentDTO, final Student student) {
        student.setName(studentDTO.getName());
        student.setDateOfBirth(studentDTO.getDateOfBirth());
        final List<Subject> studentSubjects = subjectRepository.findAllById(
                studentDTO.getStudentSubjects() == null ? Collections.emptyList() : studentDTO.getStudentSubjects());
        if (studentSubjects.size() != (studentDTO.getStudentSubjects() == null ? 0 : studentDTO.getStudentSubjects().size())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of studentSubjects not found");
        }
        student.setStudentSubjectSubjects(studentSubjects.stream().collect(Collectors.toSet()));
        return student;
    }

}

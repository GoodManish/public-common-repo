package io.bootify.my_app.service;

import io.bootify.my_app.domain.Subject;
import io.bootify.my_app.model.SubjectDTO;
import io.bootify.my_app.repos.SubjectRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(final SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<SubjectDTO> findAll() {
        return subjectRepository.findAll(Sort.by("id"))
                .stream()
                .map(subject -> mapToDTO(subject, new SubjectDTO()))
                .collect(Collectors.toList());
    }

    public SubjectDTO get(final Long id) {
        return subjectRepository.findById(id)
                .map(subject -> mapToDTO(subject, new SubjectDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final SubjectDTO subjectDTO) {
        final Subject subject = new Subject();
        mapToEntity(subjectDTO, subject);
        return subjectRepository.save(subject).getId();
    }

    public void update(final Long id, final SubjectDTO subjectDTO) {
        final Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(subjectDTO, subject);
        subjectRepository.save(subject);
    }

    public void delete(final Long id) {
        subjectRepository.deleteById(id);
    }

    private SubjectDTO mapToDTO(final Subject subject, final SubjectDTO subjectDTO) {
        subjectDTO.setId(subject.getId());
        subjectDTO.setName(subject.getName());
        return subjectDTO;
    }

    private Subject mapToEntity(final SubjectDTO subjectDTO, final Subject subject) {
        subject.setName(subjectDTO.getName());
        return subject;
    }

}

package io.bootify.my_app.rest;

import io.bootify.my_app.model.SubjectDTO;
import io.bootify.my_app.service.SubjectService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/subjects", produces = MediaType.APPLICATION_JSON_VALUE)
public class SubjectResource {

    private final SubjectService subjectService;

    public SubjectResource(final SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public ResponseEntity<List<SubjectDTO>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> getSubject(@PathVariable final Long id) {
        return ResponseEntity.ok(subjectService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSubject(@RequestBody @Valid final SubjectDTO subjectDTO) {
        return new ResponseEntity<>(subjectService.create(subjectDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSubject(@PathVariable final Long id,
            @RequestBody @Valid final SubjectDTO subjectDTO) {
        subjectService.update(id, subjectDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSubject(@PathVariable final Long id) {
        subjectService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

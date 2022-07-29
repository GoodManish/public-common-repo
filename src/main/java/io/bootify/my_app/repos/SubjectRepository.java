package io.bootify.my_app.repos;

import io.bootify.my_app.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SubjectRepository extends JpaRepository<Subject, Long> {
}

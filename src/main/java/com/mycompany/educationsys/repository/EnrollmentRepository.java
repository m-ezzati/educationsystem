package com.mycompany.educationsys.repository;

import com.mycompany.educationsys.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

}

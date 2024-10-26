package ru.hogwarts.school_magic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school_magic.model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByStudentId(Long studentId);
}

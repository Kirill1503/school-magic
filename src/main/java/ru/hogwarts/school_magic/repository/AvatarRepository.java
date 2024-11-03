package ru.hogwarts.school_magic.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school_magic.model.Avatar;

import java.util.List;
import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByStudentId(Long studentId);
    List<Avatar> findAll();

    @Query(value = "SELECT * FROM avatar", nativeQuery = true)
    List<Avatar> findAllAvatars();
}

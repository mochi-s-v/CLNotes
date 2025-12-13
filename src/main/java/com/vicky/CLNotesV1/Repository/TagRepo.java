package com.vicky.CLNotesV1.Repository;

import com.vicky.CLNotesV1.Entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepo extends JpaRepository<TagEntity, Long> {
    Optional<TagEntity> findByTagName(String tagName);
    Optional<TagEntity> findByTagNameContainingIgnoreCase(String keyword);
}

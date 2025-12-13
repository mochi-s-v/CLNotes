package com.vicky.CLNotesV1.Repository;

import com.vicky.CLNotesV1.Entity.NoteEntity;
import com.vicky.CLNotesV1.Entity.NoteTagEntity;
import com.vicky.CLNotesV1.Entity.TagEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteTagRepo extends JpaRepository<NoteTagEntity, Long> {
    Page<NoteTagEntity> findByTagEntity(TagEntity tag, Pageable pageable);
}

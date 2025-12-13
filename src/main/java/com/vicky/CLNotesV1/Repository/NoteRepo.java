package com.vicky.CLNotesV1.Repository;

import com.vicky.CLNotesV1.Entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepo extends JpaRepository<NoteEntity, Long> {
}

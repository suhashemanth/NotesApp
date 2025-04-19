package com.suhas.NotesApp.repository;

import com.suhas.NotesApp.model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes,Long> {
}

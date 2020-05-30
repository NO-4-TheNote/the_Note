package com.group4.service;

import com.group4.domain.Note;
import org.springframework.stereotype.Service;

import javax.xml.catalog.Catalog;
import java.util.List;

public interface INoteService {
    List<Note> findAllNote(Catalog catalog);
    Note findNoteByName(String name);
    Note findNoteById(Integer id);
    void saveNote(Note note);
    void updateNote(Note note);
    void deleteNote(Note note);
}

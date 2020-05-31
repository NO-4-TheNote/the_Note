package com.group4.service.impl;

import com.group4.dao.INoteDao;
import com.group4.domain.Note;
import com.group4.service.INoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.catalog.Catalog;
import java.util.List;

@Service
public class NoteServiceImpl implements INoteService {

    @Autowired
    private INoteDao noteDao;

    @Override
    public void initTable() {
        noteDao.initTable();
    }

    @Override
    public List<Note> findAllNote(Catalog catalog) {
        return null;
    }

    @Override
    public Note findNoteByName(String name) {
        return null;
    }

    @Override
    public Note findNoteById(Integer id) {
        return noteDao.findNoteById(id);
    }

    @Override
    public void saveNote(Note note) {
        noteDao.saveNote(note);
    }

    @Override
    public void updateNote(Note note) {
        noteDao.updateNote(note);
    }

    @Override
    public void deleteNote(Note note) {
        noteDao.deleteNote(note.getId());
    }
}

package com.group4.domain;

import java.io.Serializable;
import java.util.List;

public class Catalog implements Serializable {

    private Integer id;
    private Integer kid;
    private String name;

    //用于多对一(mybatis中称之为一对一)的映射，一个目录只能被一个知识库拥有
    private KnowledgeBase belongToKnowledgeBase;

    //用于一对多的映射，一个目录有多个笔记
    private List<Note> notes;

    public Catalog(Integer kid, String name) {
        this.kid=kid;
        this.name=name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKid() {
        return kid;
    }

    public void setKid(Integer kid) {
        this.kid = kid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KnowledgeBase getBelongToKnowledgeBase() {
        return belongToKnowledgeBase;
    }

    public void setBelongToKnowledgeBase(KnowledgeBase belongToKnowledgeBase) {
        this.belongToKnowledgeBase = belongToKnowledgeBase;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "id=" + id +
                ", kid=" + kid +
                ", name='" + name + '\'' +
                ", belongToKnowledgeBase=" + belongToKnowledgeBase +
                ", notes=" + notes +
                '}';
    }
}

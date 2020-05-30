package com.group4.domain;

import java.io.Serializable;
import java.util.List;

public class Note implements Serializable {

    private Integer id;
    private Integer cid;
    private String name;
    private String title;
    private String content;

    //用于多对一(mybatis中称之为一对一)的映射，一个笔记只能被一个目录拥有
    private Catalog belongToCatalog;

    public Note(Integer cid, String name) {
        this.cid=cid;
        this.name=name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", cid=" + cid +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", belongToCatalog=" + belongToCatalog +
                '}';
    }
}

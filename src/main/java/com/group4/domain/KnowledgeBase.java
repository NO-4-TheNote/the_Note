package com.group4.domain;

import java.io.Serializable;
import java.util.List;

public class KnowledgeBase implements Serializable {

    private Integer id;
    private String name;

    //一对多关系映射：一个知识库有多个目录
    private List<Catalog> catalogList;

    public KnowledgeBase(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Catalog> getCatalogList() {
        return catalogList;
    }

    public void setCatalogList(List<Catalog> catalogList) {
        this.catalogList = catalogList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "KnowledgeBase{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", catalogList=" + catalogList +
                '}';
    }
}

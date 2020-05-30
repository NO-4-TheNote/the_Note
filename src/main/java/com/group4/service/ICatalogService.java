package com.group4.service;

import com.group4.domain.Catalog;
import com.group4.domain.KnowledgeBase;


import java.util.List;

public interface ICatalogService {
    List<Catalog> findAllCatalog(KnowledgeBase knowledgeBase);
    Catalog findCatalogById(Integer id);
    Catalog findCatalogByName(String name);
    void saveCatalog(Catalog catalog);
    void updateCatalog(Catalog catalog);
    void deleteCatalog(Catalog catalog);
}

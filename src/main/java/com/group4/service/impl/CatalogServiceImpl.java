package com.group4.service.impl;

import com.group4.dao.ICatalogDao;
import com.group4.domain.Catalog;
import com.group4.domain.KnowledgeBase;
import com.group4.domain.Note;
import com.group4.service.ICatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CatalogServiceImpl implements ICatalogService {

    @Autowired
    private ICatalogDao catalogDao;

    @Override
    public List<Catalog> findAllCatalog(KnowledgeBase knowledgeBase) {
        return null;
    }

    @Override
    public Catalog findCatalogById(Integer id) {
        return catalogDao.findCatalogById(id);
    }

    @Override
    public Catalog findCatalogByName(String name) {
        return null;
    }

    @Override
    public void saveCatalog(Catalog catalog) {
        catalogDao.saveCatalog(catalog);
    }

    @Override
    public void updateCatalog(Catalog catalog) {
        catalogDao.updateCatalog(catalog);
    }

    @Override
    public void deleteCatalog(Catalog catalog) {
        catalogDao.deleteCatalog(catalog.getId());
        for (Note note : catalog.getNotes()) {

        }
    }
}

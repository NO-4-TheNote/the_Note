package com.group4.service.impl;

import com.group4.dao.IKnowledgeBaseDao;
import com.group4.domain.KnowledgeBase;
import com.group4.service.IKnowledgeBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KnowledgeBaseServiceImpl implements IKnowledgeBaseService {

    @Autowired
    private IKnowledgeBaseDao knowledgeBaseDao;

    @Override
    public void initTable() {
        knowledgeBaseDao.initTable();
    }

    @Override
    public List<KnowledgeBase> findAllKnowledgeBase() {
        return knowledgeBaseDao.findAllKnowledgeBase();
    }

    @Override
    public KnowledgeBase findKnowledgeBaseByName(String name) {
        return null;
    }

    @Override
    public KnowledgeBase findKnowledgeBaseById(Integer id) {
        return knowledgeBaseDao.findKnowledgeBaseById(id);

    }

    @Override
    public void saveKnowledgeBase(KnowledgeBase knowledgeBase) {
        knowledgeBaseDao.saveKnowledgeBase(knowledgeBase);
    }

    @Override
    public void updateKnowledgeBase(KnowledgeBase knowledgeBase) {

    }

    @Override
    public void deleteKnowledgeBase(String name) {

    }
}

package com.group4.service;

import com.group4.domain.KnowledgeBase;

import java.util.List;

public interface IKnowledgeBaseService {
    List<KnowledgeBase> findAllKnowledgeBase();
    KnowledgeBase findKnowledgeBaseByName(String name);
    KnowledgeBase findKnowledgeBaseById(Integer id);
    void saveKnowledgeBase(KnowledgeBase knowledgeBase);
    void updateKnowledgeBase(KnowledgeBase knowledgeBase);
    void deleteKnowledgeBase(String name);
}

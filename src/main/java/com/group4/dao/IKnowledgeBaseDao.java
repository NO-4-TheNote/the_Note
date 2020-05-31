package com.group4.dao;

import com.group4.domain.KnowledgeBase;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface IKnowledgeBaseDao {
    /**
     * 建表
     */
    @Update("create table if not exists `knowledgeBase` (`id` int auto_increment PRIMARY KEY, `name` text )")
    void initTable();

    /**
     * 查询所有知识库
     * @return
     */
    @Select("select * from knowledgeBase")
    @Results(id="kbMap",value = {
            @Result(id=true,column = "id",property = "id"),
            @Result(property = "catalogList",column = "id",
                    many = @Many(select = "com.group4.dao.ICatalogDao.findAllCatalogByKid",
                            fetchType = FetchType.LAZY))
    })
    List<KnowledgeBase> findAllKnowledgeBase();

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Select("select * from knowledgeBase where id=#{id}")
    @ResultMap("kbMap")
    KnowledgeBase findKnowledgeBaseById(Integer id);

    /**
     * 根据名字查询，知识库名字不重复
     * @param name
     * @return
     */
    @Select("select * from where name=#{name}")
    KnowledgeBase findKnowledgeBaseByName(String name);

    /**
     * 新建知识库保存
     * @param knowledgeBase
     */
    @Insert("insert into knowledgeBase (name)values(#{name})")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = Integer.class,before = false,statement = {"select last_insert_id()"})
    void saveKnowledgeBase(KnowledgeBase knowledgeBase);

    /**
     * 更新知识库
     * @param knowledgeBase
     */
    @Update("update knowledgeBase set name=#{name} where id=#{id}")
    void updateKnowledgeBase(KnowledgeBase knowledgeBase);

    /**
     * 删除知识库
     * @param id
     */
    @Delete("delete from knowledgeBase where id=#{id}")
    void deleteKnowledgeBase(Integer id);
}

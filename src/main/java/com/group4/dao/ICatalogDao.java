package com.group4.dao;

import com.group4.domain.Catalog;
import com.group4.domain.KnowledgeBase;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface ICatalogDao {
    /**
     * 建表
     */
    @Update("create table if not exists `catalog` (`id` int auto_increment PRIMARY KEY, `kid` int not null, `name` varchar(50), constraint belong_kb foreign key(kid) references knowledgeBase(id) on delete cascade)")
    void initTable();

    /**
     * 查询所有目录，并且获取每个目录下的所属的知识库信息
     * @return
     */
    @Select("select * from catalog")
    @Results(id="cMap",value = {
                    @Result(id=true,column = "id",property = "id"),
                    //@Result(property = "belongToKnowledgeBase",column = "kid", one=@One(select="com.group4.dao.IKnowledgeBaseDao.findKnowledgeBaseById", fetchType= FetchType.EAGER)),
                    @Result(property = "notes",column = "id", many = @Many(select = "com.group4.dao.INoteDao.findAllNoteByCid", fetchType = FetchType.LAZY))
    })
    List<Catalog> findAllCatalog();

    /**
     * 给定一个知识库的id，查询属于该id的所有目录
     * @return
     */
    @Select("select * from catalog where kid=#{kid}")
    @ResultMap("cMap")
    List<Catalog> findAllCatalogByKid(Integer kid);

    /**
     * 根据id查询目录
     * @param id
     * @return
     */
    @Select("select * from catalog where id=#{id}")
    @ResultMap("cMap")
    Catalog findCatalogById(Integer id);

    /**
     * 给定名字查询目录（模糊查询）
     * @param name
     * @return
     */
    @Select("select * from catalog where name like #{name}")
    List<Catalog> findCatalogByName(String name);

    /**
     * 新建目录
     * @param catalog
     */
    @Insert("insert catalog(kid,name)values(#{kid},#{name})")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = Integer.class,before = false,statement = {"select last_insert_id()"})
    int saveCatalog(Catalog catalog);

    /**
     * 修改目录
     * @param catalog
     */
    @Update("update catalog set kid=#{kid},name=#{name} where id=#{id}")
    void updateCatalog(Catalog catalog);

    /**
     * 根据id删除目录
     * @param id
     */
    @Delete("delete from catalog where id=#{id}")//此处需要一个级联删除的语句
    void deleteCatalog(Integer id);
}

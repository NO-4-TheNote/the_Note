package com.group4.dao;

import com.group4.domain.Note;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import javax.xml.catalog.Catalog;
import java.util.List;

@Mapper
public interface INoteDao {
    /**
     * 建表
     */
    @Update("create table if not exists `note` (`id` int auto_increment PRIMARY KEY, `cid` int not null, `name` varchar(50), title text, content longtext, constraint belong_catalog foreign key(cid) references catalog(id) on delete cascade)")
    void initTable();

    /**
     * 查询所有
     * @return
     */
    @Select("select * from catalog")
    @Results(id="cMap",value = {
            @Result(property = "belongToCatalog",column = "cid", one=@One(select="com.group4.dao.ICatalogDao.findCatalogById", fetchType= FetchType.EAGER))
    })
    List<Note> findAllNote();

    /**
     * 给定一个目录id，查询该目录下的所有笔记
     * @return
     */
    @Select("select * from note where cid=#{cid}")
    List<Note> findAllNoteByCid(Integer cid);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Select("select * from note where id=#{id}")
    Note findNoteById(Integer id);

    /**
     * 根据名字模糊查询
     * @param name
     * @return
     */
    @Select("select * from note where name like #{name}")
    List<Note> findNoteByName(String name);

    /**
     * 新建笔记
     * @param Note
     */
    @Insert("insert note(cid,name,title,content)value(#{cid},#{name},#{title},#{content})")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = Integer.class,before = false,statement = {"select last_insert_id()"})
    void saveNote(Note Note);

    /**
     * 修改笔记
     * @param Note
     */
    @Update("update note set cid=#{cid},name=#{name},title=#{title},content=#{content} where id=#{id}")
    void updateNote(Note Note);

    /**
     * 删除笔记
     * @param id
     */
    @Delete("delete from note where id=#{id}")
    void deleteNote(Integer id);
}

package com.ykl.dao;

import com.langlang.annotations.Delete;
import com.langlang.annotations.Insert;
import com.langlang.annotations.Select;
import com.langlang.annotations.Update;
import com.ykl.pojo.Star;

import java.util.List;

public interface StarDao {

    @Insert("insert into star values(#{id}, #{name}, #{created})")
    int insert(Star star);

    @Update("update star set name = #{name} where id = #{id}")
    int update(Star star);

    @Delete("delete from star where id = #{id}")
    int delete(Star star);

    @Select("select * from star where id = #{id}")
    Star select(int id);

    @Select("select * from star")
    List<Star> findAll();


}

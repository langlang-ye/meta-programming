package com.ykl.dao;

import com.ykl.pojo.Person;

import java.util.List;

/**
 * @author langlang.ye
 * @date 2021/5/16
 */
public interface PersonDao {

    // 查询所有用户
    public List<Person> findAll() throws Exception;

    // 根据条件查询所有用户
    public Person findByCondition(Person user) throws Exception;

    public void savePerson(Person user) throws Exception;

    public void updatePerson(Person user) throws Exception;

    public void deletePerson(Person person) throws Exception;
}

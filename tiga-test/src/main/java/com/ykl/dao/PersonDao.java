package com.ykl.dao;

import com.ykl.pojo.Person;
import com.ykl.pojo.PersonBO;

import java.util.List;
import java.util.Map;

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

    public Person selectPersonById(int id) throws Exception;

    public Long countNum() throws Exception;

    public List<String> listName() throws Exception;

    public int selectAgeById(int id) throws Exception;

//    public int selectAgeByName(String name) throws Exception;

    public int selectAgeByName(String name) throws Exception;

    public List<Person> selectListByCondition(PersonBO personBO) throws Exception;
    public Long selectCountByCondition(PersonBO personBO) throws Exception;

    public List<Person> selectListByMap(Map<String, Object> map);

    public Long selectCountByMap(Map<String, Object> map);
}

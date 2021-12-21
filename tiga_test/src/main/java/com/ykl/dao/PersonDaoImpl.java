package com.ykl.dao;

import com.langlang.io.Resources;
import com.langlang.sqlSession.SqlSession;
import com.langlang.sqlSession.SqlSessionFactory;
import com.langlang.sqlSession.SqlSessionFactoryBuilder;
import com.ykl.pojo.Person;

import java.io.InputStream;
import java.util.List;

/**
 * @author langlang.ye
 * @date 2021/5/16
 */
public class PersonDaoImpl /*implements PersonDao*/ {
/*
    @Override
    public List<Person> findAll() throws Exception {
        var resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        var sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        var sqlSession = sqlSessionFactory.openSession();

        List<Person> users = sqlSession.selectList("com.ykl.dao.PersonDao.findAll");
        return users;
    }

    @Override
    public Person findByCondition(Person person) throws Exception {
        var resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        var sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        var sqlSession = sqlSessionFactory.openSession();

        Person p = sqlSession.selectOne("com.ykl.dao.PersonDao.findByCondition", person);
        return p;
    }

    @Override
    public void savePerson(Person user) throws Exception {
        var resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        var sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        var sqlSession = sqlSessionFactory.openSession();

        sqlSession.update("com.ykl.dao.PersonDao.savePerson", user);
    //    sqlSession.commit();
    }

    @Override
    public void updatePerson(Person user) throws Exception {
        var resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        var sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        var sqlSession = sqlSessionFactory.openSession();

        sqlSession.update("com.ykl.dao.PersonDao.updatePerson", user);
    }

    @Override
    public void deletePerson(Person person) throws Exception {
        var resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        var sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        var sqlSession = sqlSessionFactory.openSession();

        sqlSession.update("com.ykl.dao.PersonDao.deletePerson", person);
    }*/
}

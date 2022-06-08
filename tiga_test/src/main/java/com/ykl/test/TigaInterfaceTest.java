package com.ykl.test;

import com.langlang.io.Resources;
import com.langlang.sqlSession.SqlSessionFactoryBuilder;
import com.ykl.dao.PersonDao;
import com.ykl.pojo.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * 测试接口的方式:
 * 查询所有, 按照条件查询, 更新, 删除等
 * @author langlang.ye
 * @date 2022/6/6
 */
public class TigaInterfaceTest {

    PersonDao personDao;

    @Before
    public void testBefore() throws Exception{
        var resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        var sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        var sqlSession = sqlSessionFactory.openSession();
        personDao = sqlSession.getMapper(PersonDao.class);
    }

    // 查询所有
    @Test
    public void testFindAll() throws Exception{
        List<Person> all = personDao.findAll();
        for (Person dog : all) {
            System.out.println(dog);
        }
    }

    // 条件查询
    @Test
    public void testFindByCondition() throws Exception{
        Person p = new Person();
        p.setId(2);
        p.setName("tom");
        Person peron = personDao.findByCondition(p);
        System.out.println(peron);
    }

    // 添加
    @Test
    public void testSave() throws Exception{
        Person p = new Person();
        p.setId(5);
        p.setName("Tuffy");
        p.setAge(5);
        p.setRemark("添加...");
        personDao.savePerson(p);
    }

    // 修改
    @Test
    public void testUpdate() throws Exception{
        Person p = new Person();
        p.setId(5);
        p.setName("Donald");
        p.setAge(5);
        personDao.updatePerson(p);
    }

    // 删除
    @Test
    public void testDelete() throws Exception{
        Person p = new Person();
        p.setId(5);
        personDao.deletePerson(p);
    }


}

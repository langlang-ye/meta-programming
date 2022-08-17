package com.ykl.test;

import com.ykl.dao.PersonDaoImpl;
import com.ykl.pojo.Person;
import org.junit.Test;

import java.util.List;

/**
 * 测试实现类的方式:
 * 查询所有, 按照条件查询, 更新, 删除等
 * @author langlang.ye
 * @date 2021/5/16
 */
public class TigaImplTest {

    PersonDaoImpl personDao = new PersonDaoImpl();

    // 查询所有
    @Test
    public void testFindAll() throws Exception{
        List<Person> all = personDao.findAll();
        for (Person person : all) {
            System.out.println(person);
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

    // 根据id 查询
    @Test
    public void selectPersonById() throws Exception {
        Person person = personDao.selectPersonById(4);
        System.out.println(person);
    }

    // 统计数量
    @Test
    public void countNum() throws Exception {
        Long number = personDao.countNum();
        System.out.println(number);
    }

}

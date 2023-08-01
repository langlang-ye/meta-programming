package com.ykl.test;

import com.langlang.io.Resources;
import com.langlang.sqlSession.SqlSessionFactoryBuilder;
import com.ykl.common.Page;
import com.ykl.dao.PersonDao;
import com.ykl.pojo.Person;
import com.ykl.pojo.PersonBO;
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

    // 根据id 查询
    @Test
    public void selectPersonById() throws Exception {
        Person person = personDao.selectPersonById(4);
        System.out.println(person);
    }

    // 统计人数
    @Test
    public void countNum() throws Exception {
        Long number = personDao.countNum();
        System.out.println(number);
    }

    // 查询所有的名字
    @Test
    public void listName() throws Exception {
        List<String> names = personDao.listName();
        for (String name : names) {
            System.out.println(name);
        }
    }

    // 根据id 查询年龄
    @Test
    public void selectAgeById() throws Exception {
        int age = personDao.selectAgeById(1);
        System.out.println(age);
    }

   /* // 根据 name 查询年龄
    @Test
    public void selectAgeByName() throws Exception {
        int age = personDao.selectAgeByName("tom");
        System.out.println(age);
    }*/

    // 根据 name 查询年龄
    @Test
    public void selectAgeByName() throws Exception {
        int age = personDao.selectAgeById("tom");
        System.out.println(age);
    }

    // 分页查询
    @Test
    public void selectListByCondition() throws Exception {
        int page = 3;
        int pageSize = 5;

        PersonBO personBO = new PersonBO();
        personBO.setPage((page - 1) * pageSize);
        personBO.setPageSize(pageSize);

        List<Person> persons = personDao.selectListByCondition(personBO);

        Long count = personDao.selectCountByCondition(personBO);

        System.out.println(persons);
        System.out.println(count);

        Page<Person> pages = new Page<>(page);
        pages.setList(persons);
        pages.setTotalItemNumber(count);

        System.out.println("当前页码:" + pages.getPageNo());
        System.out.println("当前页数据:" + pages.getList());
        System.out.println("总页数:" + pages.getTotalPageNumber());
        System.out.println("每页长度:" + pages.getPageSize());








    }



}
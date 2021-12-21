package com.ykl.test;

import com.ykl.dao.PersonDao;
import com.langlang.io.Resources;
import com.ykl.dao.PersonDaoImpl;
import com.ykl.pojo.Person;
import com.langlang.sqlSession.SqlSession;
import com.langlang.sqlSession.SqlSessionFactory;
import com.langlang.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * @author langlang.ye
 * @date 2021/5/16
 */
public class TigaTest {

    @Test
    public void testImpl() throws Exception {
        /*PersonDaoImpl personDao = new PersonDaoImpl();
        List<Person> all = personDao.findAll();
        for (Person person : all) {
            System.out.println(person);
        }*/

        /*PersonDaoImpl personDao = new PersonDaoImpl();
        Person p = new Person();
        p.setId(2);
        p.setName("tom");
        Person person = personDao.findByCondition(p);
        System.out.println(person);*/

       /* PersonDaoImpl personDao = new PersonDaoImpl();
        Person p = new Person();
        p.setId(5);
        p.setName("Tuffy");
        p.setAge(25);
        p.setRemark("...");
//        personDao.savePerson(p);
        p.setName("Nibbles");
        personDao.updatePerson(p);
*/
       /* PersonDaoImpl personDao = new PersonDaoImpl();
        Person p = new Person();
        p.setId(5);
        personDao.deletePerson(p);*/

    }

    @Test
    public void test() throws Exception{
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        PersonDao personDao = sqlSession.getMapper(PersonDao.class);
        /*List<Person> all = personDao.findAll();
        for (Person dog : all) {
            System.out.println(dog);
        }*/

        /*Person p = new Person();
        p.setId(2);
        p.setName("tom");
        Person peron = personDao.findByCondition(p);
        System.out.println(peron);*/

        Person p = new Person();
        p.setId(5);
        p.setName("Tuffy");
        p.setAge(5);
        p.setRemark("...");
//        personDao.savePerson(p);
//        p.setName("Nibbles");
//        personDao.updatePerson(p);
        personDao.deletePerson(p);

    }
}

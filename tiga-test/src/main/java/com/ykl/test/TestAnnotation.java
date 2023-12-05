package com.ykl.test;

import com.langlang.io.Resources;
import com.langlang.sqlSession.SqlSessionFactoryBuilder;
import com.ykl.dao.StarDao;
import com.ykl.pojo.Star;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class TestAnnotation {

    StarDao stuDao;

    @Before
    public void testBefore() throws Exception{
        var resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        var sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        var sqlSession = sqlSessionFactory.openSession();
        stuDao = sqlSession.getMapper(StarDao.class);
    }

    @Test
    public void testSave() throws Exception{
        Star star = new Star();
        star.setId(3);
        star.setName("62e");
        star.setCreated(new Date());

        int insert = stuDao.insert(star);
        System.out.println(insert);
    }

    @Test
    public void testSelect() throws Exception{
        Star star = stuDao.select(1);
        System.out.println(star);
    }

    @Test
    public void testUpdate() throws Exception {
        Star star = new Star();
        star.setId(1);
        star.setName("62C");

        int update = stuDao.update(star);
        System.out.println(update);
    }

    @Test
    public void testDelete() throws Exception {
        Star star = new Star();
        star.setId(2);

        int delete = stuDao.delete(star);
        System.out.println(delete);
    }


    //
    @Test
    public void testFindAll() {
        List<Star> all = stuDao.findAll();
        for (Star star : all) {
            System.out.println(star);
        }
    }
}

package dao;

import com.duyi.onlinevideo.dao.CourseTypeDao;
import com.duyi.onlinevideo.entity.CourseType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-*.xml"})
public class DaoTest {

    @Autowired
    CourseTypeDao courseTypeDao;

    @Test
    public void t1() {

        CourseType courseType = new CourseType();
        courseType.setFlag(1);
        courseType.setName("数据库优化");

        int code = courseTypeDao.insertCourseType(courseType);
        System.out.println(code);
    }
}

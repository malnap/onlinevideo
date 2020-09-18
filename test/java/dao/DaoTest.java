package dao;

import com.duyi.onlinevideo.dao.*;
import com.duyi.onlinevideo.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.ResponseBody;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-*.xml"})
public class DaoTest {

    @Autowired
    CourseTypeDao courseTypeDao;

    @Autowired
    BannerDao bannerDao;

    @Autowired
    CourseTopicDao courseTopicDao;

    @Autowired
    CourseVideoDao courseVideoDao;

    @Autowired
    ToolsItemDao toolsItemDao;

    @Autowired
    ToolsTypeDao toolsTypeDao;

    @Autowired
    UserDao userDao;

    @Test
    public void t1() {
        CourseType courseType = new CourseType();
        courseType.setFlag(1);
        courseType.setName("数据库优化");

        int code = courseTypeDao.insertCourseType(courseType);
        System.out.println(code);
    }

    @Test
    public void t2() {
        Banner banner = new Banner();
        banner.setImgUrl("xxx");
        int i = bannerDao.insertBanner(banner);
        System.out.println(i);
    }

    @Test
    public void t3() {
        CourseTopic courseTopic = new CourseTopic();
        courseTopic.setIntroUrl("about spring");
        int i = courseTopicDao.insertCourseTopic(courseTopic);
        System.out.println(i);
    }

    @Test
    public void t4() {
        CourseVideo courseVideo = new CourseVideo();
        courseVideo.setFreeView(1);
        int i = courseVideoDao.insertCourseVideo(courseVideo);
        System.out.println(i);
    }

    @Test
    public void t5() {
        ToolsItem toolsItem = new ToolsItem();
        toolsItem.setName("apple");
        int i = toolsItemDao.insertToolsItem(toolsItem);
        System.out.println(i);
    }

    @Test
    public void t6() {
        ToolsType toolsType = new ToolsType();
        toolsType.setName("aaa");
        int i = toolsTypeDao.insertToolsType(toolsType);
        System.out.println(i);
    }

    @Test
    public void t7() {
        User user = new User();
        user.setUsername("mal");
        int i = userDao.insertUser(user);
        System.out.println(i);
    }
}

package com.duyi.onlinevideo.dao;

import com.duyi.onlinevideo.entity.CourseTopic;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface CourseTopicDao {

    int insertCourseTopic(CourseTopic courseTopic);

    List<CourseTopic> findCourseTopicByIds(List<Integer> ids);

    List<CourseTopic> findCourseTopicByCondition(HashMap<String, Object> map);
}

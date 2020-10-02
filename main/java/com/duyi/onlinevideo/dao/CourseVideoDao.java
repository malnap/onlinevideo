package com.duyi.onlinevideo.dao;

import com.duyi.onlinevideo.entity.CourseVideo;

import java.util.HashMap;
import java.util.List;

public interface CourseVideoDao {

    int insertCourseVideo(CourseVideo CourseVideo);

    List<CourseVideo> findCourseVideoByCondition(HashMap<String, Object> map);

    CourseVideo findCourseVideoByIds(List<Integer> ids);
}

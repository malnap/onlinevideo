package com.duyi.onlinevideo.dao;

import com.duyi.onlinevideo.entity.CourseType;

import java.util.List;

public interface CourseTypeDao {

    int insertCourseType(CourseType courseType);

    List<CourseType> findCourseTypeAll();
}

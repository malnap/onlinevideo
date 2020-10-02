package com.duyi.onlinevideo.service;

import com.duyi.onlinevideo.entity.CourseType;

import java.util.List;

public interface CourseTypeService {

    int insertCourseType(CourseType courseType);

    List<CourseType> getCourseTypeAll();

}

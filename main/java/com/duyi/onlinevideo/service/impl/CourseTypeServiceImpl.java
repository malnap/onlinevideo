package com.duyi.onlinevideo.service.impl;

import com.duyi.onlinevideo.dao.CourseTypeDao;
import com.duyi.onlinevideo.entity.CourseType;
import com.duyi.onlinevideo.service.CourseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseTypeServiceImpl implements CourseTypeService {

    @Autowired
    CourseTypeDao courseTypeDao;

    @Override
    public int insertCourseType(CourseType courseType) {
        return courseTypeDao.insertCourseType(courseType);
    }

    @Override
    public List<CourseType> getCourseTypeAll() {
        return courseTypeDao.findCourseTypeAll();
    }
}

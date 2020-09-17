package com.duyi.onlinevideo.controller;

import com.duyi.onlinevideo.entity.CourseType;
import com.duyi.onlinevideo.service.CourseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @Autowired
    CourseTypeService courseTypeService;

    @RequestMapping("/test")
    public String test(){

        CourseType courseType = new CourseType();
        courseType.setFlag(1);
        courseType.setName("数据库优化test");
        courseTypeService.insertCourseType(courseType);

        return "test";
    }
}

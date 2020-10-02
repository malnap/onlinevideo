package com.duyi.onlinevideo.service.impl;

import com.duyi.onlinevideo.dao.CourseTopicDao;
import com.duyi.onlinevideo.entity.CourseTopic;
import com.duyi.onlinevideo.service.CourseTopicService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CourseTopicServiceImpl implements CourseTopicService {

    private  CourseTopicDao courseTopicDao;

    @Autowired
    public CourseTopicServiceImpl(CourseTopicDao courseTopicDao) {
        this.courseTopicDao = courseTopicDao;
    }

    @Override
    public PageInfo<CourseTopic> getIndexCourseList(int typeId) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("typeId", typeId);
        map.put("flag", 1);
        List<CourseTopic> list = courseTopicDao.findCourseTopicByCondition(map);

        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<CourseTopic> getIndexNewestList() {

        HashMap<String, Object> map = new HashMap<>();
        /* order == 1 desc */
        map.put("order", 1);
        map.put("flag", 1);
        List<CourseTopic> list = courseTopicDao.findCourseTopicByCondition(map);

        return new PageInfo<>(list);
    }

    @Override
    public CourseTopic getCourseTopic(int topicId) {
        List<Integer> ids = new ArrayList<>();
        ids.add(topicId);
        List<CourseTopic> list = courseTopicDao.findCourseTopicByIds(ids);
        return list.get(0);
    }
}

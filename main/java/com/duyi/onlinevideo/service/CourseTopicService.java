package com.duyi.onlinevideo.service;

import com.duyi.onlinevideo.entity.CourseTopic;
import com.github.pagehelper.PageInfo;

public interface CourseTopicService {

    /**
     * 根据类型搜索课程
     * @param typeId 课程类型id
     * @return PageInfo<CourseTopic>
     */
    PageInfo<CourseTopic> getIndexCourseList(int typeId);

    /**
     * 获取最新课程,按照时间降序排序
     * @return PageInfo<CourseTopic>
     */
    PageInfo<CourseTopic> getIndexNewestList();

}

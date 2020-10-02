package com.duyi.onlinevideo.service;

import com.duyi.onlinevideo.entity.CourseVideo;

import java.util.List;

public interface CourseVideoService {
    List<CourseVideo> getCourseVideoAll(int topicId);

    CourseVideo getCourseVideo(int vid);
}

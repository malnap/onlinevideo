package com.duyi.onlinevideo.controller;

import com.duyi.onlinevideo.entity.CourseTopic;
import com.duyi.onlinevideo.entity.CourseVideo;
import com.duyi.onlinevideo.service.CourseTopicService;
import com.duyi.onlinevideo.service.CourseVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TopicController {


    @Autowired
    CourseVideoService courseVideoService;

    @Autowired
    CourseTopicService courseTopicService;

    // 专题页面
    @RequestMapping(value = "/topic/{topicId}")
    public String topicPage(@PathVariable Integer topicId, Model model) {
        model.addAttribute("focusIndex", 2);
        model.addAttribute("topicId", topicId);

        CourseTopic courseTopic = courseTopicService.getCourseTopic(topicId);
        model.addAttribute("courseTopic", courseTopic);

        List<CourseVideo> courseVideoList = courseVideoService.getCourseVideoAll(topicId);
        model.addAttribute("courseVideoList", courseVideoList);

        model.addAttribute("video", courseVideoList.get(0));

        return "course_video";
    }

    // 播放某一集视频页面 http://localhost:8080/topic/71/129
    @RequestMapping(value = "/topic/{topicId}/{videoId}")
    public String topicPage(@PathVariable Integer topicId, @PathVariable Integer videoId, Model model
            , HttpSession session) {
        model.addAttribute("focusIndex", 2);
        model.addAttribute("topicId", topicId);

        CourseTopic courseTopic = courseTopicService.getCourseTopic(topicId);
        model.addAttribute("courseTopic", courseTopic);

        List<CourseVideo> courseVideoList = courseVideoService.getCourseVideoAll(topicId);
        model.addAttribute("courseVideoList", courseVideoList);

        CourseVideo video = courseVideoService.getCourseVideo(videoId);
        model.addAttribute("video", video);

        return "course_video";
    }
}

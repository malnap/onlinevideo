package com.duyi.onlinevideo.controller;

import com.duyi.onlinevideo.entity.Banner;
import com.duyi.onlinevideo.entity.CourseTopic;
import com.duyi.onlinevideo.service.BannerService;
import com.duyi.onlinevideo.service.CourseTopicService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PageController {

    @Autowired
    private CourseTopicService courseTopicService;

    @Autowired
    private BannerService bannerService;

    @RequestMapping("/")
    public String indexPage(Model model) {
        /* 设置导航条当前焦点位置 */
        model.addAttribute("focusIndex", 1);

        /* 获取首页数据 */

        /* 获取banner */
        List<Banner> bannerList = bannerService.getIndexBanner();

        /* 获取课程 */
        /* 设置分页,第几页,显示几个 */
        PageHelper.startPage(1, 4);
        PageInfo<CourseTopic> newestTopicList = courseTopicService.getIndexNewestList();

        PageHelper.startPage(1, 4);
        PageInfo<CourseTopic> courseTopicList = courseTopicService.getIndexCourseList(3);

        model.addAttribute("bannerList", bannerList);
        model.addAttribute("newestTopicList", newestTopicList);
        model.addAttribute("courseTopicList", courseTopicList);

        return "index";
    }

    /**
     * 课程列表,显示所有分类
     */
    @RequestMapping(value = "/courseList")
    public String courseList(Model model) {

        model.addAttribute("focusIndex", 2);

        model.addAttribute("typeId",0);

        PageHelper.startPage(1, 16);
        /* 最新课程 */
        PageInfo<CourseTopic> newestTopicList = courseTopicService.getIndexNewestList();
        model.addAttribute("topicList", newestTopicList);
        return "course_list";
    }

    @RequestMapping("/courseList/type/{typeId}")
    public String courseList(@PathVariable Integer typeId, Integer pageNum, Model model) {
        model.addAttribute("typeId", typeId);

        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }

        PageHelper.startPage(1, 16);

        /* typeId为0对应最新专题,否则对应类型专题 */
        PageInfo<CourseTopic> courseTopicList = (typeId == 0) ? courseTopicService.getIndexNewestList()
                : courseTopicService.getIndexCourseList(typeId);

        model.addAttribute("topicList", courseTopicList);
        return "course_list";
    }

    /* 会员 */
    @RequestMapping("/vip")
    public String vip(Model model) {
        model.addAttribute("focusIndex", 3);
        return "vip";
    }

    @RequestMapping("live")
    public String live(Model model) {
        model.addAttribute("focusIndex", 4);
        return "redirect:http://www.huya.com";
    }

    /* 工具 */
    @RequestMapping(value = "/tools")
    public String toolsPage(Model model) {
        model.addAttribute("focusIndex", 5);
        return "tools";
    }

    @RequestMapping("courseVideo")
    public String courseVideo() {
        return "course_video";
    }
}

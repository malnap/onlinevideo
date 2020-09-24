package com.duyi.onlinevideo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/")
    public String indexPage() {
        return "index";
    }

    /* 会员 */
    @RequestMapping("/vip")
    public String vip() {
        return "vip";
    }

    /* 课程列表 */
    @RequestMapping(value = "/courseList")
    public String courseList() {
        return "course_list";
    }

    /* 工具 */
    @RequestMapping(value = "/tools")
    public String toolsPage() {
        return "tools";
    }
}

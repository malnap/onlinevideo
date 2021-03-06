package com.duyi.onlinevideo.entity;

import java.util.Date;

/**
 * 课程专题
 */
public class CourseTopic implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -2234628756139393659L;

    /** 课程专题id */
    private Integer id;

    /** 课程专题的标题 */
    private String title;

    /** 课程图片 */
    private String iconUrl;

    /** 简介url */
    private String introUrl;

    /** 专题简介 */
    private String topicIntro;

    /** 课程浏览次数 */
    private Integer views;

    /** 课程状态 */
    private Integer flag;

    /** 课程分类 */
    private Integer typeId;

    /** vip会议 */
    private Integer vipFlag;

    /** 课件下载url */
    private String coursewareUrl;

    /** 创建时间 */
    private Date createTime;

    /**
     * 获取id
     *
     * @return id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置id
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取title
     *
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * 设置title
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取iconUrl
     *
     * @return iconUrl
     */
    public String getIconUrl() {
        return this.iconUrl;
    }

    /**
     * 设置iconUrl
     *
     * @param iconUrl
     */
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    /**
     * 获取introUrl
     *
     * @return introUrl
     */
    public String getIntroUrl() {
        return this.introUrl;
    }

    /**
     * 设置introUrl
     *
     * @param introUrl
     */
    public void setIntroUrl(String introUrl) {
        this.introUrl = introUrl;
    }

    /**
     * 获取topicIntro
     *
     * @return topicIntro
     */
    public String getTopicIntro() {
        return this.topicIntro;
    }

    /**
     * 设置topicIntro
     *
     * @param topicIntro
     */
    public void setTopicIntro(String topicIntro) {
        this.topicIntro = topicIntro;
    }

    /**
     * 获取views
     *
     * @return views
     */
    public Integer getViews() {
        return this.views;
    }

    /**
     * 设置views
     *
     * @param views
     */
    public void setViews(Integer views) {
        this.views = views;
    }

    /**
     * 获取flag
     *
     * @return flag
     */
    public Integer getFlag() {
        return this.flag;
    }

    /**
     * 设置flag
     *
     * @param flag
     */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    /**
     * 获取typeId
     *
     * @return typeId
     */
    public Integer getTypeId() {
        return this.typeId;
    }

    /**
     * 设置typeId
     *
     * @param typeId
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    /**
     * 获取vipFlag
     *
     * @return vipFlag
     */
    public Integer getVipFlag() {
        return this.vipFlag;
    }

    /**
     * 设置vipFlag
     *
     * @param vipFlag
     */
    public void setVipFlag(Integer vipFlag) {
        this.vipFlag = vipFlag;
    }

    /**
     * 获取coursewareUrl
     *
     * @return coursewareUrl
     */
    public String getCoursewareUrl() {
        return this.coursewareUrl;
    }

    /**
     * 设置coursewareUrl
     *
     * @param coursewareUrl
     */
    public void setCoursewareUrl(String coursewareUrl) {
        this.coursewareUrl = coursewareUrl;
    }

    /**
     * 获取createTime
     *
     * @return createTime
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置createTime
     *
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
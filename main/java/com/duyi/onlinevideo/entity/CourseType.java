package com.duyi.onlinevideo.entity;

/**
 * course_type
 * 
 * @author bianj
 * @version 1.0.0 2020-09-16
 */
public class CourseType implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -3660126451844249962L;

    /** 课程id */
    private Integer id;

    /** 课程类型名 */
    private String name;

    /** flag */
    private Integer flag;

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
     * 获取name
     * 
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置name
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
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

}
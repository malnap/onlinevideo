package com.duyi.onlinevideo.entity;

/**
 * 课程类型
 */
public class CourseType implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -3660126451844249962L;

    /** 课程id */
    private Integer id;

    /** 类目名 */
    private String name;

    /** 类目状态 */
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
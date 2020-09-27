package com.duyi.onlinevideo.entity;

/**
 * 工具类型
 */
public class ToolsType implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -2735390392853496964L;

    /** id */
    private Integer id;

    /** 名称 */
    private String name;

    /** 状态 */
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
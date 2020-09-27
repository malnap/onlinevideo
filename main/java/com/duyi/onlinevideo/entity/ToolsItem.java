package com.duyi.onlinevideo.entity;

/**
 * 工具表
 */
public class ToolsItem implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 5395125227466483893L;

    /** id */
    private Integer id;

    /** 工具名称 */
    private String name;

    /** 地址url */
    private String toolsUrl;

    /** icon图片url  */
    private String iconUrl;

    /** 工具类型id */
    private Integer toolsTypeId;

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
     * 获取toolsUrl
     *
     * @return toolsUrl
     */
    public String getToolsUrl() {
        return this.toolsUrl;
    }

    /**
     * 设置toolsUrl
     *
     * @param toolsUrl
     */
    public void setToolsUrl(String toolsUrl) {
        this.toolsUrl = toolsUrl;
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
     * 获取toolsTypeId
     *
     * @return toolsTypeId
     */
    public Integer getToolsTypeId() {
        return this.toolsTypeId;
    }

    /**
     * 设置toolsTypeId
     *
     * @param toolsTypeId
     */
    public void setToolsTypeId(Integer toolsTypeId) {
        this.toolsTypeId = toolsTypeId;
    }
}
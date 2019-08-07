package com.boo.quartz.entity;

/**
 * @ClassName JobDetail
 * @Description 任务详情实体
 * @Author boo
 * @Date 2019/8/7 9:57
 * @Version 1.0.0
 */
public class JobDetail {
    /**
     * id
     */
    private Integer id;
    /**
     * 任务名称
     */
    private String name;
    /**
     * 任务分组
     */
    private String group;
    /**
     * 任务描述
     */
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

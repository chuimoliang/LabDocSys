package com.moliang.labdocsys.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zhang qing
 * @date 2022/3/12 22:43
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@TableName(value = "experiment")
public class Experiment {
    /**
     * 实验 id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 创建人id
     */
    @TableField(value = "create_id")
    private String createId;

    /**
     * 实验名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 实验要求
     */
    @TableField(value = "text")
    private String text;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 截止时间
     */
    @TableField(value = "end_time")
    private Date endTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;
}

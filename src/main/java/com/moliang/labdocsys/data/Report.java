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
 * @date 2022/3/12 22:48
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@TableName(value = "report")
public class Report {

    /**
     * 报告 id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 创建人 id
     */
    @TableField(value = "create_id")
    private Integer createId;

    /**
     * 实验 id
     */
    @TableField(value = "experiment_id")
    private Integer experimentId;

    /**
     * 报告名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 文件路径
     */
    @TableField(value = "file_path")
    private String filePath;

    /**
     * 文件信息
     */
    @TableField(value = "file_meta")
    private String fileMeta;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;
}

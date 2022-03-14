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
 * @date 2022/3/12 22:54
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@TableName(value = "mark")
public class Mark {

    /**
     * 记录 id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 报告 id
     */
    @TableField(value = "report_id")
    private Integer reportId;

    /**
     * 教师 id
     */
    @TableField(value = "teacher_id")
    private String teacherId;

    /**
     * 批阅内容
     */
    @TableField(value = "mark")
    private String mark;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

}

package com.moliang.labdocsys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moliang.labdocsys.aop.Teacher;
import com.moliang.labdocsys.data.DataPage;
import com.moliang.labdocsys.data.Experiment;
import com.moliang.labdocsys.data.ExperimentForm;
import com.moliang.labdocsys.mapper.ExperimentMapper;
import com.moliang.labdocsys.util.WebUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author zhang qing
 * @date 2022/3/13 10:22
 */
@Component
public class ExperimentService {

    @Resource
    private ExperimentMapper experimentMapper;

    /**
     * 创建实验
     */
    public Object createExperiment(HttpServletRequest req, ExperimentForm form) {
        Experiment experiment = Experiment.builder().createId(WebUtil.getUserId(req))
                .endTime(form.getEndTime())
                .name(form.getName())
                .text(form.getText())
                .build();
        return experimentMapper.insert(experiment);
    }

    /**
     * 查看实验
     */
    public Object list(boolean open, String createId, String name, int pageNum, int pageSize) {
        QueryWrapper<Experiment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        if (open) {
            queryWrapper.ge("end_time", new Date());
        }
        if (name != null) {
            queryWrapper.like("name", name);
        }
        if (createId != null) {
            queryWrapper.eq("create_id", createId);
        }
        IPage<Experiment> page = new Page<>(pageNum, pageSize);
        List<Experiment> list = experimentMapper.selectPage(page, queryWrapper).getRecords();
        return new DataPage<Experiment>(list, page);
    }

}

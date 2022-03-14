package com.moliang.labdocsys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moliang.labdocsys.aop.Teacher;
import com.moliang.labdocsys.data.Experiment;
import com.moliang.labdocsys.mapper.ExperimentMapper;
import com.moliang.labdocsys.util.WebUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
    public Object createExperiment(HttpServletRequest req, Date endTime, String name, String text) {
        Experiment experiment = Experiment.builder().createId(WebUtil.getUserId(req))
                .endTime(endTime)
                .name(name)
                .text(text)
                .build();
        return experimentMapper.insert(experiment);
    }

    /**
     * 查看实验
     */
    public Object getExperiments() {
        QueryWrapper<Experiment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        return experimentMapper.selectList(queryWrapper);
    }

}

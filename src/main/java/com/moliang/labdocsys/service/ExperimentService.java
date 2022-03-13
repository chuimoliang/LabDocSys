package com.moliang.labdocsys.service;

import com.moliang.labdocsys.mapper.ExperimentMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhang qing
 * @date 2022/3/13 10:22
 */
@Component
public class ExperimentService {

    @Resource
    private ExperimentMapper experimentMapper;



}

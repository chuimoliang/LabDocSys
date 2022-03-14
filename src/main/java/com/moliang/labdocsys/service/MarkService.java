package com.moliang.labdocsys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moliang.labdocsys.data.DataPage;
import com.moliang.labdocsys.data.Mark;
import com.moliang.labdocsys.mapper.MarkMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhang qing
 * @date 2022/3/13 17:09
 */
@Slf4j
@Component
public class MarkService {

    @Resource
    private MarkMapper markMapper;

    public int createMark(int id, String content, String userId) {
        Mark mark = Mark.builder().content(content)
                .reportId(id)
                .teacherId(userId)
                .build();
        return markMapper.insert(mark);
    }

    public DataPage<Mark> list(int id) {
        QueryWrapper<Mark> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("report_id", id);
        List<Mark> list = markMapper.selectList(queryWrapper);
        return new DataPage<>(list, list.size());
    }

}

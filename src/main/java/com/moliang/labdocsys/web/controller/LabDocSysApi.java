package com.moliang.labdocsys.web.controller;

import cn.hutool.core.collection.CollUtil;
import com.moliang.labdocsys.aop.Teacher;
import com.moliang.labdocsys.config.Role;
import com.moliang.labdocsys.data.ExperimentForm;
import com.moliang.labdocsys.data.LoginForm;
import com.moliang.labdocsys.service.ExperimentService;
import com.moliang.labdocsys.service.MarkService;
import com.moliang.labdocsys.service.ReportService;
import com.moliang.labdocsys.service.UserAccountService;
import com.moliang.labdocsys.util.WebUtil;
import com.moliang.labdocsys.web.common.WebRespCode;
import com.moliang.labdocsys.web.common.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhang qing
 * @date 2022/3/13 10:22
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class LabDocSysApi {

    @Resource
    private ExperimentService experimentService;

    @Resource
    private ReportService reportService;

    @Resource
    private MarkService markService;

    @Resource
    private UserAccountService userAccountService;

    @PostMapping("user/login")
    public WebResponse login(@RequestBody LoginForm form) {
        String token = userAccountService.login(form.getUserId(), form.getPassword());
        if (token != null) {
            return WebResponse.success(token);
        }
        return WebResponse.fail(WebRespCode.PARAM_ERROR);
    }

    /**
     * 获取当前登录用户信息
     */
    @GetMapping(value = "user/info")
    public WebResponse getAdminInfo(HttpServletRequest request) {
        String username = WebUtil.getUserId(request);
        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("menus", userAccountService.getMenus(WebUtil.getRole(request)));
        data.put("icon", "");
        List<String> roles = new ArrayList<>(1);
        roles.add(WebUtil.getRole(request));
        data.put("roles",roles);
        return WebResponse.success(data);
    }

    @PostMapping("experiment/save")
    @Teacher
    public WebResponse createExperiment(@RequestBody ExperimentForm form, HttpServletRequest req) {
        log.info("userId: [{}]访问创建实验接口", WebUtil.getUserId(req));
        if (form == null || form.getName() == null || form.getText() == null || form.getEndTime() == null) {
            return WebResponse.fail(WebRespCode.PARAM_ERROR);
        }
        return WebResponse.success(experimentService.createExperiment(req, form));
    }

    /**
     *  参数:
     *    open boolean 是否只查找未到截止日期的
     *    createId String 创建人id
     *    name String 实验题目 模糊查询
     *    pageNum int 页码
     *    pageSize int 页大小
     */
    @GetMapping("experiment/list")
    public WebResponse list(@RequestParam(value = "open", defaultValue = "false") boolean open,
                            @RequestParam(value = "createId", required = false) String createId,
                            @RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                            HttpServletRequest request) {
        log.info("userId: [{}]访问查找实验接口", WebUtil.getUserId(request));
        return WebResponse.success(experimentService.list(open, createId, name, pageNum, pageSize));
    }

    @GetMapping("experiment/info")
    public WebResponse experimentInfo(@RequestParam(value = "id", defaultValue = "false") int id,
                            HttpServletRequest request) {
        log.info("userId: [{}]访问获取实验接口", WebUtil.getUserId(request));
        return WebResponse.success(experimentService.get(id));
    }

    /**
     *    根据实验分类, 老师可查看自己开设实验下的报告
     *    学生可查看自己提交的报告
     *    id int 实验id
     *    name String 实验题目 模糊查询
     *    pageNum int 页码
     *    pageSize int 页大小
     */
    @GetMapping("report/listT")
    public WebResponse reportListT(@RequestParam(value = "id") int id,
                                   @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return WebResponse.success(reportService.listT(id, pageNum, pageSize));
    }

    @GetMapping("report/listS")
    public WebResponse reportListS(@RequestParam(value = "id", defaultValue = "-1") int id,
                                   @RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "userId", required = false) String userId,
                                   @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                   HttpServletRequest request) {
        String user = WebUtil.getUserId(request);
        log.info("userId:[{}]查找报告数据", user);
        return WebResponse.success(reportService.listS(id, name, userId, pageNum, pageSize));
    }

    /**
     * 提交报告
     */
    @PostMapping("report/submit")
    public WebResponse submitReport(@RequestParam(value = "file") MultipartFile file,
                                    @RequestParam(value = "id") int id,
                                    HttpServletRequest request) {
        String userId = WebUtil.getUserId(request);
        if (reportService.save(id, file, userId) > 0) {
            return WebResponse.success();
        }
        return WebResponse.fail(WebRespCode.BAD_REQUEST);
    }

    /**
     * 下载报告
     */
    @GetMapping("report/download")
    public WebResponse downloadReport(@RequestParam(value = "id") String ids,
                                      HttpServletResponse response) {
        return WebResponse.success(reportService.download(ids, response));
    }

    /**
     * 批阅实验报告
     */
    @Teacher
    @PostMapping("mark/create")
    public WebResponse markReport(@RequestParam(value = "id") int id,
                                  @RequestParam(value = "mark") String mark,
                                  HttpServletRequest request) {
        String userId = WebUtil.getUserId(request);
        if (markService.createMark(id, mark, userId) > 0) {
            return WebResponse.success();
        }
        return WebResponse.fail(WebRespCode.BAD_REQUEST);
    }

    /**
     * 查看报告批阅
     */
    @GetMapping("mark/list")
    public WebResponse markList(@RequestParam(value = "id") int id) {
        return WebResponse.success(markService.list(id));
    }

}

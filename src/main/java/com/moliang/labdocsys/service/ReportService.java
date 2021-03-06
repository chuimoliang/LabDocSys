package com.moliang.labdocsys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moliang.labdocsys.config.Config;
import com.moliang.labdocsys.data.DataPage;
import com.moliang.labdocsys.data.Report;
import com.moliang.labdocsys.mapper.ReportMapper;
import com.moliang.labdocsys.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author zhang qing
 * @date 2022/3/13 17:09
 */
@Slf4j
@Component
public class ReportService {

    @Resource
    private ReportMapper reportMapper;

    public DataPage<Report> listT(int id, int pageNum, int pageSize) {
        QueryWrapper<Report> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("experiment_id", id);
        IPage<Report> page = new Page<>(pageNum, pageSize);
        return new DataPage<>(reportMapper.selectPage(page, queryWrapper).getRecords(), page);
    }

    public DataPage<Report> listS(int id, String name, String userId, int pageNum, int pageSize) {
        QueryWrapper<Report> queryWrapper = new QueryWrapper<>();
        if (id != -1) {
            queryWrapper.eq("experiment_id", id);
        }
        if (name != null) {
            queryWrapper.like("name", name);
        }
        if (userId != null) {
            queryWrapper.eq("create_id", userId);
        }
        queryWrapper.orderByDesc("create_time");
        IPage<Report> page = new Page<>(pageNum, pageSize);
        return new DataPage<>(reportMapper.selectPage(page, queryWrapper).getRecords(), page);
    }

    public int save(int id, MultipartFile file, String userId) {
        File uuid = new File(Config.docPath, UUID.randomUUID() + "/" + file.getOriginalFilename());
        if (!uuid.getParentFile().exists()) {
            uuid.getParentFile().mkdirs();
        }
        try {
            file.transferTo(uuid);
        } catch (IOException e) {
            log.error("??????????????????", e);
            return -1;
        }
        Report report = Report.builder().createId(userId)
                .name(file.getOriginalFilename())
                .experimentId(id)
                .fileMeta(String.valueOf(file.getSize()))
                .filePath(uuid.getPath())
                .build();
        return reportMapper.insert(report);
    }

    public String download(String ids, HttpServletResponse response) {
        // ????????????????????????
        String[] idStr = ids.split(",");
        List<String> paths = new ArrayList<>(idStr.length);
        for (String id : idStr) {
            paths.add(reportMapper.selectById(Integer.valueOf(id)).getFilePath());
        }
        if (paths.size() == 1) {
            // ????????????
            File file = new File(paths.get(0));
            String s = writeFile(response, file, "application/octet-stream");
            if (s != null) {
                return s;
            }
        }
        // tmp?????????uuid?????????
        File uuid = new File(Config.tempPath , String.valueOf(UUID.randomUUID()));
        File root = new File(uuid.getPath());
        if (!root.exists()) {
            root.mkdirs();
        }
        // ???????????????uuid?????????
        paths.forEach(e -> {
            File source = new File(e);
            try {
                FileUtils.copyFile(source, new File(root.getPath() + source.getName()));
            } catch (IOException ex) {
                log.error("?????????????????????,[{}]", e);
            }
        });
        // ???????????????
        File zipFile = new File(root.getParent(), root.getName() + ".zip");
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            zip(zos, root, root.getName());
        } catch (IOException e) {
            log.error("compress error", e);
            return e.getMessage();
        }
        String s = writeFile(response, zipFile, "application/x-zip-compressed");
        if (s != null) {
            return s;
        }
        // ??????uuid??????
        if (!deleteFile(uuid)) {
            log.error("delete dir [{}] fail", uuid.getPath());
        }
        return null;
    }

    private String writeFile(HttpServletResponse response, File file, String contentType) {
        // ????????????
        try(OutputStream out = response.getOutputStream(); FileInputStream in = new FileInputStream(file);) {
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(file.getName(), "UTF-8"));
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
        } catch (IOException e) {
            log.error("write zip in response error", e);
            return e.getMessage();
        }
        return null;
    }

    private void zip(ZipOutputStream zos, File file, String path) throws IOException {
        // ????????????????????????????????????????????????????????????????????????????????????????????????
        if (file.isDirectory()) {
            ZipEntry entry = new ZipEntry(path + File.separator);// ?????????????????????????????????????????????????????????
            zos.putNextEntry(entry);
            File[] files = file.listFiles();
            for (File x : files) {
                zip(zos, x, path + File.separator + x.getName());
            }
        } else {
            FileInputStream fis = new FileInputStream(file);// ????????????????????????????????????????????????????????????
            ZipEntry entry = new ZipEntry(path);
            zos.putNextEntry(entry);// ???????????????????????????
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = fis.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            zos.flush();
            fis.close();
            zos.closeEntry();// ????????????????????????????????????????????????????????????????????????
        }
    }

    private boolean deleteFile(File file) {
        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            return file.exists() && file.isFile() && file.delete();
        } else {
            boolean flag = true;
            File[] files = file.listFiles();
            for (File f : files) {
                flag &= deleteFile(f);
            }
            flag &= file.delete();
            return flag;
        }
    }

}

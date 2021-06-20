package com.myapp.task.manage.board.attach;

import com.myapp.task.common.validation.TaskValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AttachServiceImpl implements AttachService {
    private static final Logger logger;

    static {
        logger = LoggerFactory.getLogger(AttachServiceImpl.class);
    }

    @Value("${file.upload.path}")
    private String fileUploadPath;

    @Autowired
    private AttachRepository attachRepository;

    @Autowired
    private TaskValidationService taskValidationService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<AttachVO> uploadAttachFile(MultipartFile[] files) throws IOException {
        if(this.taskValidationService.isNull(files)) return null;

        List<AttachVO> attachList = new ArrayList<>();

        for(MultipartFile file : files) {
            String oriFileName = file.getOriginalFilename();
            String realFileName = this.getUUIDFileName() + "." + this.getFileExtension(oriFileName);
            String path = fileUploadPath + new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()) + File.separator;

            if(!this.makeDirectory(path)) throw new IOException("DIR CREATE FAIL : " + oriFileName);

            try (OutputStream out = new FileOutputStream(path + realFileName)) {
                out.write(file.getBytes());
                out.flush();
            } catch (IOException e) {
                logger.error("FILE UPLOAD ERROR : ", e);
                throw new IOException("FILE UPLOAD ERROR : + " + oriFileName);
            }

            AttachVO attach = new AttachVO();
            attach.setOriFileName(oriFileName);
            attach.setRealFilePath(path + realFileName);
            attach.setUploadDate(new Date());

            this.attachRepository.save(attach);

            attachList.add(attach);
        }
        return attachList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAttachFile(Long attachNo) {
        AttachVO attach = this.attachRepository.findByAttachNo(attachNo);
        if(this.taskValidationService.isNull(attach)) return false;

        File file = new File(attach.getRealFilePath());

        if(!file.delete()) return false;

        this.attachRepository.delete(attach);

        return true;
    }

    private String getFileExtension(final String fileName) {
        if(this.taskValidationService.isNull(fileName)) return "tmp";
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    private boolean makeDirectory(String path) {
        if(this.taskValidationService.isNull(path)) return false;

        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            logger.error("DIR CREATE ERROR : ", e);
            return false;
        }

        return true;
    }

    private String getUUIDFileName() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAttachFiles(Long noticeNo) {
        List<AttachVO> attachList = this.attachRepository.findAllByNoticeNo(noticeNo);

        for(AttachVO attach : attachList) {
            if(!this.deleteAttachFile(attach.getAttachNo())) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        }

        return true;
    }

    @Override
    public void updateNoticeNo(Long attachNo, Long noticeNo) {
        AttachVO attach = this.attachRepository.findByAttachNo(attachNo);
        attach.setNoticeNo(noticeNo);

        this.attachRepository.save(attach);
    }

    @Override
    public void attachFileDownload(Long attachNo, HttpServletResponse response) throws Exception {
        AttachVO attach = this.attachRepository.findByAttachNo(attachNo);
        if(this.taskValidationService.isNull(attach)) return;

        setDownloadResponse(response, attach.getOriFileName());

        InputStream in = new FileInputStream(attach.getRealFilePath());
        OutputStream os = response.getOutputStream();

        FileCopyUtils.copy(in, os);
    }

    private void setDownloadResponse(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");

        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment; filename=" + encodedFileName + ";");
    }
}

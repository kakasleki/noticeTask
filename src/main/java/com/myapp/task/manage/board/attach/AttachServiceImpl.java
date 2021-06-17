package com.myapp.task.manage.board.attach;

import com.myapp.task.common.validation.TaskValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Service
public class AttachServiceImpl implements AttachService {
    @Value("${file.upload.path}")
    private String fileUploadPath;

    @Autowired
    private AttachRepository attachRepository;

    @Autowired
    private TaskValidationService taskValidationService;

    @Override
    public boolean uploadAttachFile(Long noticeNo, MultipartFile[] files) {

        for(MultipartFile file : files) {
            String oriFileName = file.getOriginalFilename();
            String realFileName = UUID.randomUUID().toString() + "." + this.getFileExtension(oriFileName);
            String path = fileUploadPath + new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()) + File.separator;

            AttachVO attach = new AttachVO();
            attach.setNoticeNo(noticeNo);
            attach.setOriFileName(oriFileName);
            attach.setRealFilePath(fileUploadPath + realFileName);
            attach.setUploadDate(new Date());

            this.attachRepository.save(attach);
        }

        return false;
    }

    @Override
    public boolean deleteAttacheFile(Long attachNo) {
        AttachVO attach = this.attachRepository.findByAttachNo(attachNo);
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

        File dir = new File(path);
        if(!dir.exists()) return dir.mkdirs();
        return false;
    }
}

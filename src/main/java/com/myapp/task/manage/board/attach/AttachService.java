package com.myapp.task.manage.board.attach;

import org.springframework.web.multipart.MultipartFile;

public interface AttachService {
    boolean uploadAttachFile(Long noticeNo, MultipartFile[] files);

    boolean deleteAttacheFile(Long attachNo);
}

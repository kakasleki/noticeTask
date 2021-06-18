package com.myapp.task.manage.board.attach;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AttachService {
    boolean uploadAttachFile(Long noticeNo, MultipartFile[] files) throws IOException;

    boolean deleteAttachFile(Long attachNo);

    boolean deleteAttachFiles(Long noticeNo);
}

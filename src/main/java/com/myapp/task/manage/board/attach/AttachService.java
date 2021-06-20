package com.myapp.task.manage.board.attach;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface AttachService {
    List<AttachVO> uploadAttachFile(MultipartFile[] files) throws IOException;

    boolean deleteAttachFile(Long attachNo);

    boolean deleteAttachFiles(Long noticeNo);

    void updateNoticeNo(Long attachNo, Long noticeNo);

    void attachFileDownload(Long attachNo, HttpServletResponse response) throws Exception;
}

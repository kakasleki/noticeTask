package com.myapp.task.manage.board.attach;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachRepository extends JpaRepository<AttachVO, Long> {
    AttachVO findByAttachNo(Long attachNo);
}

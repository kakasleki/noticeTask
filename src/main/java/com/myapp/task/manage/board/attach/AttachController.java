package com.myapp.task.manage.board.attach;

import com.myapp.task.common.cmmcode.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/board/attach")
public class AttachController {
	@Autowired
	private AttachService attachService;

	@DeleteMapping(value = "/attach/{attachNo}")
	public ResponseEntity<Map<String, Object>> deleteAttach(@PathVariable("attachNo") Long attachNo) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put(ResultCode.RESULT, this.attachService.deleteAttachFile(attachNo) ? ResultCode.SUCCESS : ResultCode.ERROR);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}

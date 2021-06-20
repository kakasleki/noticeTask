package com.myapp.task.manage.board.attach;

import com.myapp.task.common.cmmcode.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/board/attach")
public class AttachController {
	@Autowired
	private AttachService attachService;

	@PostMapping(value = "/attach")
	public ResponseEntity<List<AttachVO>> uploadAttach(@RequestParam("file") MultipartFile[] files) throws Exception {
		return new ResponseEntity<>(this.attachService.uploadAttachFile(files), HttpStatus.OK);
	}

	@DeleteMapping(value = "/attach/{attachNo}")
	public ResponseEntity<Map<String, Object>> deleteAttach(@PathVariable("attachNo") Long attachNo) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put(ResultCode.RESULT, this.attachService.deleteAttachFile(attachNo) ? ResultCode.SUCCESS : ResultCode.ERROR);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@GetMapping(value = "/attach/{attachNo}")
	public void getAttachFileDownload(@PathVariable("attachNo") Long attachNo, HttpServletResponse response) throws Exception {
		this.attachService.attachFileDownload(attachNo, response);
	}
}

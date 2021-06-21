package com.myapp.task.manage.board.attach;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
public class AttachTest {
	@Autowired
	private AttachService attachService;

	@Test
	public void test01_attachFileUpload() throws Exception {
		MultipartFile file1 = new MockMultipartFile("image1.jpg", new FileInputStream(new File("/Users/kakasleki/Downloads/OSH_3893.jpg")));
		MultipartFile file2 = new MockMultipartFile("image2.jpg", new FileInputStream(new File("/Users/kakasleki/Downloads/OSH_3893.jpg")));
		MultipartFile[] files = {
				file1,
				file2
		};

		List<AttachVO> attachList = this.attachService.uploadAttachFile(files);
		Assert.assertNotNull(attachList);

		for(AttachVO attach : attachList) {
			Assert.assertTrue(this.attachService.deleteAttachFile(attach.getAttachNo()));
		}
	}
}

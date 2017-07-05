package com.yzwy;

import com.yzwy.application.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CicserviceApplication.class)
public class CicserviceApplicationTests {
	@Autowired
	MessageService messageService;

	private MockMvc mvc;

	@Test
	public void contextLoads() {
		try {
			messageService.caclulateAllTemplet();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void generaJson() {
		try {
			messageService.generateTempletJson();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}

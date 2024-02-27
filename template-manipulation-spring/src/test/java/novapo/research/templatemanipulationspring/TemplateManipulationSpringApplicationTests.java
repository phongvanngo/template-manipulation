package novapo.research.templatemanipulationspring;

import novapo.research.templatemanipulationspring.service.KickoffTemplateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TemplateManipulationSpringApplicationTests {

	@Autowired
	private KickoffTemplateService kickoffTemplateService;

	@Test
	void contextLoads() {
	}

	@Test
	void editTemplate() {
		System.out.println("hello \u27A2");
		kickoffTemplateService.editTemplate();
	}

}

package net.leejjon.javawebservice;

import net.leejjon.javawebservice.controller.ExampleController;
import net.leejjon.javawebservice.service.ExampleService;
import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@Log4j
@RunWith(MockitoJUnitRunner.class)
public class ExampleControllerTest {
	@Mock
	private ExampleService exampleService;

	private ExampleController exampleController;
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		exampleService = new ExampleService();
		exampleController = new ExampleController(exampleService);
		mockMvc = standaloneSetup(exampleController).build();
	}

	@Test
	public void testGetGoodExample_success() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/api/v1/examples/good")).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
		assertEquals("{\"result\":\"result\"}", mvcResult.getResponse().getContentAsString());
	}

	@Test
	public void testGetBadExample_success() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/api/v1/examples/bad")).andReturn();
		assertEquals(500, mvcResult.getResponse().getStatus());
		assertTrue(mvcResult.getResponse().getContentAsString().contains("java.sql.SQLException"));
	}
}

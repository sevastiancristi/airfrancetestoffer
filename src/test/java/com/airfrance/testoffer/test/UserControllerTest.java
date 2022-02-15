package com.airfrance.testoffer.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.airfrance.testoffer.TestofferApplication;
import com.airfrance.testoffer.User;
import com.airfrance.testoffer.UserController;
import com.airfrance.testoffer.UserModelAssembler;
import com.airfrance.testoffer.UserRepository;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { UserControllerTest.class, TestofferApplication.class })
@WebAppConfiguration
public class UserControllerTest {

	@Mock
	UserController mockedUserController;

	@Mock
	UserRepository repository;

	@Mock
	UserModelAssembler assembler;

	MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private User stubUser;


	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		stubUser = new User("User1", new SimpleDateFormat("yyyy-MM-dd").parse("2003-01-01"), "France", "+3423",
				User.Gender.FEMALE);
		mockedUserController.setRepositoryAndAssembler(repository, assembler);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	public void applicationContextMockServletExists() {
		ServletContext servletContext = webApplicationContext.getServletContext();
		Assert.assertNotNull(servletContext);
		Assert.assertTrue(servletContext instanceof MockServletContext);
	}

	@Test
	public void getUsersExpectOk() throws Exception {
		List<User> stubList = Arrays.asList(new User[] {
				new User("User1", new SimpleDateFormat("yyyy-MM-dd").parse("2003-01-01"), "France", "+3423",
						User.Gender.FEMALE),
				new User("User2", new SimpleDateFormat("yyyy-MM-dd").parse("2002-01-01"), "France", "+32421",
						User.Gender.MALE) });
		when(repository.findAll()).thenReturn(stubList);
		mockMvc.perform(get("/users")).andExpect(status().isOk()).andExpect(content().contentType(MediaTypes.HAL_JSON));
	}

	@Test
	public void getUserExpectOk() throws Exception {
		when(repository.findById(Mockito.any())).thenReturn(Optional.of(stubUser));
		mockMvc.perform(get("/user/1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaTypes.HAL_JSON));
	}

	@Test
	public void getUserExpectErrorWhenUserNotFound() throws Exception {
		when(repository.findById(Mockito.anyLong())).thenReturn(null);
		mockMvc.perform(get("/user/1")).andExpect(status().isNotFound());
	}

	@Test
	public void postUserStatusOk() throws Exception {
		when(repository.save(any(User.class))).thenReturn(stubUser);
		Gson lGson = new Gson();
		mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(lGson.toJson(stubUser)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaTypes.HAL_JSON));
	}
}

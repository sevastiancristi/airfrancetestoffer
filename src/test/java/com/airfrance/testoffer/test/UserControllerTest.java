package com.airfrance.testoffer.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.airfrance.testoffer.User;
import com.airfrance.testoffer.UserController;
import com.airfrance.testoffer.UserModelAssembler;
import com.airfrance.testoffer.UserRepository;
import com.airfrance.testoffer.exceptions.UserControllerAdvice;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@WebAppConfiguration
public class UserControllerTest {

	@InjectMocks
	UserController mockedUserController;

	@Mock
	UserRepository repository;

	@Mock
	UserModelAssembler assembler;

	MockMvc mockMvc;

	private User stubUser;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.openMocks(this);
		stubUser = new User("User1", new SimpleDateFormat("yyyy-MM-dd").parse("2003-01-01"), "France", "+3423",
				User.Gender.FEMALE);
		this.mockMvc = MockMvcBuilders.standaloneSetup(mockedUserController)
				.setControllerAdvice(new UserControllerAdvice()).build();
	}

	@Test
	public void getUsersExpectOk() throws Exception {
		List<User> stubList = Arrays.asList(new User[] {
				new User("User1", new SimpleDateFormat("yyyy-MM-dd").parse("2003-01-01"), "France", "+3423",
						User.Gender.FEMALE),
				new User("User2", new SimpleDateFormat("yyyy-MM-dd").parse("2002-01-01"), "France", "+32421",
						User.Gender.MALE) });
		when(repository.findAll()).thenReturn(stubList);
		mockMvc.perform(get("/users")).andExpect(status().isOk());
	}

	@Test
	public void getUserExpectOk() throws Exception {
		when(repository.findById(Mockito.any())).thenReturn(Optional.of(stubUser));
		mockMvc.perform(get("/user/1")).andExpect(status().isOk());
	}

	@Test
	public void getUserExpectErrorWhenUserNotFound() throws Exception {
		when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(get("/user/1")).andExpect(status().isNotFound());
	}

	@Test
	public void postUserStatusOk() throws Exception {
		when(repository.save(any(User.class))).thenReturn(stubUser);
		Gson lGson = new Gson();
		mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(lGson.toJson(stubUser)))
				.andExpect(status().isOk());
	}
}

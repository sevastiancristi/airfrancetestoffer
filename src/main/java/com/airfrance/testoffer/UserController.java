package com.airfrance.testoffer;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.airfrance.testoffer.exceptions.BadSearchRequestControllerException;
import com.airfrance.testoffer.exceptions.UserNotFoundControllerException;

@RestController
public class UserController {

	@Autowired
	private UserRepository repository;

	@Autowired
	private UserModelAssembler assembler;

	@GetMapping("/users")
	CollectionModel<EntityModel<User>> getAllUsers() {
		List<EntityModel<User>> users = repository.findAll().stream().map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(users, linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
	}

	@GetMapping("/user/{id}")
	EntityModel<User> getUserById(@PathVariable Long id) {
		User lUser = repository.findById(id).orElseThrow(() -> new UserNotFoundControllerException(id));
		return assembler.toModel(lUser);
	}

	@PostMapping("/user")
	EntityModel<User> postNewUser(@Valid @RequestBody User newUser) {
		return assembler.toModel(repository.save(newUser));
	}

	@PostMapping("/search")
	CollectionModel<EntityModel<User>> searchUser(@RequestBody User userSearchParameters) {
		if (userSearchParameters.getId() == null && userSearchParameters.getName() == null
				&& userSearchParameters.getBirthDate() == null && userSearchParameters.getResidenceCountry() == null
				&& userSearchParameters.getTelephoneNumber() == null && userSearchParameters.getGender() == null)
			throw new BadSearchRequestControllerException();

		List<EntityModel<User>> users = repository.findAll(Example.of(userSearchParameters)).stream()
				.map(assembler::toModel).collect(Collectors.toList());
		return CollectionModel.of(users, linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
	}

}

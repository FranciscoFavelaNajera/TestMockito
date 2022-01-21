package com.example.demogra;

import com.example.demogra.controller.ClientController;
import com.example.demogra.entity.Client;
import com.example.demogra.repo.ClientRepository;
import com.example.demogra.service.ClientService;
import com.example.demogra.service.ClientServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class DemograApplicationTests {
	private MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();

	@InjectMocks
	private ClientController clientController;

	@Mock
	private ClientService clientService;

	@Autowired
	private ClientServiceImpl clientServiceImpl;;

	@MockBean
	private ClientRepository clientRepository;

	@BeforeAll
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
	}

	Client c1 = new Client(Long.valueOf(1),"F","AA");
	Client c2 = new Client(Long.valueOf(2),"FF","AAA");
	List<Client> record = new ArrayList<>(Arrays.asList(c1,c2));

	@Test
	public void getAllRecords_success() throws Exception {
		List<Client> records = new ArrayList<>(Arrays.asList(c1, c2));

		Mockito.when(clientRepository.findAll()).thenReturn(records);

		mockMvc.perform(MockMvcRequestBuilders
						.get("/client")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect((ResultMatcher) jsonPath("$", hasSize(3)))
				.andExpect((ResultMatcher) jsonPath("$[2].name", is("Jane Doe")));
	}



	/*
	@Test
	public void SaveOneLenguage() {
		Client client = new Client(Long.valueOf(1),"F","AA");
		ClientServiceImpl.save(client);
		Mockito.verify(clientRepository,times(1)).save(client);

	}
	*/

	@Test
	public void GetallTest() {
		Long a= Long.valueOf(1);
		Mockito.when(clientRepository.findAll()).thenReturn(Stream.of(new Client(Long.valueOf(1),"F","AA"),new Client(Long.valueOf(2),"BB","AAA	")).collect(Collectors.toList()));
		assertEquals(2,clientRepository.findAll().size());
	}
	@Test
	public void GetOneByIDTest() {
		Client c1 = new Client(Long.valueOf(1),"F","AA");
		int id=1;
		Mockito.when(clientRepository.findById(Long.valueOf(id))).thenReturn(Optional.of((c1)));
		assertEquals(Optional.of(c1),clientRepository.findById(Long.valueOf(id)));
	}
	@Test
	public void PostMappingSucess() throws Exception {

		Mockito.when(clientService.save(c1)).thenReturn(c1);

		String content = objectWriter.writeValueAsString(c1);

		System.out.println(content);

		mockMvc.perform(MockMvcRequestBuilders
						.post("/client/")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(status().isCreated());
	}
	@Test
	public void PutMappingSucess() throws Exception {

		Mockito.when(clientService.update(c1)).thenReturn(c1);

		String content = objectWriter.writeValueAsString(c1);

		System.out.println(content);

		mockMvc.perform(MockMvcRequestBuilders
						.put("/client/")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(content))
				.andExpect(status().isOk())
				.andExpect((ResultMatcher) jsonPath("$.name", is(c1.getName())));
	}

	@Test
	public void DeleteOneLenguage() {

		Client Client = new Client(Long.valueOf(1),"F","AA");
		clientServiceImpl.delete(Long.valueOf(1));
		Mockito.verify(clientRepository,times(1)).deleteById(1L);
	}
}

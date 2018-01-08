package com.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.contoller.BookController;
import com.example.model.Book;
import com.example.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BookControllerTest {
	
	@Mock
	public BookRepository bookRepo;
	
	@InjectMocks
	public BookController bookController;
	
	public MockMvc mockMvc;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
	}
	
	@Test
	public void testGetBooks() throws Exception {
		Mockito.when(bookRepo.findAll()).thenReturn(new ArrayList<Book>());
		mockMvc.perform(get("/getBooks")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"));
	}
	
	@Test
	public void testCreateBook() throws Exception {
		//Mockito.when(bookRepo.findOne(Matchers.anyInt())).thenReturn(null);
		Book book = new Book();
		book.setId("10");
		Mockito.when(bookRepo.save(Matchers.any(Book.class))).thenReturn(book);
		String json = new ObjectMapper().writeValueAsString(book);
		mockMvc.perform(post("/createBook").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is(200));
	}
	
	
	@Test
	public void testUpdateBook() throws Exception {
		Book book = new Book();
		book.setId("2");
		Mockito.when(bookRepo.findOne(Matchers.anyString())).thenReturn(book);
		Mockito.when(bookRepo.save(Matchers.any(Book.class))).thenReturn(null);
		String json = new ObjectMapper().writeValueAsString(book);
		mockMvc.perform(put("/updateBook").content(json).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is(200));
	}

	@Test
	public void testdeleteBook() throws Exception{
		mockMvc.perform(delete("/delete/10").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().is(404));
		
	}
	

}

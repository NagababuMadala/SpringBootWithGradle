package com.example.contoller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.model.Book;
import com.example.repository.BookRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
public class BookController {
	
	@Autowired
	private BookRepository bookRepository;
	//ResponseEntity<Collection<Book>>
	@RequestMapping(method = RequestMethod.GET, value="/getBooks" )
	public List<Book> getAllBooks() {
		List <Book> book=bookRepository.findAll();
		return book;
		//return new ResponseEntity<Collection<Book>>(bookRepository.findAll(), HttpStatus.OK);
		
	}
	
	//Creating book using POST
	//ResponseEntity<Void>
	@RequestMapping(value="/createBook",method=RequestMethod.POST,consumes="application/json")
	public String createBook (@RequestBody Book bookMap,UriComponentsBuilder uri) throws JsonMappingException, JsonParseException, IOException{
		bookRepository.save(bookMap);
		HttpHeaders header = new HttpHeaders();
		//return new ResponseEntity<Void>(header,HttpStatus.CREATED);
		return "record created successfully";
	}
	
	
	//ResponseEntity<Book>
	@RequestMapping(method = RequestMethod.GET, value="/getBook/{id}" )
	public  Book getBookDetails(@PathVariable("id") String id) {
		Book book = bookRepository.findOne(id);
		return book;
		//return new ResponseEntity<Book>(book,HttpStatus.OK);
	}
	//ResponseEntity<Void>
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteBook/{id}")
	public  String deleteBookDetails(@PathVariable("id") String id){
		bookRepository.delete(id);
		HttpHeaders header = new HttpHeaders();
		//return new ResponseEntity<Void>(header, HttpStatus.OK);		
		return "record deleted success";
	}
	@RequestMapping(value="/updateBook",method=RequestMethod.PUT)
	public String updateBook ( @RequestBody Book book){
		Book bookId=bookRepository.findOne(book.getId());
		if (bookId !=null) {
			bookRepository.save(book);
		}
	return "successfully updated..";
	}

}

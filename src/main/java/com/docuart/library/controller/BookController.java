package com.docuart.library.controller;

import com.docuart.library.entity.Book;
import com.docuart.library.service.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookServices bookServices;


    @GetMapping("/getall")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(bookServices.findAllBooks(), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> save(@RequestBody Book book){
        return new ResponseEntity<>(bookServices.add(book),HttpStatus.CREATED);
    }


    @PutMapping
    public ResponseEntity<?> update(@RequestParam(name = "bookId") Long bookId, @RequestBody Book book){
        return new ResponseEntity<>(bookServices.update(bookId,book),HttpStatus.CREATED);
    }


    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam(name = "bookId") Long bookId){
        return new ResponseEntity<>(bookServices.delete(bookId), HttpStatus.NO_CONTENT);
    }


    @GetMapping("/find-by-id")
    public ResponseEntity<?> findById(@RequestParam(name = "bookId") Long bookId){
        return new ResponseEntity<>(bookServices.findById(bookId), HttpStatus.OK);
    }


    @GetMapping("/give-book")
    public ResponseEntity<?> giveBook(@RequestParam(name = "bookId") Long bookId){
        return new ResponseEntity<>(bookServices.giveBook(bookId), HttpStatus.OK);
    }
    @GetMapping("/count")
    public ResponseEntity<Long> getBookCount() {
        return new ResponseEntity<>(bookServices.getcountbooks(),HttpStatus.OK);
    }
}

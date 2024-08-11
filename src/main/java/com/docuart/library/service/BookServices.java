package com.docuart.library.service;
import com.docuart.library.entity.Book;
import com.docuart.library.repository.BookRepository;
import com.docuart.library.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServices {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAll(){
        return bookRepository.findAll(Sort.by(Sort.Direction.DESC, "bookId"));
    }

    public List<Book> findAllBooks(){
        return bookRepository.findAllBooks();
    }

    public Book findById(Long bookId){
        return bookRepository.findById(bookId).orElseThrow(()->new RuntimeException("İd bulunamadı."));

    }

    public Book add(Book book){
        return bookRepository.save(book);
    }

    public Book update(Long bookId,Book book){
        Book guncellenecekBook = bookRepository.findById(bookId).orElseThrow(()->new RuntimeException("İd bulunamadı."));
        Utils.copyNonNullProperties(book,guncellenecekBook);
        return bookRepository.save(guncellenecekBook);
    }

    public String delete(Long bookId){
        Book silinecekBook = bookRepository.findById(bookId).orElseThrow(()->new RuntimeException("İd bulunamadı."));
        bookRepository.delete(silinecekBook);
        return "Silme işlemi başarılı.";
    }

    public String giveBook(Long bookId){
        Book odunVerilecekKitap = bookRepository.findById(bookId).orElseThrow(()->new RuntimeException("İd bulunamadı."));
        odunVerilecekKitap.setQuantity(odunVerilecekKitap.getQuantity() - 1);
        update(bookId, odunVerilecekKitap);
        return "Güncellendi işlemi başarılı.";
    }

    public Long getcountbooks(){
        return bookRepository.countBooks();
    }

}

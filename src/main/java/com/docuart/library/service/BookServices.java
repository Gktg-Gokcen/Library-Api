package com.docuart.library.service;
import com.docuart.library.entity.Book;
import com.docuart.library.entity.User;
import com.docuart.library.enums.BookStatusEnum;
import com.docuart.library.repository.BookRepository;
import com.docuart.library.repository.UserRepository;
import com.docuart.library.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BookServices {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

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
        if (guncellenecekBook.getQuantity() > 0) {
            guncellenecekBook.setStatus(BookStatusEnum.AKTIF);
        }else{
            guncellenecekBook.setStatus(BookStatusEnum.PASIF);
        }
        return bookRepository.save(guncellenecekBook);
    }

    public String delete(Long bookId){
        Book silinecekBook = bookRepository.findById(bookId).orElseThrow(()->new RuntimeException("İd bulunamadı."));
        bookRepository.delete(silinecekBook);
        return "Silme işlemi başarılı.";
    }

    public String giveBook(Long bookId, Long userId) {
        Book odunVerilecekKitap = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("İd bulunamadı."));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User bulunamadi ..."));

        if (user.getBooks().stream().anyMatch(x -> x.getBookId() == odunVerilecekKitap.getBookId())) {
            throw new RuntimeException("Kitap Daha önce alınmış");
        } else {
            int updatedQuantity = odunVerilecekKitap.getQuantity() - 1;
            odunVerilecekKitap.setQuantity(updatedQuantity);
            if (updatedQuantity <= 0) {
                odunVerilecekKitap.setStatus(BookStatusEnum.PASIF);
            } else {
                odunVerilecekKitap.setStatus(BookStatusEnum.AKTIF);
            }
            bookRepository.save(odunVerilecekKitap);

            user.getBooks().add(odunVerilecekKitap);
            userRepository.save(user);

            return "Güncelleme işlemi başarılı.";
        }


    }


    public Long getcountbooks(){
        return bookRepository.countBooks();
    }

    public List<Book> getBooksByUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User bulunamadi ..."));
        return user.getBooks();
    }

}

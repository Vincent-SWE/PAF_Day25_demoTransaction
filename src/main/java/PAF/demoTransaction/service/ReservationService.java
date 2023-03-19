package PAF.demoTransaction.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PAF.demoTransaction.model.Book;
import PAF.demoTransaction.model.Resv;
import PAF.demoTransaction.model.ResvDetails;
import PAF.demoTransaction.repository.BookRepo;
import PAF.demoTransaction.repository.ResvDetailsRepo;
import PAF.demoTransaction.repository.ResvRepo;

@Service
public class ReservationService {

    @Autowired
    BookRepo bRepo;

    @Autowired
    ResvRepo rRepo;
    
    @Autowired
    ResvDetailsRepo rdRepo;
    
    @Transactional
    // A simple @Transactional can replace the entire @Transaction(isolation........) code directly below
    // @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public Boolean reserveBook(List<Book> books, String reservePersonName) {

        Boolean bReservationCompleted = false;

        // ===================================================================================
        // Logics that we want to check:
        // check for book availability by quantity
        // if books available 
        // minus quantity from the books (requires transaction)
        // QuantityUpdate Marker
        // create the reservation record (requires transaction)
        // create the reservation details' records (requires transaction)
        // ===================================================================================

     // check for book availability by quantity
     Boolean bBooksAvailable = true;
     List<Book> libBooks = bRepo.findAll();
     for (Book book : books) {
         List<Book> filteredBook = libBooks.stream().filter(b -> b.getId().equals(book.getId()))
                 .collect(Collectors.toList());

         if (!filteredBook.isEmpty()) {
             if (filteredBook.get(0).getQuantity() == 0) {
                 bBooksAvailable = false;
                 break;
             } else {
                 // QuantityUpdate Marker
                 bRepo.update(book.getId());
             }
         } else {
             bBooksAvailable = false;
             break;
         }
     }

     // if books available
     // minus quantity from the books (requires transaction)
     // QuantityUpdate Marker

     if (bBooksAvailable) {
         // create the reservation record (requires transaction)
         Resv reservation = new Resv();
         reservation.setFullName(reservePersonName);
         reservation.setResvDate(Date.valueOf(LocalDate.now()));
         Integer reservationId = rRepo.createResv(reservation);

         // create the reservation details' records (requires transaction)
         for (Book book : books) {
             ResvDetails reservationDetail = new ResvDetails();
             reservationDetail.setBookId(book.getId());
             reservationDetail.setResvId(reservationId);

             rdRepo.createResvDetails(reservationDetail);
         }

         bReservationCompleted = true;
     }

     return bReservationCompleted;
    


    }


}

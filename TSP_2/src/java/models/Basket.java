package models;
// Generated 17.12.2017 14:21:14 by Hibernate Tools 4.3.1



/**
 * Basket generated by hbm2java
 */
public class Basket  implements java.io.Serializable {
     private Integer idBasket;
     private BookCatalog bookCatalog;
     private Users users;

    public Basket() {
    }

    public Basket(BookCatalog bookCatalog, Users users) {
       this.bookCatalog = bookCatalog;
       this.users = users;
    }
   
    public Integer getIdBasket() {
        return this.idBasket;
    }
    
    public void setIdBasket(Integer idBasket) {
        this.idBasket = idBasket;
    }
    public BookCatalog getBookCatalog() {
        return this.bookCatalog;
    }
    
    public void setBookCatalog(BookCatalog bookCatalog) {
        this.bookCatalog = bookCatalog;
    }
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
}



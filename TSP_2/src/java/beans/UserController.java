/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import models.Basket;
import models.BookCatalog;
import models.Users;

/**
 *
 * @author Павел
 */
@ManagedBean
@SessionScoped
public class UserController implements java.io.Serializable {

    Users user;
    DBhelper helper;
    transient DataModel basketUser;
    transient DataModel books;
    BookCatalog current;
    String sortBy;
    BookCatalog search;

    public BookCatalog getSearch() {
        return search;
    }

    public void setSearch(BookCatalog search) {
        this.search = search;
    }

    public UserController() {
        this.helper = new DBhelper();
        this.user = (Users) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("User");
        this.basketUser = new ListDataModel(this.helper.getBasketUser(user));
        this.books = new ListDataModel(this.helper.getBooks());
        this.search = new BookCatalog();
        this.sortBy = "name ASC";
    }

    public BookCatalog getCurrent() {
        return current;
    }

    public DataModel getBasketUser() {
        return basketUser;
    }

    public DataModel getBooks() {
        return books;
    }

    public String view() {
        this.current = (BookCatalog) books.getRowData();
        return "browse";
    }

    public String backView() {
        return "user";
    }

    public String addToBasket() {
        this.basketUser = new ListDataModel(this.helper.addBookInBasket((BookCatalog) books.getRowData(), user));
        return null;
    }

    public String removeFromBasket() {
        this.basketUser = new ListDataModel(this.helper.removeBookFromBasket((Basket) basketUser.getRowData(), user));
        return null;
    }

    public String buy() {
        this.helper.buyBook((BookCatalog) books.getRowData(), user);
        return "thanks";
    }

    public String buyFromBasket() {
        List<Basket> booksInBasket = this.helper.getBasketUser(user);
        if (booksInBasket.size() > 0) {
            this.helper.buyBooks(booksInBasket, user);
            this.basketUser=null;
            return "thanks";
        }
        return null;
    }

    public String searchAndSort() {
        this.books = new ListDataModel(this.helper.searchAndSort(search, sortBy));
        return null;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortBy() {
        return sortBy;
    }

    public String allBooks() {
        this.books = new ListDataModel(this.helper.getBooks());
        return null;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index";
    }

    public String returnShop() {
        return "user";
    }
}

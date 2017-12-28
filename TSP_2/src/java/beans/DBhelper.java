/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.List;
import models.Basket;
import models.BookCatalog;
import models.Orders;
import models.Users;
import org.hibernate.Query;
import org.hibernate.Session;
import java.sql.Date;

/**
 *
 * @author Павел
 */
public class DBhelper implements java.io.Serializable {

    transient Session session = null;

    public DBhelper() {
        this.session = HibernateUtil.getSessionFactory().openSession();
    }

    public Users checkUser(String username, String password) {
        Users user = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Users where username='" + username + "' and password='" + password + "'");
            user = (Users) q.uniqueResult();
            tx.commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }

        return user;
    }

    //User page
    public List getBasketUser(Users user) {
        List<Basket> basket = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Basket where ID_user='" + user.getIdUser() + "'");
            basket = (List<Basket>) q.list();
            tx.commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return basket;
    }

    public List getBooks() {
        List<BookCatalog> books = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from BookCatalog");
            books = (List<BookCatalog>) q.list();
            tx.commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return books;
    }

    public List searchAndSort(BookCatalog search, String sort) {
        List<BookCatalog> books = null;
        StringBuilder sql = new StringBuilder();
        boolean flag = false;
        if (search.getName() != null && !search.getName().equals("")) {
            if (flag) {
                sql.append(" and name='").append(search.getName()).append("' ");
            } else {
                sql.append(" where name='").append(search.getName()).append("'");
            }
        }
        if (search.getAuthor() != null && !search.getAuthor().equals("")) {
            if (flag) {
                sql.append(" and author='").append(search.getAuthor()).append("' ");
            } else {
                sql.append(" where author='").append(search.getAuthor()).append("'");
            }
        }
        if (search.getGenre() != null && !search.getGenre().equals("")) {
            if (flag) {
                sql.append(" and genre='").append(search.getGenre()).append("' ");
            } else {
                sql.append(" where genre='").append(search.getGenre()).append("'");
            }
        }
        if (search.getYear() != null) {
            if (flag) {
                sql.append(" and year='").append(search.getYear()).append("' ");
            } else {
                sql.append(" where year='").append(search.getYear()).append("'");
            }
        }
        if (search.getCost() != null) {
            if (flag) {
                sql.append(" and cost='").append(search.getCost()).append("' ");
            } else {
                sql.append(" where cost='").append(search.getCost()).append("'");
            }
        }
        sql.append(" order by ").append(sort);
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from BookCatalog " + sql);
            books = (List<BookCatalog>) q.list();
            tx.commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return books;
    }

    public List addBookInBasket(BookCatalog book, Users user) {

        Basket newItem = new Basket();
        newItem.setBookCatalog(book);
        newItem.setUsers(user);

        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            session.saveOrUpdate(newItem);
            session.flush();
            tx.commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }

        List<Basket> basket = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Basket where ID_user='" + user.getIdUser() + "'");
            basket = (List<Basket>) q.list();
            tx.commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return basket;
    }

    public List removeBookFromBasket(Basket book, Users user) {
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            session.delete(book);
            session.flush();
            tx.commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }

        List<Basket> basket = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Basket where ID_user='" + user.getIdUser() + "'");
            basket = (List<Basket>) q.list();
            tx.commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return basket;
    }

    public void buyBook(BookCatalog book, Users user) {
        Orders newItem = new Orders();
        newItem.setBookCatalog(book);
        newItem.setUsers(user);
        newItem.setDataSale(new Date(System.currentTimeMillis()));

        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            session.saveOrUpdate(newItem);
            session.flush();
            tx.commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public void buyBooks(List<Basket> basket, Users user) {
        Date date = new Date(System.currentTimeMillis());
        for (Basket bookInBasket : basket) {
            Orders newItem = new Orders();
            newItem.setBookCatalog(bookInBasket.getBookCatalog());
            newItem.setUsers(user);
            newItem.setDataSale(date);
            try {
                org.hibernate.Transaction tx = session.beginTransaction();
                session.saveOrUpdate(newItem);
                session.delete(bookInBasket);
                session.flush();
                tx.commit();

            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        }
    }

    //Admin page
    public List getOrders() {
        List<Orders> orders = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Orders");
            orders = (List<Orders>) q.list();
            tx.commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return orders;
    }

    public List getOrdersInPeriod(Date from, Date to) {
        List<Orders> orders = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Orders where dataSale between '" + from + "' and '" + to + "'");
            orders = (List<Orders>) q.list();
            tx.commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return orders;
    }

    public List getBestseller(Date from, Date to) {
        List<BookCatalog> books = null;
        List<Long> counts = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("select count(o.bookCatalog) from Orders as o where o.dataSale between '" + from.toString() + "' and '" + to.toString() + "' group by o.bookCatalog.name  order by count(o.bookCatalog) DESC");
            counts = (List<Long>) q.list();
            tx.commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        if (!counts.isEmpty()) {
            Long maxCount = counts.get(0);
            try {
                org.hibernate.Transaction tx = session.beginTransaction();
                Query q = session.createQuery("select o.bookCatalog from Orders as o where o.dataSale between '" + from.toString() + "' and '" + to.toString() + "' group by o.bookCatalog.idBook having count(o.bookCatalog.idBook)='" + maxCount.toString() + "'");
                books = (List<BookCatalog>) q.list();
                tx.commit();

            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        }
        return books;
    }

    public Long getProfit(Date from, Date to) {
        Long profit = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("select sum(bookCatalog.cost) from Orders where dataSale between '" + from.toString() + "' and '" + to.toString() + "'");
            profit = (Long) q.uniqueResult();
            tx.commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return profit;
    }

    public Users registry(Users newUser) {
        Users user = null;
        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Users where username='" + newUser.getUsername() + "'");
            user = (Users) q.uniqueResult();
            tx.commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        if (user == null) {
            try {
                newUser.setPassword(newUser.getPassword().hashCode()+"");
                newUser.setRole("u");
                org.hibernate.Transaction tx = session.beginTransaction();
                session.saveOrUpdate(newUser);
                session.flush();
                tx.commit();
                return newUser;
            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        }
        return null;
    }
}

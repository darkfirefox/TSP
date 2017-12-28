/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author Павел
 */
@ManagedBean
@SessionScoped
public class AdminController implements java.io.Serializable{

    DBhelper helper;
    transient DataModel orders;
    transient DataModel bestsellers;
    Long profit;
    Date from;
    Date to;
    
    
    public AdminController() {
        this.helper=new DBhelper();
        this.from=new Date(117,0,1);
        this.to=new Date(System.currentTimeMillis());
        this.orders=new ListDataModel(this.helper.getOrders());
    }
    
    public String viewBestsellers(){
        this.bestsellers=new ListDataModel(this.helper.getBestseller(new java.sql.Date(from.getTime()), new java.sql.Date(to.getTime())));
        return null;
    }

    public Long getProfit() {
        return profit;
    }
    
    public void setProfit(Long profit) {
        this.profit = profit;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public DataModel getOrders() {
        return orders;
    }

    public DataModel getBestsellers() { 
        return bestsellers;
    }
    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index";
    }
    public String allOrders(){
        this.orders=new ListDataModel(this.helper.getOrders());
        return null;
    }
    public String ordersInPeriod(){
        this.orders=new ListDataModel(this.helper.getOrdersInPeriod(new java.sql.Date(from.getTime()), new java.sql.Date(to.getTime())));
        return null;
    }
    public String profitPeriod(){
        this.profit=this.helper.getProfit(new java.sql.Date(from.getTime()), new java.sql.Date(to.getTime()));
        return null;
    }
}

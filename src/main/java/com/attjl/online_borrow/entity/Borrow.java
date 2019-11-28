package com.attjl.online_borrow.entity;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "borrow_inform")
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String numbering;
    private String username;
    private Date time;
    private Integer serialNumber;  //记录用户选择的配送信息的索引值
    private Integer quantity;
    private Integer type;     //普通用户标识借阅3中状态：1.正在配送、2.借阅未归还、3.借阅已归还
    private String lastTime;   //记录借阅剩余天数，必须用String型，便于Date对象的转换成yyyy-MM-dd
    private Integer libraryType;  //图书馆用户标识借阅3中状态：1.正在配送、2.借阅未归还、3.借阅已归还
    private Date returnTime;  //用户归还图书的时刻
    private String borrowedTime;//图书已借出时间

    public String getBorrowedTime() {
        return borrowedTime;
    }

    public void setBorrowedTime(String borrowedTime) {
        this.borrowedTime = borrowedTime;
    }
    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public Integer getLibraryType() {
        return libraryType;
    }

    public void setLibraryType(Integer libraryType) {
        this.libraryType = libraryType;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumbering() {
        return numbering;
    }

    public void setNumbering(String numbering) {
        this.numbering = numbering;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}

package org.example.springbank.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.springbank.enums.TransactionType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm", timezone = "UTC")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Account sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Account receiver;

    @Column(nullable = false)
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    public Transaction(){}

    public Transaction(Account senderAccount, Account receiverAccount, double amount, TransactionType type){
        this.date = new Date();
        this.sender = senderAccount;
        this.receiver = receiverAccount;
        this.amount = amount;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public Account getSender() {
        return sender;
    }
    public void setSender(Account senderAccount) {
        this.sender = senderAccount;
    }
    public Account getReceiver() {
        return receiver;
    }
    public void setReceiver(Account receiverAccount) {
        this.receiver = receiverAccount;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void updateAmount(double amount) {
        this.amount += amount;
    }

    public TransactionType getType() {
        return type;
    }
    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public String toString(){
        return "Transaction{id="+id+", " +
                "senderAccount="+sender+", " +
                "receiverAccount="+receiver+", " +
                "amount="+amount+", " +
                "type="+type+
                "}";
    }
}

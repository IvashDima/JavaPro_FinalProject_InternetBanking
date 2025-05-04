package org.example.springbank.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.springbank.enums.TransactionType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Transactions")
public class Transaction extends BaseEntity{

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

    public Transaction(Account senderAccount, Account receiverAccount, double amount, TransactionType type){
        this.date = new Date();
        this.sender = senderAccount;
        this.receiver = receiverAccount;
        this.amount = amount;
        this.type = type;
    }

    public void updateAmount(double amount) {
        this.amount += amount;
    }

    @Override
    public String toString(){
        return "Transaction{" +
                // "id="+id+", " +
                "senderAccount="+sender+", " +
                "receiverAccount="+receiver+", " +
                "amount="+amount+", " +
                "type="+type+
                "}";
    }
}

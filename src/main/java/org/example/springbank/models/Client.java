package org.example.springbank.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "client_name")
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Account> account = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Loan> loan = new ArrayList<>();

    @JsonIgnore
    @OneToOne(mappedBy = "client", fetch = FetchType.LAZY) //cascade = CascadeType.ALL, orphanRemoval = true
    private CustomUser user;

    public Client(String name, String surname, String phone, String email, String address){
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public void setUser(CustomUser user) {
        this.user = user;
        if (user != null) {
            user.setClient(this);
        }
    }

    @Override
    public String toString(){
        return name + " " + surname
//                "Client{" +
//                "id="+id+", " +
//                "name='"+name+
//                ", " +"surname='"+surname+
//              ", " +"phone='"+phone+
//              ", " +"email='"+email+"'" +
                ;
    }
}

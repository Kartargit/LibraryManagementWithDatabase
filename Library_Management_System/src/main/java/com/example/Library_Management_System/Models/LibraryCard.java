package com.example.Library_Management_System.Models;

import com.example.Library_Management_System.Enums.CardStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LibraryCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cardNo;

    @Enumerated(value = EnumType.STRING)
    private CardStatus cardStatus;

    private Integer noOfBooksIssued;

//    foreign key in child table
    @OneToOne
    @JoinColumn
    @JsonIgnore
    private Student student;//need to set student obj here
//    child class should have unidirectional mapping for sure
    @OneToMany(mappedBy = "libraryCard",cascade = CascadeType.ALL)
    private List<Transactions> transactionsList = new ArrayList<>();
}

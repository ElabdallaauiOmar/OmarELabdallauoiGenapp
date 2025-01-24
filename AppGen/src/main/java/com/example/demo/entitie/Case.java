package com.example.demo.entitie;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cases") // Use "cases" as the table name
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_case_id", nullable = false)
    @JsonBackReference // Indicates the "back" part of the relationship
    private UserCase userCase;

    public Case() {}

    public Case(String content, UserCase userCase) {
        this.content = content;
        this.userCase = userCase;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserCase getUserCase() {
        return userCase;
    }

    public void setUserCase(UserCase userCase) {
        this.userCase = userCase;
    }
}
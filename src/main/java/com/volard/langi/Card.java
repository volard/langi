package com.volard.langi;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Cards")
public class Card {

    public Card(String front, String back){
        this.front = front;
        this.back = back;
    }

    @Id
    public String id;

    // It's usually the foreign word
    public String front;

    // It's usually explanation of the foreign word
    public String back;
}

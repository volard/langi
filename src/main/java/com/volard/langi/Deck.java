package com.volard.langi;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Decks")
public class Deck {

    @Id
    public String id;

    public String name;

    public Deck(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "Deck[id=%s, name=%s]",
                id, name);
    }
}

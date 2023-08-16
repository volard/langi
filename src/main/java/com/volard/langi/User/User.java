package com.volard.langi.User;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
@NoArgsConstructor
@Data
public class User {

    @Id
    private String id;
    @NonNull
    private String username;
    private String email;
    private String password;
}

package com.volard.langi.User;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
@NoArgsConstructor(force = true) // Empty constructor is required by the data layer and JSON (maybe)
@Data
public class User {
    @Id private String id;
    @NonNull private String username;
    @NonNull private String email;
    @NonNull private String password;
}

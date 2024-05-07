package com.capstone.crmproject.testfile;

import com.capstone.crmproject.dto.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
//import org.springframework.data.mongodb.core.mapping.Document;

//@Document(collection = "users")
@Getter
@Setter
@Builder
public class User {
    private String userName;
    private String password;
    private String userNick;
    private UserRole role;
}

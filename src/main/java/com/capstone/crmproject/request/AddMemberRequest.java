package com.capstone.crmproject.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AddMemberRequest {
    String owner;
    String workSpaceName;
    String memberName;
}

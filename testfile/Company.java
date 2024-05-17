package com.capstone.crmproject.testfile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Company {
    private String name;

    @Override
    public String toString() {
        return name;
    }
}

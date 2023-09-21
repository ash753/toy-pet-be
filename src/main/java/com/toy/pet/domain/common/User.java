package com.toy.pet.domain.common;

import com.toy.pet.domain.enums.Role;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class User {
    private Long id;
    private String name;
    private List<Role> roleList;


    public User(Long id, String name, List<Role> roleList) {
        this.id = id;
        this.name = name;
        this.roleList = roleList;
    }
}

package com.toy.pet.domain.common;

import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.enums.Role;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class User {
    private Long id;
    private String username;
    private List<Role> roleList;

    public User(Member member) {
        this.id = member.getId();
        this.username = member.getName();
        this.roleList = List.of(member.getRole());
    }
}

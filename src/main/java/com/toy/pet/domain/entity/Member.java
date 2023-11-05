package com.toy.pet.domain.entity;

import com.toy.pet.domain.common.Constant;
import com.toy.pet.domain.enums.OAuthProvider;
import com.toy.pet.domain.enums.ResponseCode;
import com.toy.pet.domain.enums.Role;
import com.toy.pet.exception.CommonException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Table(name = "members")
@Getter
@Entity
@Where(clause = "delete_yn = 0")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String email;
    private String name;

    @Column(name = "oauth_provider")
    @Enumerated(EnumType.STRING)
    private OAuthProvider oauthProvider;

    @Column(name = "oauth_id")
    private Long oauthId;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean deleteYn;


    public Member(String nickname, String email, String name, com.toy.pet.domain.enums.OAuthProvider oauthProvider,
                  Long oauthId, Role role) {
        this.nickname = nickname;

        if (!Pattern.matches(Constant.EMAIL_REGEX, email)) {
            throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0018);
        }
        this.email = email;
        this.name = name;
        this.oauthProvider = oauthProvider;
        this.oauthId = oauthId;
        this.role = role;
        this.deleteYn = false;
        this.createdBy = 1L;
        this.updatedBy = 1L;
    }

    public void delete(Long deleteMemberId) {
        this.deleteYn = true;
        this.updatedBy = deleteMemberId;
        this.updatedAt = LocalDateTime.now();
    }
}

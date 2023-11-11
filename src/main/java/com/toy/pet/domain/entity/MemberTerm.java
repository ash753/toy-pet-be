package com.toy.pet.domain.entity;

import com.toy.pet.domain.enums.ResponseCode;
import com.toy.pet.exception.CommonException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.springframework.http.HttpStatus;

@Table(name = "member_terms")
@Getter
@Entity
@Where(clause = "delete_yn = 0")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberTerm extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "term_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Term term;

    private boolean agree;

    private boolean deleteYn;

    public MemberTerm(Member member, Term term, boolean agree) {
        if (term.isRequired() && !agree) {
            throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0061);
        }

        this.member = member;
        this.term = term;
        this.agree = agree;

        this.createdBy = member.getId();
        this.updatedBy = member.getId();
    }
}

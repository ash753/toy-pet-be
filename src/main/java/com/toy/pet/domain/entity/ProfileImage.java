package com.toy.pet.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Table(name = "profile_images")
@Entity
@Where(clause = "delete_yn = 0")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String imageUrl;

    private boolean deleteYn;

    public ProfileImage(Member member, String imageUrl) {
        this.member = member;
        this.imageUrl = imageUrl;
        this.createdBy = 1L;
        this.updatedBy = 1L;
    }

    public void delete(Long deleteMemberId) {
        this.deleteYn = true;
        this.updatedBy = deleteMemberId;
        this.updatedAt = LocalDateTime.now();
    }
}

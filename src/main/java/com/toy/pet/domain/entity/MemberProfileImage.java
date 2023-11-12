package com.toy.pet.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Table(name = "member_profile_images")
@Entity
@Where(clause = "delete_yn = 0")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberProfileImage extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String originalFileName;
    private String uploadFileName;
    private String uploadFilePath;
    private String imageUrl;
    private boolean deleteYn;

    public MemberProfileImage(Member member, String originalFileName, String uploadFileName, String uploadFilePath,
                              String imageUrl) {

        this.member = member;
        this.originalFileName = originalFileName;
        this.uploadFileName = uploadFileName;
        this.uploadFilePath = uploadFilePath;
        this.imageUrl = imageUrl;
        this.createdBy = member.getId();
        this.updatedBy = member.getId();
    }

    public void delete(Long deleteMemberId) {
        this.deleteYn = true;
        this.updatedBy = deleteMemberId;
        this.updatedAt = LocalDateTime.now();
    }
}

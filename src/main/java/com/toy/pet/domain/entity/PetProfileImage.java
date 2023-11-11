package com.toy.pet.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Table(name = "pet_profile_images")
@Entity
@Where(clause = "delete_yn = 0")
@DynamicInsert
@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetProfileImage extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "pet_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pet pet;

    private String originalFileName;
    private String uploadFileName;
    private String uploadFilePath;
    private String imageUrl;
    private boolean deleteYn;


    public PetProfileImage(Pet pet, String originalFileName, String uploadFileName, String uploadFilePath,
                           String imageUrl, Long memberId) {
        this.pet = pet;
        this.originalFileName = originalFileName;
        this.uploadFileName = uploadFileName;
        this.uploadFilePath = uploadFilePath;
        this.imageUrl = imageUrl;
        this.createdBy = memberId;
        this.updatedBy = memberId;
    }

    public void delete(Long deleteMemberId) {
        this.deleteYn = true;
        this.updatedBy = deleteMemberId;
        this.updatedAt = LocalDateTime.now();
    }
}

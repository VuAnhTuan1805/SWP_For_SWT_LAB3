package com.swpteam.smokingcessation.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.swpteam.smokingcessation.common.BaseEntity;
import com.swpteam.smokingcessation.domain.enums.BlogStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Blog extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false, updatable = false)
    Account account;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    Category category;

    @Builder.Default
    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<Comment> comments = new ArrayList<>();

    String title;

    @Column(unique = true, nullable = false)
    String slug;

    String coverImageUrl;
    String excerpt;

    @Lob
    String content;

    @Enumerated(EnumType.STRING)
    BlogStatus status;
}

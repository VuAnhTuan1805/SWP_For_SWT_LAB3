package com.swpteam.smokingcessation.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swpteam.smokingcessation.domain.enums.AccountStatus;
import com.swpteam.smokingcessation.domain.enums.Role;
import com.swpteam.smokingcessation.common.BaseEntity;
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
public class Account extends BaseEntity {

    @Column(unique = true, columnDefinition = "NVARCHAR(30)")
    String username;

    @Column(nullable = false, unique = true, columnDefinition = "NVARCHAR(30)")
    String email;

    @Column(nullable = false, columnDefinition = "NVARCHAR(100)")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    @Column(unique = true, columnDefinition = "NVARCHAR(10)")
    String phoneNumber;

    @Enumerated(EnumType.STRING)
    Role role;

    @Enumerated(EnumType.STRING)
    AccountStatus status;

    String avatar;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    Member member;

    @Builder.Default
    @OneToMany(mappedBy = "account")
    @JsonIgnore
    List<AITokenUsage> aiTokenUsages = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "account")
    @JsonIgnore
    List<Blog> blogs = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "account")
    @JsonIgnore
    List<Booking> bookings = new ArrayList<>();

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    Coach coach;

    @Builder.Default
    @OneToMany(mappedBy = "account")
    @JsonIgnore
    List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "account")
    @JsonIgnore
    List<Plan> plans = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "account")
    @JsonIgnore
    List<RecordHabit> recordHabits = new ArrayList<>();

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, optional = false)
    @JsonIgnore
    Setting setting;

    @Builder.Default
    @OneToMany(mappedBy = "account")
    @JsonIgnore
    List<Subscription> subscriptions = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "account")
    @JsonIgnore
    List<Transaction> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    @JsonIgnore
    List<Notification> notifications;

    @JsonIgnore
    public boolean isHavingSubscription() {
        return subscriptions != null &&
                subscriptions.stream().anyMatch(Subscription::isActive);
    }
}
package com.shillyshally.coredomain.extrarestaurant;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class ExtraRestaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    public ExtraRestaurant(String name) {
        this(null, name, ApprovalStatus.UNCHECKED);
    }
}

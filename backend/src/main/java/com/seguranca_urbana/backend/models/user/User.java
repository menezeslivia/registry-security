package com.seguranca_urbana.backend.models.user;

import com.seguranca_urbana.backend.models.dtos.user.UserUpdateDTO;
import com.seguranca_urbana.backend.models.enums.UserRole;
import com.seguranca_urbana.backend.models.occurrence.Occurrence;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToMany(mappedBy = "user")
    private List<Occurrence> occurrences;


    public void update(UserUpdateDTO dto) {
        updateField(() -> this.username = dto.username(), dto.username());
        updateField(() -> this.password = dto.password(), dto.password());
        updateField(() -> this.userRole = dto.userRole(), dto.userRole());
    }
    private <T> void updateField(Runnable updateAction, T newValue) {
        if (newValue != null) {
            updateAction.run();
        }
    }
}

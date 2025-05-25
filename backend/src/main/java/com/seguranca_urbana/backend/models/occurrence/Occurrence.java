package com.seguranca_urbana.backend.models.occurrence;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceUpdateDTO;
import com.seguranca_urbana.backend.models.enums.OccurrenceCategory;
import com.seguranca_urbana.backend.models.enums.OccurrenceStatus;
import com.seguranca_urbana.backend.models.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_occurrence")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Occurrence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private OccurrenceCategory category;

    @Enumerated(EnumType.STRING)
    private OccurrenceStatus status;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String address;

    @Lob
    private byte[] photo;

    private LocalDateTime createdAt;


    public void update(OccurrenceUpdateDTO dto) {
        updateField(() -> this.category = dto.category(), dto.category());
        updateField(() -> this.status = dto.status(), dto.status());
        updateField(() -> this.description = dto.description(), dto.description());
        updateField(() -> this.address = dto.address(), dto.address());
        updateField(() -> this.photo = dto.photo(), dto.photo());
    }
    private <T> void updateField(Runnable updateAction, T newValue) {
        if (newValue != null) {
            updateAction.run();
        }
    }


}
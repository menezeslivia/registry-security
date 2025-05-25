package com.seguranca_urbana.backend.services.user.admin;


import com.seguranca_urbana.backend.repositorys.OccurrenceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class DeleteOccurrenceService {

    @Autowired
    private OccurrenceRepository occurrenceRepository;

    public void execute(Long id) {
        if (!occurrenceRepository.existsById(id)) {
            throw new EntityNotFoundException("Occurrence not found");
        }
        occurrenceRepository.deleteById(id);
    }
}
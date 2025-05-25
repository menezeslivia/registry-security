package com.seguranca_urbana.backend.controllers.admin;



import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceRequestDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceUpdateDTO;
import com.seguranca_urbana.backend.services.user.admin.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/occurrences")
@AllArgsConstructor
public class AdminOccurrenceController {

    private final CreateOccurrenceService createOccurrenceService;
    private final GetAllOccurrencesService getAllOccurrencesService;
    private final GetOccurrenceByIdService getOccurrenceByIdService;
    private final GetOccurrencesByAddressService getOccurrencesByAddressService;
    private final UpdateOccurrenceService updateOccurrenceService;
    private final UpdateOccurrenceStatusService updateOccurrenceStatusService;
    private final DeleteOccurrenceService deleteOccurrenceService;

    // Criação: admin pode associar a ocorrência a qualquer usuário usando o ID
    @PostMapping("/{userId}")
    public ResponseEntity<OccurrenceResponseDTO> create(@PathVariable Long userId, @RequestBody OccurrenceRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createOccurrenceService.execute(userId, dto));
    }

    @GetMapping
    public ResponseEntity<List<OccurrenceResponseDTO>> getAll() {
        return ResponseEntity.ok(getAllOccurrencesService.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OccurrenceResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(getOccurrenceByIdService.execute(id));
    }

    @GetMapping("/address/{address}")
    public ResponseEntity<List<OccurrenceResponseDTO>> getByAddress(@PathVariable String address) {
        return ResponseEntity.ok(getOccurrencesByAddressService.execute(address));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OccurrenceResponseDTO> update(@PathVariable Long id, @RequestBody OccurrenceUpdateDTO dto) {
        return ResponseEntity.ok(updateOccurrenceService.execute(id, dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OccurrenceResponseDTO> updateStatus(@PathVariable Long id, @RequestBody OccurrenceUpdateDTO dto) {
        return ResponseEntity.ok(updateOccurrenceStatusService.execute(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteOccurrenceService.execute(id);
        return ResponseEntity.noContent().build();
    }
}

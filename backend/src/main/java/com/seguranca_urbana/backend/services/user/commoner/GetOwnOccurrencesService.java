package com.seguranca_urbana.backend.services.user.commoner;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.repositorys.OccurrenceRepository;
import com.seguranca_urbana.backend.services.mappers.OccurrenceDTOMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetOwnOccurrencesService {

    @Autowired
    private OccurrenceRepository occurrenceRepository;
    @Autowired
    private OccurrenceDTOMapperService occurrenceDTOMapperService;

    public List<OccurrenceResponseDTO> execute(Long userId) {
        return occurrenceRepository.findAllByUserId(userId)
                .stream()
                .map(occurrenceDTOMapperService::toDTO)
                .toList();
    }
}
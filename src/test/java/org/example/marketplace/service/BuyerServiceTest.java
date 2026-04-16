package org.example.marketplace.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.marketplace.dto.BuyerDto;
import org.example.marketplace.entity.BuyerEntity;
import org.example.marketplace.exception.EntityConflictException;
import org.example.marketplace.mapper.BuyerMapper;
import org.example.marketplace.repository.BuyerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuyerServiceTest {

    @Mock
    BuyerRepository buyerRepository;

    @Mock
    BuyerMapper buyerMapper;

    @InjectMocks
    BuyerService buyerService;

    @Test
    void create_shouldCreateBuyer() {
        long id = 8L;
        String email = "jack@mail.ru";
        BuyerDto inputDto = new BuyerDto();
        inputDto.setEmail(email);
        BuyerDto expectedResult = new BuyerDto();
        expectedResult.setId(id);
        BuyerEntity entityBeforeSave = new BuyerEntity();
        BuyerEntity entityAfterSave = new BuyerEntity();
        entityAfterSave.setId(id);

        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        when(buyerRepository.existsByEmail(emailCaptor.capture())).thenReturn(false);
        when(buyerMapper.toEntity(any())).thenReturn(entityBeforeSave);
        when(buyerRepository.save(any())).thenReturn(entityAfterSave);
        when(buyerMapper.toDto(any())).thenReturn(expectedResult);

        BuyerDto result = buyerService.create(inputDto);
        assertEquals(expectedResult, result);
        String capturedEmail = emailCaptor.getValue();
        assertEquals(email, capturedEmail);
        verify(buyerRepository, times(1)).save(any());
        verify(buyerMapper, times(1)).toEntity(any());
        verify(buyerMapper, times(1)).toDto(any());
    }

    @Test
    void create_shouldThrowException_ifBuyerExists() {
        String email = "jack@mail.ru";
        BuyerDto inputDto = new BuyerDto();
        inputDto.setEmail(email);

        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        when(buyerRepository.existsByEmail(emailCaptor.capture())).thenReturn(true);

        assertThrows(EntityConflictException.class, () -> buyerService.create(inputDto));
        String capturedEmail = emailCaptor.getValue();
        assertEquals(email, capturedEmail);
    }


    @Test
    void get_shouldReturnBuyer_ifFound() {
        long buyerId = 8L;
        BuyerEntity buyerEntity = new BuyerEntity();
        buyerEntity.setId(buyerId);
        BuyerDto buyerDto = new BuyerDto();

        when(buyerRepository.findById(buyerId))
                .thenReturn(Optional.of(buyerEntity));
        when(buyerMapper.toDto(buyerEntity))
                .thenReturn(buyerDto);

        BuyerDto result = buyerService.get(buyerId);
        assertEquals(buyerDto, result);
        verify(buyerRepository, times(1)).findById(buyerId);
        verify(buyerMapper, times(1)).toDto(buyerEntity);
    }

    @Test
    void get_shouldThrowEntityNotFoundException() {
        when(buyerRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> buyerService.get(1L)
        );
    }
}
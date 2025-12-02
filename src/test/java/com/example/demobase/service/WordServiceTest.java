package com.example.demobase.service;

import com.example.demobase.dto.WordDTO;
import com.example.demobase.model.Word;
import com.example.demobase.repository.WordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WordServiceTest {

    @Mock
    private WordRepository wordRepository;

    @InjectMocks
    private WordService wordService;

    private Word word1;
    private Word word2;
    private Word word3;

    @BeforeEach
    void setUp() {
        word1 = new Word(1L, "PROGRAMADOR", true);
        word2 = new Word(2L, "COMPUTADORA", false);
        word3 = new Word(3L, "TECNOLOGIA", false);
    }

    @Test
    void testGetAllWords() {
        // GIVEN: El repositorio devuelve la lista de palabras configurada en el setUp
        when(wordRepository.findAllOrdered()).thenReturn(List.of(word1, word2, word3));

        // WHEN: Ejecutamos el servicio
        List<WordDTO> result = wordService.getAllWords();

        // THEN: Verificaciones
        assertNotNull(result);
        assertEquals(3, result.size()); // Deben ser 3 palabras
        
        // Verificamos el mapeo correcto del primer elemento
        assertEquals(word1.getId(), result.get(0).getId());
        assertEquals(word1.getPalabra(), result.get(0).getPalabra());
        assertEquals(word1.getUtilizada(), result.get(0).getUtilizada());
        
        // Verificamos que se llamó al método específico del repositorio
        verify(wordRepository).findAllOrdered();
        
    }

    @Test
    void testGetAllWords_EmptyList() {
        // GIVEN: El repositorio devuelve una lista vacía
        when(wordRepository.findAllOrdered()).thenReturn(Collections.emptyList());

        // WHEN: Ejecutamos el servicio
        List<WordDTO> result = wordService.getAllWords();

        // THEN: Verificaciones
        assertNotNull(result); // La lista no debe ser null
        assertTrue(result.isEmpty()); // La lista debe estar vacía
        assertEquals(0, result.size());
        
        // Verificamos la interacción
        verify(wordRepository).findAllOrdered();
        
    }
}


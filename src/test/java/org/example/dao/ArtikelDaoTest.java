package org.example.dao;

import org.example.domain.Artikel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtikelDaoTest {

    @Mock
    private EntityManager emMock;

    @InjectMocks
    private ArtikelDao target;

    @Mock
    private Artikel artikelMock;

    @Mock
    private TypedQuery<Artikel> typedQueryMock;

    @Test
    void vindBijId() {
        //given
        long id = 3455L;
        when(emMock.find(eq(Artikel.class), eq(id))).thenReturn(artikelMock);

        //when
        Artikel artikel = target.vindBijId(id);

        verify(emMock).find(eq(Artikel.class), eq(id));
        assertEquals(artikel, artikelMock);
    }

    @Test
    void geefAlles() {
        when(emMock.createNamedQuery(anyString(), eq(Artikel.class))).thenReturn(typedQueryMock);
        target.geefAlles();
        verify(emMock).createNamedQuery(anyString(), eq(Artikel.class));
        verify(typedQueryMock).getResultList();
    }

    @Test
    void aanmaken() {
        //given

        //when
        target.aanmaken(artikelMock);
        //then
        verify(emMock).persist(eq(artikelMock));

    }
}

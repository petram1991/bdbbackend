package org.example.dao;

import org.example.domain.Artikel;
import org.example.domain.Gebruiker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GebruikerDaoTest {

    @Mock
    private EntityManager emMock;

    @InjectMocks
    private GebruikerDao target;

    @Mock
    private Gebruiker gebruikerMock;

    @Mock
    private TypedQuery<Gebruiker> typedQueryMock;

    @Test
    void aanmaken() {
        //given
        //when
        target.aanmaken(gebruikerMock);
        //then
        verify(emMock).persist(eq(gebruikerMock));
    }

    @Test
    void geefAlles() {
        when(emMock.createNamedQuery(anyString(), eq(Gebruiker.class))).thenReturn(typedQueryMock);
        target.geefAlles();
        verify(emMock).createNamedQuery(anyString(), eq(Gebruiker.class));
        verify(typedQueryMock).getResultList();
    }

    @Test
    void vindBijId() {
        //given
        long id = 5L;
        when(emMock.find(eq(Gebruiker.class), eq(id))).thenReturn(gebruikerMock);

        //when
        Gebruiker gebruiker = target.vindBijId(id);

        verify(emMock).find(eq(Gebruiker.class), eq(id));
        assertEquals(gebruiker, gebruikerMock);
    }
}

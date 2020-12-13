package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = "Artikel.vindAlles", query = "SELECT u FROM Artikel u")
public class Artikel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    private String omschrijving;
    private double prijs;
    @Enumerated(value = EnumType.STRING)
    private StatusArtikel statusArtikel = StatusArtikel.ACTIEF;
}

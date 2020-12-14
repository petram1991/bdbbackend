package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.util.WachtwoordUtils;

import javax.persistence.*;

import java.util.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor

@NamedQueries({
        @NamedQuery(name = "Gebruiker.vindAlles", query = "SELECT u FROM Gebruiker u ORDER BY u.gebruiksnaam DESC"),
        @NamedQuery(name = Gebruiker.VIND_GEBRUIKER_WACHTWOORD, query = "SELECT u FROM Gebruiker u WHERE u.gebruiksnaam = :gebruiksnaam AND u.wachtwoord = :wachtwoord")
})
public class Gebruiker {
    public static final String VIND_GEBRUIKER_WACHTWOORD = "Gebruiker.VindGebruikerEnWachtwoord";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String gebruiksnaam;
    private String email;
    private String wachtwoord;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy ="aanbieder")
    private List<Artikel> aangebodenArtikel;

    @PrePersist
    private void setUUID() {
        wachtwoord = WachtwoordUtils.digestPassword(wachtwoord);
    }
}

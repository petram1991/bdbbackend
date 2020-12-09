package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.util.WachtwoordUtils;

import javax.persistence.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor

@NamedQueries({
        @NamedQuery(name = "Gebruiker.vindAlles", query = "SELECT u FROM Gebruiker u ORDER BY u.gebruiksnaam DESC")
})
public class Gebruiker {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String gebruiksnaam;
    private String email;
    private String wachtwoord;

    @PrePersist
    private void setUUID() {
        wachtwoord = WachtwoordUtils.digestPassword(wachtwoord);
    }
}

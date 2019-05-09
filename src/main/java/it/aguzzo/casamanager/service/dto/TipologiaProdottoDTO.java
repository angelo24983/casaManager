package it.aguzzo.casamanager.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.aguzzo.casamanager.domain.TipologiaProdotto} entity.
 */
public class TipologiaProdottoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2)
    private String nome;

    @NotNull
    @Size(min = 2)
    private String descrizione;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TipologiaProdottoDTO tipologiaProdottoDTO = (TipologiaProdottoDTO) o;
        if (tipologiaProdottoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipologiaProdottoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipologiaProdottoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descrizione='" + getDescrizione() + "'" +
            "}";
    }
}

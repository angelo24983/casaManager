package it.aguzzo.casamanager.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.aguzzo.casamanager.domain.Prodotto} entity.
 */
public class ProdottoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2)
    private String nome;

    @NotNull
    private Long prezzo;


    private Long tipologiaId;

    private String tipologiaNome;

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

    public Long getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Long prezzo) {
        this.prezzo = prezzo;
    }

    public Long getTipologiaId() {
        return tipologiaId;
    }

    public void setTipologiaId(Long tipologiaProdottoId) {
        this.tipologiaId = tipologiaProdottoId;
    }

    public String getTipologiaNome() {
        return tipologiaNome;
    }

    public void setTipologiaNome(String tipologiaProdottoNome) {
        this.tipologiaNome = tipologiaProdottoNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProdottoDTO prodottoDTO = (ProdottoDTO) o;
        if (prodottoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prodottoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProdottoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", prezzo=" + getPrezzo() +
            ", tipologia=" + getTipologiaId() +
            ", tipologia='" + getTipologiaNome() + "'" +
            "}";
    }
}

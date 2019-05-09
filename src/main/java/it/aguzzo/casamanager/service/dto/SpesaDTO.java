package it.aguzzo.casamanager.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link it.aguzzo.casamanager.domain.Spesa} entity.
 */
public class SpesaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3)
    private String nome;

    private LocalDate data;


    private Long userId;

    private String userLogin;

    private Set<ProdottoDTO> prodottos = new HashSet<>();

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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Set<ProdottoDTO> getProdottos() {
        return prodottos;
    }

    public void setProdottos(Set<ProdottoDTO> prodottos) {
        this.prodottos = prodottos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SpesaDTO spesaDTO = (SpesaDTO) o;
        if (spesaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), spesaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SpesaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", data='" + getData() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            "}";
    }
}

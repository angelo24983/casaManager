package it.aguzzo.casamanager.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Spesa.
 */
@Entity
@Table(name = "spesa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Spesa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "data")
    private LocalDate data;

    @ManyToOne
    @JsonIgnoreProperties("spesas")
    private User user;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "spesa_prodotto",
               joinColumns = @JoinColumn(name = "spesa_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "prodotto_id", referencedColumnName = "id"))
    private Set<Prodotto> prodottos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Spesa nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getData() {
        return data;
    }

    public Spesa data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public User getUser() {
        return user;
    }

    public Spesa user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Prodotto> getProdottos() {
        return prodottos;
    }

    public Spesa prodottos(Set<Prodotto> prodottos) {
        this.prodottos = prodottos;
        return this;
    }

    public Spesa addProdotto(Prodotto prodotto) {
        this.prodottos.add(prodotto);
        prodotto.getSpesas().add(this);
        return this;
    }

    public Spesa removeProdotto(Prodotto prodotto) {
        this.prodottos.remove(prodotto);
        prodotto.getSpesas().remove(this);
        return this;
    }

    public void setProdottos(Set<Prodotto> prodottos) {
        this.prodottos = prodottos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Spesa)) {
            return false;
        }
        return id != null && id.equals(((Spesa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Spesa{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", data='" + getData() + "'" +
            "}";
    }
}

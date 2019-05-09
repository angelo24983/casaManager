package it.aguzzo.casamanager.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Prodotto.
 */
@Entity
@Table(name = "prodotto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Prodotto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "prezzo", nullable = false)
    private Long prezzo;

    @OneToOne
    @JoinColumn(unique = true)
    private TipologiaProdotto tipologia;

    @ManyToMany(mappedBy = "prodottos")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Spesa> spesas = new HashSet<>();

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

    public Prodotto nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getPrezzo() {
        return prezzo;
    }

    public Prodotto prezzo(Long prezzo) {
        this.prezzo = prezzo;
        return this;
    }

    public void setPrezzo(Long prezzo) {
        this.prezzo = prezzo;
    }

    public TipologiaProdotto getTipologia() {
        return tipologia;
    }

    public Prodotto tipologia(TipologiaProdotto tipologiaProdotto) {
        this.tipologia = tipologiaProdotto;
        return this;
    }

    public void setTipologia(TipologiaProdotto tipologiaProdotto) {
        this.tipologia = tipologiaProdotto;
    }

    public Set<Spesa> getSpesas() {
        return spesas;
    }

    public Prodotto spesas(Set<Spesa> spesas) {
        this.spesas = spesas;
        return this;
    }

    public Prodotto addSpesa(Spesa spesa) {
        this.spesas.add(spesa);
        spesa.getProdottos().add(this);
        return this;
    }

    public Prodotto removeSpesa(Spesa spesa) {
        this.spesas.remove(spesa);
        spesa.getProdottos().remove(this);
        return this;
    }

    public void setSpesas(Set<Spesa> spesas) {
        this.spesas = spesas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prodotto)) {
            return false;
        }
        return id != null && id.equals(((Prodotto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Prodotto{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", prezzo=" + getPrezzo() +
            "}";
    }
}

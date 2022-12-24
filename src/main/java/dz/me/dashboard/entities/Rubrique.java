package dz.me.dashboard.entities;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Rubrique {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    private String nameFr;
    private String nameAr;
    private String nameEn;
    @Enumerated(EnumType.STRING)
    @Column(name = "nature")
    private Nature nature;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_service")
    private Service service;

    @OneToMany(mappedBy = "rubrique", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<RubriqueValue> rubriqueValues;

    public enum Nature {
        MONEY, NUMBER
    }
}

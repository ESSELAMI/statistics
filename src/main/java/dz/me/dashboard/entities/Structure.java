package dz.me.dashboard.entities;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author MEKRICHE TAREK
 */
@Entity
@Getter
@Setter
@Data
public class Structure {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @Column
    private String structure;

    @Column
    private String codeStructure;

    @Column
    private String structureAr;
    @Column
    private String adresse;

    @Column
    private String adresseAr;

    @ManyToOne
    @JoinColumn(name = "id_wilaya")
    private Wilaya wilaya;

}

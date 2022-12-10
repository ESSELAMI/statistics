package dz.me.dashboard.entities;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author MEKRICHE TAREK
 */
@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchiveTraitement {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;
    @Type(type = "uuid-char")
    private UUID idTicket;
    @Column
    private String ticket;

    @Column
    private String pseudoTicket;

    @Column
    private boolean isTransferred;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_service")
    private Service service;

    @Column
    @CreationTimestamp
    private Date dateEdition;
    @Column
    private Date dateAppel;
    @Column
    private Date dateTraitement;
    @Column
    private String idClient;
    @JsonIgnore
    @OneToOne
    private User userAppel;
    @JsonIgnore
    @OneToOne
    private User userTrait;
    @JsonIgnore
    @OneToOne
    private User userTransfer;

    @OneToOne
    private Guichet guichet;

    @Column
    private String traitement;
}

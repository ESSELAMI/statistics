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
import lombok.NoArgsConstructor;

/**
 *
 * @author MEKRICHE TAREK
 */
@Entity()
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;
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
    private int prioritaire = 0;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "pseudo_From_Service")
    private Service pseudoFromService;
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "idPseudoTicket", updatable = true, nullable = true, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID idPseudoTicket;

}

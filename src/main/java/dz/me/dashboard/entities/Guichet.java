package dz.me.dashboard.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import lombok.Data;

/**
 *
 * @author MEKRICHE TAREK
 */
@Entity
@Data
public class Guichet {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;
    @Column
    private int guichet;
    @Column
    private String name;
    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_service")
    private Service service;

}

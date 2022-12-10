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
public class GroupService {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    private String groupName;

    public GroupService(String groupName) {
        this.groupName = groupName;
    }

    @ManyToOne
    @JoinColumn(name = "id_structure")
    private Structure structure;
}

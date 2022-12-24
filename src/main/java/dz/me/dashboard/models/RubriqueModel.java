package dz.me.dashboard.models;

import dz.me.dashboard.entities.Rubrique.Nature;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Data

@Setter
public class RubriqueModel {
    private String id;
    private String nameFr;
    private String nameAr;
    private String nameEn;
    private String serviceId;
    private Nature nature;

}

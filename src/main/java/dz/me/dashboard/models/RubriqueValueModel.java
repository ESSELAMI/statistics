package dz.me.dashboard.models;

import java.util.Date;

import lombok.Getter;

@Getter
public class RubriqueValueModel {
    private String id;
    private String rubriqueId;
    private double value;
    private Date insertionDate;
}

package dz.me.dashboard.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
public class RubriqueValueModel {
    private String id;
    private String rubriqueId;
    private double value;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date insertionDate;
}

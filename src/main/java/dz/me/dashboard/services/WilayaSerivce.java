package dz.me.dashboard.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dz.me.dashboard.entities.Wilaya;

public interface WilayaSerivce {

    public List<Wilaya> all();

    public Optional<Wilaya> findById(UUID id);

    public Wilaya save(Wilaya wilaya);

    public void deleteById(UUID wilayaId);

    public Object saveAll(List<Wilaya> wilaya);

    public Optional<Wilaya> findByCodeWilaya(String string);

}

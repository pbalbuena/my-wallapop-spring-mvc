package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Mensaje;

public interface MensajeRepository extends CrudRepository<Mensaje, Long>{

}

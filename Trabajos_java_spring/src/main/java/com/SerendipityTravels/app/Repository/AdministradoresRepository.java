package com.SerendipityTravels.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SerendipityTravels.app.Entity.Administradores;

public interface AdministradoresRepository extends JpaRepository<Administradores, Integer>{
	
	Administradores findByCorreoAndContra(String correo, String contra);

}

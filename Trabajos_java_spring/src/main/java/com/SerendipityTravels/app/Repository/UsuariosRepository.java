package com.SerendipityTravels.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SerendipityTravels.app.Entity.Usuarios;

public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {
	
	Usuarios findByCorreoAndContra(String correo, String contra);
	// Método para buscar usuario por cédula
    Usuarios findByCedula(Long cedula);

}

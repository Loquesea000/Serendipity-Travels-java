package com.SerendipityTravels.app.Repository;

import com.SerendipityTravels.app.Entity.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Integer> {
    // Método para buscar un SuperAdmin por correo
    SuperAdmin findByCorreoAndContra(String correo, String contra);
 
}

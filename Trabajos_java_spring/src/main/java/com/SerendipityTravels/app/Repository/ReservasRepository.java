package com.SerendipityTravels.app.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SerendipityTravels.app.Entity.Reservas;

public interface ReservasRepository extends JpaRepository<Reservas, Integer>{
	
	 //List<Reservas> findByUsuarioCedulaOrderByFechaReservaDesc(long cedula);
	 Optional<Reservas> findFirstByUsuarioCedulaOrderByIdDesc(long cedula);
	 //public List<Reservas> findByUsuarioCedulaOrderByFechaReservaDesc(Long cedula);
	 public List<Reservas> findByUsuarioCedulaAndEstOrderByFechaReservaDesc(Long cedula, int est);

}

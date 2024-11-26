package com.SerendipityTravels.app.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reservas")
public class Reservas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private int id;

    @Column(name = "fecha_reserva")
    private LocalDate fechaReserva;

    @Column(name = "fecha_viaje")
    private Date fechaViaje;

    @ManyToOne
    @JoinColumn(name = "cedula", referencedColumnName = "cedula")
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "id_destino", referencedColumnName = "id")
    private Destinos destino;

    @ManyToOne
    @JoinColumn(name = "id_paquete", referencedColumnName = "id_paquete")
    private PaquetesTuristicos paquete;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pagos> pagos;
    
    @Column(name = "est", nullable = false)
    private int est = 1; // Valor predeterminado de 1

    public int getEst() {
		return est;
	}

	public void setEst(int est) {
		this.est = est;
	}
	
	// Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate localDate) {
        this.fechaReserva = localDate;
    }

    public Date getFechaViaje() {
        return fechaViaje;
    }

    public void setFechaViaje(Date fechaViaje) {
        this.fechaViaje = fechaViaje;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Destinos getDestino() {
        return destino;
    }

    public void setDestino(Destinos destino) {
        this.destino = destino;
    }

    public PaquetesTuristicos getPaquete() {
        return paquete;
    }

    public void setPaquete(PaquetesTuristicos paquete) {
        this.paquete = paquete;
    }

    public int getIdDestino() {
        return destino != null ? destino.getId() : -1;
    }

    public void setIdDestino(int idDestino) {
        this.destino = new Destinos();
        this.destino.setId(idDestino);
    }

    public int getIdPaquete() {
        return paquete != null ? paquete.getId() : -1;
    }

    public void setIdPaquete(int idPaquete) {
        this.paquete = new PaquetesTuristicos();
        this.paquete.setId(idPaquete);
    }

    public long getCedula() {
        return usuario != null ? usuario.getCedula() : -1;
    }

    public void setCedula(long cedula) {
        this.usuario = new Usuarios();
        this.usuario.setCedula(cedula);
    }
}

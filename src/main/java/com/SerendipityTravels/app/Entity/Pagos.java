package com.SerendipityTravels.app.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "pagos")
public class Pagos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private int idPago;

    @Column(name = "fecha_pago")
    private Date fechaPago;

    private String metodo;

    @Column(name = "MontoPagado")
    private long montoPagado;

    @ManyToOne
    @JoinColumn(name = "id_reserva", referencedColumnName = "id_reserva")
    private Reservas reserva;

    // Getters y Setters
    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public long getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(long montoPagado) {
        this.montoPagado = montoPagado;
    }

    public Reservas getReserva() {
        return reserva;
    }

    public void setReserva(Reservas reserva) {
        this.reserva = reserva;
    }
}

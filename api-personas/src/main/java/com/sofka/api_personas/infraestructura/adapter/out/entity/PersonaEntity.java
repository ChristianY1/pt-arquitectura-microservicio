package com.sofka.api_personas.infraestructura.adapter.out.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "PERSONAS")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PersonaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personas_seq")
    @SequenceGenerator(name = "personas_seq", sequenceName = "personas_seq", allocationSize = 1)
    private Long personaId;
    private String nombre;
    private String genero;
    private int edad;

    @Column(unique = true, length = 10)
    private String identificacion;

    private String direccion;
    private String telefono;

    @CreatedDate
    private LocalDateTime fechaCreacion;

    @LastModifiedDate
    private LocalDateTime fechaActualizacion;

}

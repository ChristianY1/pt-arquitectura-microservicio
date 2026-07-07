package com.sofka.api_transaccional.infraestructura.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sofka.api_transaccional.infraestructura.adapter.out.entity.ClienteEntity;

public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, Long> {

}

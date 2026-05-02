/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.repository;

import entities.SorcererEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 *
 * @author zubbo
 */
@Repository
public interface SorcererRepository extends JpaRepository<SorcererEntity, Long> {
    Optional<SorcererEntity> findByName(String name);
}

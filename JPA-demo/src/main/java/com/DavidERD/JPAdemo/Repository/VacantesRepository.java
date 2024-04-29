package com.DavidERD.JPAdemo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DavidERD.JPAdemo.Models.VacantesModels;

public interface VacantesRepository extends JpaRepository<VacantesModels, Integer> {
    List<VacantesModels> findByEstatus(String estatus);
    List<VacantesModels> findByDestacadoAndEstatusOrderByIdDesc(int destacado, String estatus);
    List<VacantesModels> findBySalarioBetweenOrderBySalarioDesc(double s1, double s2);
    List<VacantesModels> findByEstatusIn(String[] estatus);
}

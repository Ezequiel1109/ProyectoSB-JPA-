package com.DavidERD.JPAdemo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DavidERD.JPAdemo.Models.CategoriasModels;
//public interface CategoriasRepository extends CrudRepository<CategoriasModels, Integer>
public interface CategoriasRepository extends JpaRepository<CategoriasModels, Integer>{

}

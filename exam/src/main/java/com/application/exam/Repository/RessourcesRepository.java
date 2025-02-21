package com.application.exam.Repository;

import com.application.exam.Model.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RessourcesRepository extends JpaRepository<Ressource, Integer> {
}

package com.application.exam.Repository;


import com.application.exam.Model.ModuleElement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<ModuleElement, Integer> {
}

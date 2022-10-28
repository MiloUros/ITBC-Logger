package com.example.ITBC.Logger.repository;

import com.example.ITBC.Logger.model.Log;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findAll(Specification<Log> specification);
}

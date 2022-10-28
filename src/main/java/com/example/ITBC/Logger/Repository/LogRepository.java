package com.example.ITBC.Logger.Repository;

import com.example.ITBC.Logger.Model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}

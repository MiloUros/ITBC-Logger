package com.example.ITBC.Logger.Model;

import com.example.ITBC.Logger.Model.Enum.LogType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "log_table")
@Getter @Setter
@NoArgsConstructor
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String message;
    private LogType logType;
    private LocalDate createdDate;

    public Log(String message, LogType logType, LocalDate createdDate) {
        this.message = message;
        this.logType = logType;
        this.createdDate = createdDate;
    }
}

package com.example.ITBC.Logger.model;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "logs")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "message")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "logType")
    private LogType logType;

    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdDate;

    @JsonIgnore
    @ManyToOne()
    private User user;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Log otherLog)) return false;
        return id != null && id.equals(otherLog.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

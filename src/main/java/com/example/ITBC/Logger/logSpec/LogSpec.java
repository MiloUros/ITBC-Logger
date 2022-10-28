package com.example.ITBC.Logger.logSpec;

import com.example.ITBC.Logger.model.Log;
import com.example.ITBC.Logger.model.LogType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogSpec {

    public static Specification<Log> getSpec(String message, LogType logType, Date startDate, Date endDate) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicate = new ArrayList<>();

            if (message != null && !(message.isEmpty())) {
                predicate.add(criteriaBuilder.like(root.get("message"), message));
            }
            if (logType != null) {
                predicate.add(criteriaBuilder.equal(root.get("logType"), logType));
            }
            return criteriaBuilder.and(predicate.toArray(new Predicate[0]));
        });
    }

}

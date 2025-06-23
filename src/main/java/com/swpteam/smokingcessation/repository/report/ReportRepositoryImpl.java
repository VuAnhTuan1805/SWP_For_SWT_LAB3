package com.swpteam.smokingcessation.repository.report;


import com.swpteam.smokingcessation.domain.dto.report.ReportSummaryResponse;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class ReportRepositoryImpl implements IReportRepository {

    private final EntityManager entityManager;

    @Override
    public ReportSummaryResponse getReportSummary(LocalDateTime from, LocalDateTime to) {
        Object[] result = (Object[]) entityManager.createQuery("""
                        SELECT 
                            (SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.createdAt BETWEEN :from AND :to AND t.isDeleted = false),
                            (SELECT COUNT(a) FROM Account a WHERE a.createdAt BETWEEN :from AND :to AND a.isDeleted = false),
                            (SELECT COUNT(a) FROM Account a WHERE a.isDeleted = false),
                            (SELECT COUNT(a) FROM Account a WHERE a.updatedAt BETWEEN :from AND :to AND a.isDeleted = false)
                        """)
                .setParameter("from", from)
                .setParameter("to", to)
                .getSingleResult();

        return ReportSummaryResponse.builder()
                .revenue((Double) result[0])
                .newAccounts(((Long) result[1]).intValue())
                .currentAccounts(((Long) result[2]).intValue())
                .activeAccounts(((Long) result[3]).intValue())
                .fromDate(from)
                .toDate(to)
                .build();
    }
}

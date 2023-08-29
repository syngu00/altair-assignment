package hu.syngu00.dataprocessor.logging.repository;

import hu.syngu00.dataprocessor.logging.entity.RequestLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RequestLogRepository extends JpaRepository<RequestLogEntity, UUID> {
}

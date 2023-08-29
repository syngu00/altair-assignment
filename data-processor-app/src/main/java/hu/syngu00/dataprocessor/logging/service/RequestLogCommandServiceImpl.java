package hu.syngu00.dataprocessor.logging.service;

import hu.syngu00.dataprocessor.logging.RequestLogCommandService;
import hu.syngu00.dataprocessor.logging.entity.RequestLogEntity;
import hu.syngu00.dataprocessor.logging.mapper.RequestLogMapper;
import hu.syngu00.dataprocessor.logging.repository.RequestLogRepository;
import hu.syngu00.dataprocessor.model.RequestLog;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RequestLogCommandServiceImpl implements RequestLogCommandService {

    private final RequestLogRepository repository;
    private final RequestLogMapper mapper;

    @Override
    @Transactional(noRollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public RequestLog createEntry(RequestLog entry) {
        RequestLogEntity saved = repository.save(mapper.toEntity(entry));
        return mapper.toDto(saved);
    }

}

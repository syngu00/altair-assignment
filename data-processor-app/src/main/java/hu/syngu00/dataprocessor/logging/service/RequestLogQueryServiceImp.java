package hu.syngu00.dataprocessor.logging.service;

import hu.syngu00.dataprocessor.logging.RequestLogQueryService;
import hu.syngu00.dataprocessor.logging.mapper.RequestLogMapper;
import hu.syngu00.dataprocessor.logging.repository.RequestLogRepository;
import hu.syngu00.dataprocessor.model.RequestLog;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class RequestLogQueryServiceImp implements RequestLogQueryService {

    private final RequestLogRepository repository;
    private final RequestLogMapper mapper;

    @Override
    public List<RequestLog> queryLogs() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }
}

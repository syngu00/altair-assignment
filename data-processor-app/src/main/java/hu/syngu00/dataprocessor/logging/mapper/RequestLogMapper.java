package hu.syngu00.dataprocessor.logging.mapper;

import hu.syngu00.dataprocessor.logging.entity.RequestLogEntity;
import hu.syngu00.dataprocessor.model.RequestLog;
import org.springframework.stereotype.Component;

@Component
public class RequestLogMapper {

    public RequestLog toDto(RequestLogEntity entity) {
        RequestLog dto = new RequestLog();
        dto.setId(entity.getId());
        dto.setRequestDateTime(entity.getRequestDateTime());
        dto.setDuration(entity.getDuration());
        dto.setMethod(entity.getMethod());
        dto.setUri(entity.getUri());
        dto.setStatus(entity.getStatus());
        dto.setRequestBody(entity.getRequestBody());
        dto.setResponseBody(entity.getResponseBody());
        return dto;
    }

    public RequestLogEntity toEntity(RequestLog dto) {
        RequestLogEntity entity = new RequestLogEntity();
        entity.setId(dto.getId());
        entity.setRequestDateTime(dto.getRequestDateTime());
        entity.setDuration(dto.getDuration());
        entity.setMethod(dto.getMethod());
        entity.setUri(dto.getUri());
        entity.setStatus(dto.getStatus());
        entity.setRequestBody(dto.getRequestBody());
        entity.setResponseBody(dto.getResponseBody());
        return entity;
    }
}



package hu.syngu00.dataprocessor.logging;

import hu.syngu00.dataprocessor.model.RequestLog;

import java.util.List;

public interface RequestLogQueryService {
    List<RequestLog> queryLogs();
}

package hu.syngu00.dataprocessor.logging;

import hu.syngu00.dataprocessor.model.RequestLog;

public interface RequestLogCommandService {
    RequestLog createEntry(RequestLog entry);
}

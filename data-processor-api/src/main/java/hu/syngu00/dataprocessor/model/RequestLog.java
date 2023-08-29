package hu.syngu00.dataprocessor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

/*
    TODO to be hones, i'm not sure why i'm put this model here
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestLog {
    private UUID id;
    private ZonedDateTime requestDateTime;
    private long duration;
    private String method;
    private String uri;
    private int status;
    private String requestBody;
    private String responseBody;

    public RequestLog(ZonedDateTime requestDateTime, long duration, String method, String uri, int status, String requestBody, String responseBody) {
        this.requestDateTime = requestDateTime;
        this.duration = duration;
        this.method = method;
        this.uri = uri;
        this.status = status;
        this.requestBody = requestBody;
        this.responseBody = responseBody;
    }
}

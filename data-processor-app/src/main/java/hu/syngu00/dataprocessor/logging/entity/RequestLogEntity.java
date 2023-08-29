package hu.syngu00.dataprocessor.logging.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.UuidGenerator;

import java.time.ZonedDateTime;
import java.util.UUID;

@Immutable
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "app_request_log")
public class RequestLogEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "request_date_time", nullable = false)
    private ZonedDateTime requestDateTime;

    @Column(name = "duration", nullable = false)
    private long duration;

    @Column(name = "method", nullable = false)
    private String method;

    @Column(name = "uri", nullable = false)
    private String uri;

    @Column(name = "status", nullable = false)
    private int status;

    @Lob
    @Column(name = "request_body")
    private String requestBody;

    @Lob
    @Column(name = "response_body")
    private String responseBody;
}

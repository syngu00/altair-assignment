package hu.syngu00.dataprocessor.controller;

import hu.syngu00.dataprocessor.logging.RequestLogQueryService;
import hu.syngu00.dataprocessor.model.RequestLog;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api")
public class RequestLogController {

    private final RequestLogQueryService queryService;


    /*
        TODO a paging controller would be better in this case
     */
    @GetMapping(path = "/log")
    public List<RequestLog> process() {
        return queryService.queryLogs();
    }
}

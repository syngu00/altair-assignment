package hu.syngu00.dataprocessor.controller;

import hu.syngu00.dataprocessor.logging.RequireRequestLogging;
import hu.syngu00.dataprocessor.median.MedianCalculationService;
import hu.syngu00.dataprocessor.model.ProcessDataRequest;
import hu.syngu00.dataprocessor.model.ProcessDataResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api")
public class DataProcessController {

    private final MedianCalculationService calculationService;

    /*
       TODO Proper JSON Schema validation with 3rd party tool would be nice
     */
    @RequireRequestLogging
    @PostMapping(path = "/process")
    public ProcessDataResponse process(@RequestBody ProcessDataRequest request) {
        return calculationService.calculateMedian(request);
    }

}

package hu.syngu00.dataprocessor.median;

import hu.syngu00.dataprocessor.model.ProcessDataRequest;
import hu.syngu00.dataprocessor.model.ProcessDataResponse;

public interface MedianCalculationService {

    ProcessDataResponse calculateMedian(ProcessDataRequest request);
}

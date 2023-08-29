package hu.syngu00.dataprocessor.median.service;

import hu.syngu00.dataprocessor.median.MedianCalculationService;
import hu.syngu00.dataprocessor.model.ProcessDataRequest;
import hu.syngu00.dataprocessor.model.ProcessDataResponse;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MedianCalculationServiceImpl implements MedianCalculationService {

    /*
        TODO A proper data validation on top the JSON schema validation would be good
     */
    @Override
    public ProcessDataResponse calculateMedian(ProcessDataRequest request) {
        ProcessDataResponse response = new ProcessDataResponse();

        request.forEach((label, attributesMap) -> {
            Map<String, Object> medianMap = attributesMap
                    .asMap()
                    .entrySet()
                    .stream()
                    .parallel()
                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> getMedia(entry.getValue())));

            response.put(label, medianMap);
        });

        return response;
    }

    /*
        TODO It's somewhat genius solution, but in the other hand it's kinda lame, for example a null in the collection will break this solution
     */
    Object getMedia(Collection<Object> values) {
        Object firstValue = values.iterator().next();
        if (firstValue instanceof String) {
            return getMedia(values.stream().map(o -> (String) o), String::compareTo);
        } else if (firstValue instanceof Number) {
            return getMedia(values.stream().map(Number.class::cast), Comparator.comparingDouble(Number::doubleValue));
        } else {
            throw new IllegalArgumentException("Unsupported value type: " + firstValue.getClass());
        }
    }

    <T> T getMedia(Stream<T> valuesStream, Comparator<T> comparator) {
        List<T> sorted = valuesStream.sorted(comparator).toList();

        T median;
        int middle = sorted.size() / 2;
        if (sorted.size() % 2 == 0) {
            median = sorted.get(middle - 1);
        } else {
            median = sorted.get(middle);
        }

        return median;
    }

}


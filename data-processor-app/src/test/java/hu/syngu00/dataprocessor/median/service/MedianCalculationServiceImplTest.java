package hu.syngu00.dataprocessor.median.service;

import hu.syngu00.dataprocessor.model.ProcessDataRequest;
import hu.syngu00.dataprocessor.model.ProcessDataResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class MedianCalculationServiceImplTest {

    private MedianCalculationServiceImpl service;

    @BeforeEach
    void setUp() {
        this.service = new MedianCalculationServiceImpl();
    }

    @Test
    void tesGetMediaWithString() {
        String result = (String) service.getMedia(List.of("baa", "aaa", "caa"));
        assertThat(result, is("baa"));
    }

    @Test
    void tesGetMediaWithDouble() {
        Double result = (Double) service.getMedia(List.of(1.1, 1.4, 2.5, 0.1));
        assertThat(result, is(1.1));
    }

    @Test
    void tesGetMediaWithInt() {
        Integer result = (Integer) service.getMedia(List.of(5, 2, 2, 4));
        assertThat(result, is(2));
    }

    @Test
    void tesGetMediaWithIntAndDouble() {
        Double result = (Double) service.getMedia(List.of(5, 2.1, 2, 4));
        assertThat(result, is(2.1));
    }
}

package hu.syngu00.dataprocessor.service;

import hu.syngu00.dataprocessor.model.ProcessDataRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CsvReaderServiceTest {

    private CsvReaderService csvReaderService;

    @BeforeEach
    void setUp() {
        this.csvReaderService = new CsvReaderService();
    }

    @Test
    void testParse() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream testCsv = classLoader.getResourceAsStream("test.csv");

        ProcessDataRequest request = csvReaderService.parse(testCsv);

        assertThat(request, allOf(
                hasKey("item1"),
                hasKey("item2")
        ));

        Map<String, Collection<Object>> item1 = request.get("item1").asMap();
        assertThat(item1, allOf(
                hasKey("field1"),
                hasKey("field2")
                )
        );
        assertThat(new ArrayList<>(item1.get("field1")), containsInAnyOrder(10.0, 12.0));
        assertThat(new ArrayList<>(item1.get("field2")), containsInAnyOrder(10.0, 12.0));
        assertThat(new ArrayList<>(item1.get("field3")), containsInAnyOrder("aaa", "baa"));

        Map<String, Collection<Object>> item2 = request.get("item2").asMap();
        assertThat(item2, allOf(
                        hasKey("field1"),
                        hasKey("field2")
                )
        );
        assertThat(new ArrayList<>(item2.get("field1")), containsInAnyOrder(10.0, 12.0));
        assertThat(new ArrayList<>(item2.get("field2")), containsInAnyOrder(10.0, 12.0));
        assertThat(new ArrayList<>(item2.get("field3")), containsInAnyOrder("aaa", "baa"));
    }

}

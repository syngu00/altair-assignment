package hu.syngu00.dataprocessor;

import hu.syngu00.dataprocessor.model.CliOptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class DataProcessorCliTest {

    @InjectMocks
    private DataProcessorCli cli;

    @Test
    void testHandleArguments() {
        CliOptions options = cli.handleArguments("-u", "http://localhost:8090", "-f", "samples/gaussian.csv", "-s", "-o", "result.json");

        assertThat(options, allOf(
                hasProperty("uri", is("http://localhost:8090")),
                hasProperty("inputFile", is("samples/gaussian.csv")),
                hasProperty("writeOutResponse", is(true)),
                hasProperty("outFile", is("result.json"))
        ));
    }
}

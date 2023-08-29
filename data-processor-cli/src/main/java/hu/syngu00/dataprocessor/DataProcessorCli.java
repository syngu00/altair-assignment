package hu.syngu00.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.syngu00.dataprocessor.model.CliOptions;
import hu.syngu00.dataprocessor.model.ProcessDataRequest;
import hu.syngu00.dataprocessor.model.ProcessDataResponse;
import hu.syngu00.dataprocessor.service.CsvReaderService;
import hu.syngu00.dataprocessor.service.ResultWriterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

/*
    Probably spring boot in this case not the best choice, too robust too slow
 */
@Slf4j
@SpringBootApplication
@AllArgsConstructor
public class DataProcessorCli implements CommandLineRunner {

    private final RestTemplateBuilder restTemplateBuilder;
    private final ResultWriterService resultWriterService;
    private final CsvReaderService csvReaderService;
    private final ObjectMapper objectMapper;

    public static void main(String[] args) {
        new SpringApplicationBuilder(DataProcessorCli.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        CliOptions options = handleArguments(args);

        RestTemplate restTemplate = restTemplateBuilder
                .rootUri(options.getUri())
                .build();

        log.info("CLI Options {}", options);

        ProcessDataRequest request = csvReaderService.parse(options);
        ProcessDataResponse response = restTemplate.postForEntity("/api/process", request, ProcessDataResponse.class)
                .getBody();

        log.info("The file was processed successfully the result is: \n {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));

        if (options.isWriteOutResponse()) {
            resultWriterService.save(response, options);
        }
    }

    CliOptions handleArguments(String... args) {
        ArgumentParser parser = ArgumentParsers.newFor("DataProcessorCli").build()
                .defaultHelp(true)
                .description("DataProcessorCli is a command line interface for processing data.");

        parser.addArgument("-u", "--uri")
                .setDefault("http://localhost:8080")
                .help("The URI of the server to connect to. Default is http://localhost:8080");

        parser.addArgument("-f", "--file")
                .required(true)
                .help("The input file to be processed. This option is mandatory.");

        parser.addArgument("-s", "--save")
                .action(net.sourceforge.argparse4j.impl.Arguments.storeTrue())
                .help("Flag indicating whether to save the response. If this flag is present, the response will be saved.");

        parser.addArgument("-o", "--out")
                .help("The file where the response will be saved. This option is optional. If not provided and the -s/--save flag is present, the response will be saved with the original file name but with a postfix.");

        Namespace res;
        try {
            res = parser.parseArgs(args);
            return CliOptions.builder()
                    .uri(res.getString("uri"))
                    .inputFile(res.getString("file"))
                    .writeOutResponse(res.getBoolean("save"))
                    .outFile(res.getString("out"))
                    .build();
        } catch (ArgumentParserException e) {
            parser.printHelp();
            parser.handleError(e);
            System.exit(1);
        } catch (Exception e) {
            parser.printHelp();
            log.error("Total failure", e);
            System.exit(1);
        }

        return CliOptions.builder().build();
    }


}

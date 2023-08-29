package hu.syngu00.dataprocessor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.syngu00.dataprocessor.model.CliOptions;
import hu.syngu00.dataprocessor.model.ProcessDataResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;


/*
    It would be nice if the result would be saved as CSV, proper IO error handling also would be nice
 */
@Slf4j
@Service
@AllArgsConstructor
public class ResultWriterService {

    private final ObjectMapper objectMapper;

    public void save(ProcessDataResponse response, CliOptions options) {
        String outputPath;
        if (options.getOutFile() == null) {
            File originalFile = new File(options.getInputFile());
            String originalFileName = originalFile.getName();
            String originalFileNameWithoutExtension = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
            String resultFileName = originalFileNameWithoutExtension + "_result.json";
            outputPath = new File(originalFile.getParent(), resultFileName).getPath();
        } else {
            outputPath = options.getOutFile();
        }

        File resultFile = new File(outputPath);

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(resultFile, response);
        } catch (IOException e) {
            log.error("Unable to save result, File:'{}'", outputPath);
        }

        log.info("The response was successfully saved, File:'{}'", outputPath);
    }

}

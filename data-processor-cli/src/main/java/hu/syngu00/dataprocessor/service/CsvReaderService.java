package hu.syngu00.dataprocessor.service;

import hu.syngu00.dataprocessor.model.CliOptions;
import hu.syngu00.dataprocessor.model.ProcessDataRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/*
    This service should also employ a proper file format validation, and should give a proper error in case of invalid csv
 */
@Slf4j
@Service
public class CsvReaderService {

    public ProcessDataRequest parse(CliOptions options) {
        try (InputStream inputStream = new FileInputStream(options.getInputFile())) {
            return parse(inputStream);
        } catch (IOException e) {
            log.error("Unable to read CSV file, please check if the file is exist or properly formatted");
            System.exit(1);
        }
        return null;
    }

    public ProcessDataRequest parse(InputStream inputStream) throws IOException {
        CSVFormat format = CSVFormat.Builder.create()
                .setDelimiter(';')
                .setHeader()
                .setSkipHeaderRecord(true)
                .build();

        ProcessDataRequest request = new ProcessDataRequest();

        try (CSVParser csvParser = new CSVParser(new InputStreamReader(inputStream), format)) {
            List<String> headers = csvParser.getHeaderNames();
            String labelHeader = headers.get(headers.size() - 1);
            for (CSVRecord csvRecord : csvParser) {
                String label = csvRecord.get(labelHeader);
                for (String header : headers) {
                    if (!header.equals(labelHeader)) {
                        String value = csvRecord.get(header);
                        Object parsedValue;
                        try {
                            parsedValue = Double.parseDouble(value);
                        } catch (NumberFormatException e) {
                            parsedValue = value;
                        }
                        request.put(label, header, parsedValue);
                    }
                }
            }
        }
        return request;
    }
}

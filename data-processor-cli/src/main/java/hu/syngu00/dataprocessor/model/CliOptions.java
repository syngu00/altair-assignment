package hu.syngu00.dataprocessor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CliOptions {

    @Builder.Default
    private String uri = "http://localhost:8080"; //-u --url

    private String inputFile; //-f --file

    @Builder.Default
    private boolean writeOutResponse = false; // -s --save

    private String outFile; // -o --out
}

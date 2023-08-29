# Data Processor CLI

`data-processor-cli` is a command line interface (CLI) application that processes a given CSV file, sends the processed data to a web app API, and returns a result that can be saved as a JSON file.


## Usage

`DataProcessorCli [-h] [-u URI] -f FILE [-s] [-o OUT]`
Options
- `-h, --help`: Show the help message and exit.
- `-u URI, --uri URI`: The URI of the server to connect to. Default is http://localhost:8080.
- `-f FILE, --file FILE`: The input file to be processed. This option is mandatory.
- `-s, --save`: Flag indicating whether to save the response. If this flag is present, the response will be saved. Default is false.
- `-o OUT, --out OUT`: The file where the response will be saved. This option is optional. If not provided and the -s/--save flag is present, the response will be saved with the original file name but with a postfix.

## Example Usage
### Linux/Mac

`./bin/DataProcessorCli -u http://localhost:8080 -f samples/gaussian.csv -s -o result.json`

### Windows

`./bin/DataProcessorCli.bat -u http://localhost:8080 -f samples/gaussian.csv -s -o result.json`

CSV Format
The required CSV format is as follows:

```
"a1";"a2";"a3";"a4";"id";"label"
5.1;3.5;1.4;0.2;"id_1";"Iris-setosa"
4.9;3.0;1.4;0.2;"id_2";"Iris-setosa"
5.8;2.8;5.1;2.4;"id_115";"Iris-virginica"
6.4;3.2;5.3;2.3;"id_116";"Iris-virginica"
```

Notes
- More samples can be found in the `samples` folder.
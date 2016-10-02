# Sentiment analysis using [LingPipe][1]

[![Build Status][2]][3]

Documentation: [sentiment-analysis.pdf][8]

## Creating the documentation
The documentation is written in LaTeX, the PDF output will be generated in the
target/documentation folder by using the following command:

```shell
make all
```

## Downloading the trainer and evaluator files
The manually annotated trainer and evaluator files are not distributed due to
license compatibility. However they can be downloaded from the [META-SHARE][4] site
after registering:
- [Train data for restaurant reviews][5]
- [Train data for laptop reviews][6]

After downloading the files they should be placed in the src/test/resources directory:

```
src
`-- test
    `-- resources
        |-- laptop
        |   |-- Laptops_Train.xml
        |   |-- laptops_trial_english_sl.xml
        `-- restaurant
            |-- Restaurants_Train.xml
            `-- restaurants_trial_english_sl.xml
```

## Building the application
The application can be built by issuing this command:

```shell
mvn install
```

The above command will run all the unit tests for which having the trainer and
evaluator files in the appropriate folder is a prerequisite. However the
application can also be built when these files are missing - in this case just
skip the unit tests:

```shell
mvn install -DskipTests=true
```

## Running the application
The application can also be run from the command line by providing the trainer and
evaluator files as arguments.

```shell
mvn exec:java -Dexec.mainClass="com.github.sandor_balazs.sentiment_analysis.Application" -Dexec.args="TRAINER_FILE EVALUATOR_FILE"
```

## Troubleshooting
If build fails due to unresolvable [LingPipe][1] dependency (e.g. the artifact
cannot be found in [Maven's central repository][7]) the needed
lingpipe-4.1.0.jar file has to be installed manually in the local repository:

```shell
wget http://lingpipe-download.s3.amazonaws.com/lingpipe-4.1.0.jar
mvn install:install-file -Dfile=lingpipe-4.1.0.jar -DgroupId=com.aliasi -DartifactId=lingpipe -Dversion=4.1.0 -Dpackaging=jar
rm lingpipe-4.1.0.jar
```

[1]: http://alias-i.com/lingpipe/
[2]: https://travis-ci.org/sandor-balazs/sentiment-analysis.svg?branch=master
[3]: https://travis-ci.org/sandor-balazs/sentiment-analysis
[4]: http://metashare.ilsp.gr:8080
[5]: http://metashare.ilsp.gr:8080/repository/search/?q=SemEval-2016+ABSA+Restaurant+Reviews-English+Train+Data
[6]: http://metashare.ilsp.gr:8080/repository/search/?q=SemEval-2016+ABSA+Laptop+Reviews-English+Train+Data
[7]: http://search.maven.org/#search%7Cga%7C1%7Clingpipe
[8]: https://github.com/sandor-balazs/sentiment-analysis/releases/download/v1.0.0/sentiment-analysis.pdf

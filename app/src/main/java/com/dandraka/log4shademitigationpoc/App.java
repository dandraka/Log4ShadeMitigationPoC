package com.dandraka.log4shademitigationpoc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
        //String configFile = new File("src/resources", "log4j2.xml").toURI().toString();
        String configFile = getConfigFile();
        System.setProperty("log4j.configurationFile", configFile);
        Logger logger = LogManager.getRootLogger();

        //logger.info("${jndi:ldap://127.0.0.1:389/lalala}");
        
        System.console().format("Example attack string: ${jndi:ldap://127.0.0.1:389/lalala}\r\n");
        System.console().format("Please enter your name and press enter\r\n");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String name = reader.readLine();

        logger.info("Your name is {}", name);

        reader.close();
    }

    private static String getConfigFile() throws IOException {
        /*
        <?xml version=\"1.0\" encoding=\"UTF-8\"?>
        <Configuration status=\"WARN\">
            <Appenders>
                <Console name=\"LogToConsole\" target=\"SYSTEM_OUT\">
                    <PatternLayout pattern=\"%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n\"/>
                </Console>
            </Appenders>
            <Loggers>
                <Logger name=\"ConsoleLogger\" level=\"debug\" additivity=\"false\">
                    <AppenderRef ref=\"LogToConsole\"/>
                </Logger>
                <Root level=\"debug\">
                    <AppenderRef ref=\"LogToConsole\"/>
                </Root>
            </Loggers>
        </Configuration>
        */
        String configContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Configuration status=\"WARN\"><Appenders><Console name=\"LogToConsole\" target=\"SYSTEM_OUT\"><PatternLayout pattern=\"%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n\"/></Console></Appenders><Loggers><Logger name=\"ConsoleLogger\" level=\"debug\" additivity=\"false\"><AppenderRef ref=\"LogToConsole\"/></Logger><Root level=\"debug\"><AppenderRef ref=\"LogToConsole\"/></Root></Loggers></Configuration>";

        Path tempFile = Files.createTempFile(null, null);
        Files.write(tempFile, configContent.getBytes(StandardCharsets.UTF_8));

        return tempFile.toAbsolutePath().toString();
    }
}

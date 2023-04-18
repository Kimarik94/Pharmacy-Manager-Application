package ru.imp.platov.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProperties {
    public static Properties readProperties(){
        Properties props = new Properties();
        Path dbPropertiesPath = Paths.get("src/main/resources/database.properties");

        try{
            BufferedReader bufferedReader = Files.newBufferedReader(dbPropertiesPath, StandardCharsets.UTF_8);
            props.load(bufferedReader);
        }catch(IOException ex){
            Logger.getLogger(DatabaseProperties.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return props;
    }
}

package com.example.nusaht.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    public Logger logger;
    FileHandler fh;

    public Log(String filename) throws SecurityException, IOException
    {
        File file = new File(filename);
        if (file.exists()) {

             try
             {
               file.createNewFile();
               fh = new FileHandler(filename,true);
                 logger = Logger.getLogger("com.example.nusaht");
                 logger.addHandler(fh);
                 logger.setLevel(Level.ALL);
                 SimpleFormatter formatter = new SimpleFormatter();
                 fh.setFormatter(formatter);
             } catch (IOException | SecurityException ex)
            {
                  ex.printStackTrace();
            }

        }

    }
}

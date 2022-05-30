package es.joseluisgs.dam.examenmayo.utils;

import java.io.File;
import java.net.URI;

public class Properties {
    public static final String PATH = System.getProperty("user.dir");
    public static final String DATA_DIR = PATH + File.separator + "data";
    public static final String CSV_DIR = DATA_DIR + File.separator + "CSV";
    public static final String CSV_FILE = CSV_DIR + File.separator + "hogwarts.csv";
    public static final char CSV_SEPARATOR = ',';
}

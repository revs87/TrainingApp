package com.example.simpletextcomposeapplication;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirectoryListing {

   /*
       Java Print Files
        In the Java file, write a program to first create a file in the current directory with the name
        newfile.txt filled with any content. Then print out all the files in the current directory in
        alphabetical order so that they are in the following format: FILENAME, FILENAME, ...

       Example Output
        file.js, helloworld.txt, abc.txt
    */

    @Test
    public void test() {

        File folder = new File("./");
        File newFile = new File(folder, "newfile.txt");
        try {
            newFile.createNewFile();
        } catch (IOException e) {}

        File[] listOfFiles = folder.listFiles();
        List<String> listStr = new ArrayList<String>();
        for (int i=0; i<listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                listStr.add(listOfFiles[i].getName());
            }
        }
        Object[] listArray = listStr.toArray();
        Arrays.sort(listArray);

        for (int i=0; i<listArray.length; i++) {
            if (i == listArray.length-1) {
                System.out.println(listArray[i]);
            } else {
                System.out.print(listArray[i] + ", ");
            }
        }

        newFile.deleteOnExit();
    }
}

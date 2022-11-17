package main.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class backgroundModeParser {
    private static String readFileAsString(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }

    public static void main(String[] args) throws IOException {

        int countInsert = 0;
        String targetTextCode = readFileAsString("src/main/java/target.txt");
        List<String> targetList = Pattern.compile("\n")
                .splitAsStream(targetTextCode)
                .collect(Collectors.toList());


        String sourceTextCode = readFileAsString("src/main/java/source.txt");
        List<String> sourceList = Pattern.compile("\n")
                .splitAsStream(sourceTextCode)
                .collect(Collectors.toList());

        for (String strSource : sourceList) {
            if (strSource.contains(" name=") && strSource.contains("background.mode=")) {
                String substrName = strSource.substring(strSource.indexOf(" name=") + 1);
                String tempName = substrName.substring(substrName.indexOf("=") + 1, substrName.indexOf(" "));
                String substrMode = strSource.substring(strSource.indexOf("background.mode="));
                String numMode = substrMode.substring(substrMode.indexOf("=") + 2, substrMode.indexOf(" ") - 1);
                //System.out.println(tempName + " " + numMode);
                for (int i = 0; i < targetList.size(); i++) {
                    if (targetList.get(i).contains("\"" + tempName + "\"")) {
//                        System.out.println(targetList.get(i));
                        targetList.add(i + 1, "\tbackgroundMode: " + numMode + ",");
                        countInsert++;
                        break;
                    }
                }
            }
        }



        if(countInsert==0){
            for (String strSource : sourceList) {
                if (strSource.contains("name=") && strSource.contains("background.mode=")) {
                    String substrName = strSource.substring(strSource.indexOf("name=") + 1);
                    String tempName = substrName.substring(substrName.indexOf("=") + 1, substrName.indexOf(" "));
                    String substrMode = strSource.substring(strSource.indexOf("background.mode="));
                    String numMode = substrMode.substring(substrMode.indexOf("=") + 2, substrMode.indexOf(" ") - 1);
                    //System.out.println(tempName + " " + numMode);
                    for (int i = 0; i < targetList.size(); i++) {
                        if (targetList.get(i).contains("\"" + tempName + "\"")) {
//                        System.out.println(targetList.get(i));
                            targetList.add(i + 1, "\tbackgroundMode: " + numMode + ",");
                            countInsert++;
                            break;
                        }
                    }
                }
            }
        }

        targetList.forEach(System.out::println);

        System.out.println("\n\n\n\n\n" + countInsert);
    }
}

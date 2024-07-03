import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

class ImgAnalyzer
{
    public static void main(String[] args)
    {
        try
        {
            File imgFile = getFile();
            String path = imgFile.getAbsolutePath();
            analyzeImg(path);
        }
            catch (Exception e)
        {
            System.exit(-1);
        }
    }
        public static File getFile()
    {
        final JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
        
        // Only show image files by default
        FileNameExtensionFilter filter = new FileNameExtensionFilter("IMG FILES", "jpg", "png", "gif");
        fc.setFileFilter(filter);
        fc.setDialogTitle("Deep Search Image Analyzer");

        int returnVal = fc.showOpenDialog(null);
        
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            return fc.getSelectedFile();
        }

        else return null;
    }
    
    public static Scanner openToRead(File oldFile)
    {
        Scanner inFile = null;
        do
        {
            try
            {
                inFile = new Scanner(oldFile);
            }
            catch (FileNotFoundException fnfe)
            {
                System.out.println("Error Reading File");
            }
            
        } while (inFile == null);

        return inFile;
    }

    public static void analyzeImg(String imgPath) {
        //System.out.println(imgPath);
        ProcessBuilder processBuilder = new ProcessBuilder("python", "predict.py", imgPath);

        processBuilder.redirectErrorStream(true);
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            String results = "";
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                    try {
                        results= line;
                    }
                    catch(Exception e) {
                        continue;
                    }    
                }
                System.out.println(results);
    
                // Get the result as a string
                String result = output.toString();

            int exitCode = process.waitFor();
        }
        catch(Exception e) {
            System.out.println("broke");
        }
    }
}

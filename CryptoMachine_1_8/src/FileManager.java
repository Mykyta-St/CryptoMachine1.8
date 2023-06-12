import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager
{
    private Path inputFile;
    private Path outputFile;

    public FileManager (Path inputFile, String process, String inputText)
    {
        this.inputFile = inputFile;
        this.outputFile = createOutputFile(process);
        writeToOutputFile(inputText);
    }

    public static Path getFilepathIfFileExist (String fileToCheck)
    {
        Path filePath;
        try
        {
            String currentPath = new java.io.File(".").getCanonicalPath();
            if (fileToCheck.contains("/") || fileToCheck.contains("\\"))
            {
                filePath = Paths.get(fileToCheck);
            } else
            {
                filePath = Paths.get(currentPath, fileToCheck);
            }
            if (filePath.toFile().exists())
            {
                return filePath;
            }
            else
            {
                System.out.println("Input file doesn't exist");
                return null;
            }
        }catch (Exception e)
        {
            return null;
        }
    }
    public static boolean checkIfEmptyFile(Path inputFile)
    {
        try
        {
            if (inputFile.toFile().length() == 0)
            {
                System.out.println("Inputfile is empty");
                return true;
            }
            else
            {
                return false;
            }
        } catch (NullPointerException e)
        {
            return true;
        }
    }

    public static String getStringFromFile(Path inputFile)
    {
        StringBuilder stringBuilder = new StringBuilder();
        try(FileReader inputFileReader = new FileReader(inputFile.toFile());
            BufferedReader reader = new BufferedReader(inputFileReader))
        {
            while (reader.ready())
            {
                stringBuilder.append(reader.readLine()).append("\n");
            }
        return stringBuilder.toString();
        } catch (IOException e)
        {
            System.out.println("Error reading file!");
            return null;
        }
    }

    public void writeToOutputFile (String outputText)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile.toFile())))
        {
            writer.write(outputText);
        } catch (Exception e)
        {
            System.out.println("Error while writing to file");
        }
    }

    private Path createOutputFile (String process)
    {
        return Paths.get(newFileName(process));
    }

    private String newFileName(String process)
    {
        if (process.equals("encrypt"))
        {
            return "[ENCRYPTED]" + inputFile.toFile().getName();
        } else
        {
            return "[DECRYPTED]" + inputFile.toFile().getName();
        }
    }

    public Path getOutputFile ()
    {
        return this.outputFile;
    }
}

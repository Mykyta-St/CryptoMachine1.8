import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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
        //System.out.println(outputFile);
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
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(outputFile.toFile(), "rw");
             FileChannel channel = randomAccessFile.getChannel() )
        {
            ByteBuffer byteBuffer = ByteBuffer.allocate(outputText.getBytes().length);
            byteBuffer.put(outputText.getBytes(StandardCharsets.UTF_8));
            byteBuffer.flip();
            channel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
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

    /*
    private String decryptedFileName(Path fileName)
    {
        String currentFileName = fileName.getFileName().toString();

        if (currentFileName.contains("[ENCRYPTED]") || currentFileName.contains("[DECRYPTED]"))
        {
            int index = currentFileName.indexOf("[ENCRYPTED]")+11;
            return "[DECRYPTED]" + currentFileName.substring(index);
        }
        else return "[DECRYPTED]" + currentFileName;
    }

    private static StringBuilder getStringFromBytes(Charset charset, FileChannel fileChannel) throws IOException
    {
        byte [] byteArray = new byte [byteArraySize];
        StringBuilder stringBuilder = new StringBuilder();
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray, 0, byteArray.length);
        CharBuffer charBuffer = CharBuffer.allocate(byteArray.length / 2);
        int bytesRead;
        int iteration = 1;
        byte [] bytes;

        while ((bytesRead = fileChannel.read(byteBuffer)) >= 0)
        {
            fileChannel.read(byteBuffer);
            bytes = byteBuffer.array();
            if ((bytes[bytes.length-1] == -48)||(bytes[bytes.length-1] == -47))
            {
                byteBuffer.flip();
                byteBuffer.limit(byteBuffer.capacity()-1);
                charBuffer = charset.decode(byteBuffer);
                byteBuffer.clear();
                byteBuffer.put(bytes[bytes.length-1]);
            } else
            {
                charBuffer = charset.decode(byteBuffer.flip());
                byteBuffer.clear();
            }
            stringBuilder.append(charBuffer);
        }
        return stringBuilder;
    }
    */
}

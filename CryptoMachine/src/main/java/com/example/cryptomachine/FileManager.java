package com.example.cryptomachine;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class FileManager
{
    private Path inputFile;
    private Path outputFile;

    private static final int byteArraySize = 2*3*24;

    public String getInputText(Path inputFile)
    {
        this.inputFile = inputFile;
        Charset charset = StandardCharsets.UTF_8;
        StringBuilder stringBuilder = new StringBuilder();

        try(RandomAccessFile inputDataFile = new RandomAccessFile(inputFile.toFile(), "r");
            FileChannel readChannel = inputDataFile.getChannel())
        {

            stringBuilder.append(getStringFromBytes(charset, readChannel));

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return stringBuilder.toString().toLowerCase();
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
    public void createOutputFile (String outputText, String process, Language language)
    {
        Path directory = inputFile.getParent();
        if (process.equals("encrypt")) {
                outputFile = Path.of(directory.toString(), encryptedFileName(inputFile));
        }
        else {
            outputFile = Path.of(directory.toString(), decryptedFileName(inputFile));
        }
        try {
            if (!outputFile.toFile().createNewFile()) {
                outputFile.toFile().delete();
            } else outputFile.toFile().createNewFile();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(outputFile.toFile(), "rw");
             FileChannel channel = randomAccessFile.getChannel()
            )
        {

            ByteBuffer byteBufferr = ByteBuffer.allocate(outputText.getBytes().length);
            byteBufferr.put(outputText.getBytes(StandardCharsets.UTF_8));
            byteBufferr.flip();
            channel.write(byteBufferr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String encryptedFileName(Path fileName)
    {
        String currentFileName = fileName.getFileName().toString();

        if (currentFileName.contains("[ENCRYPTED]") || currentFileName.contains("[DECRYPTED]"))
        {
           int index = currentFileName.indexOf("[ENCRYPTED]")+11;
           return "[ENCRYPTED]" + currentFileName.substring(index);
        }
        else return "[ENCRYPTED]" + currentFileName;
    }
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
}

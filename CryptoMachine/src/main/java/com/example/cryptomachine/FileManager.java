package com.example.cryptomachine;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.Path;

public class FileManager
{
    private File inputFile;
    private File outputFile;

    Encryptor encryptor;

    public String getInputText(File inputFile)
    {
        this.inputFile = inputFile;
        Charset charset = Charset.forName("UTF-8");
        StringBuilder sB = new StringBuilder();

        try(RandomAccessFile inFile = new RandomAccessFile(inputFile.getName(), "r");
            FileChannel readChannel = inFile.getChannel();)
        {

            sB.append(getStringFromBytes(charset, readChannel));

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return sB.toString().toLowerCase();
    }

    private static StringBuilder getStringFromBytes(Charset charset, FileChannel fileChannel) throws IOException
    {
        byte [] byteArray = new byte [2*3*24];
        StringBuilder sb = new StringBuilder();
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray, 0, byteArray.length);
        CharBuffer charBuffer = CharBuffer.allocate(byteArray.length / 2);
        int bytesRead = 0;
        int iteration = 1;
        byte [] bytes;

        while ((bytesRead = fileChannel.read(byteBuffer)) >= 0)
        {
            System.out.println("Iteration: " + iteration + " Bytes read: " + bytesRead);
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
            System.out.println();
            sb.append(charBuffer);
        }
        System.out.println(sb);
        return sb;
    }
    public void createOutputFile (String outputText, String process, Language language)
    {File directory = inputFile.getParentFile();
        if (process.equals("encrypt")) {
            if (language == Language.ENGLISH) outputFile = new File(directory, "encryptedFileEN.txt");
            else if (language == Language.UKRAINIAN) outputFile = new File(directory, "encryptedFileUA.txt");
            else if (language == Language.RUSSIAN) outputFile = new File(directory, "encryptedFileRU.txt");
        }
        else {
            if (language == Language.ENGLISH) outputFile = new File(directory,"DEcryptedFileEN.txt");
            else if (language == Language.UKRAINIAN) outputFile = new File(directory,"DEcryptedFileUA.txt");
            else if (language == Language.RUSSIAN) outputFile = new File(directory,"DEcryptedFileRU.txt");
        }
        try {
            if (!outputFile.createNewFile()) {
                outputFile.delete();
            } else outputFile.createNewFile();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(outputFile.getName(), "rw");
             FileChannel channel = randomAccessFile.getChannel();
            )
        {

            ByteBuffer byteBufferr = ByteBuffer.allocate(outputText.getBytes().length);
            byteBufferr.put(outputText.getBytes("UTF-8"));
            byteBufferr.flip();
            channel.write(byteBufferr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

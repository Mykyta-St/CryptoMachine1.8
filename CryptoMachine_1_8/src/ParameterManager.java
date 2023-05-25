import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class ParameterManager
{
    private ArrayList<String> inputArguments;
    private String process;
    private Language language;
    private String inputText;
    private Path inputFile;
    private int shift = 0;
    private Path outputFile;
    private String outputText;

    ParameterManager()
    {
        setProcess("");
        setLanguage(null);
        setShift(0);
        setInputText("");
        setInputFile(null);
        getParametersIfNull();
    }

    public ParameterManager(String [] arguments)
    {
        this.inputArguments = getInputArguments(arguments);
        System.out.println("Input arguments: " + this.inputArguments);
        setProcess("");
        setShift(0);
        setInputText("");
        setInputFile(null);
        checkArguments(this.inputArguments);
        getParametersIfNull();
    }

    private ArrayList<String> getInputArguments(String[] arguments) {
        ArrayList<String> inputArguments = new ArrayList<>();
        int textCount = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arguments.length; i++)
        {
            if (textCount == 1) sb.append(' ');
            for (int j = 0; j < arguments[i].length(); j++)
            {
                if (arguments[i].charAt(j) == '"')
                {
                    if (textCount == 0)
                    {
                        textCount = 1;
                    }
                    else
                    {
                        textCount = 0;
                        inputArguments.add(sb.toString());
                        sb.setLength(0);
                    }
                } else
                    sb.append(arguments[i].charAt(j));
            }
            if (textCount == 0)
            {
                inputArguments.add(sb.toString());
                sb.setLength(0);
            }
        }
        return inputArguments;
    }

    private void checkArguments (ArrayList <String> arguments)
    {
        for (int i = 0; i < arguments.size(); i++)
        {
            switch (i)
            {
                case 0:
                    if (checkProcessParameter(arguments.get(i)))
                    {
                        System.out.println("Process parameter obtained: " + getProcess());
                    } else if (checkFileParameter(arguments.get(i)))
                    {
                        System.out.println("Filepath parameter obtained: " + getInputFile());
                    } else if (checkShiftParameter(arguments.get(i)))
                    {
                        System.out.println("Key parameter obtained: " + getShift());
                    }
                    break;
                case 1:
                    if (checkFileParameter(arguments.get(i)))
                    {
                        System.out.println("Filepath parameter obtained: " + getInputFile());
                    } else if (checkShiftParameter(arguments.get(i)))
                    {
                        System.out.println("Key parameter obtained: " + getShift());
                    }else if (checkProcessParameter(arguments.get(i)))
                    {
                        System.out.println("Process parameter obtained: " + getProcess());
                    }
                    break;
                case 2:
                    if (checkShiftParameter(arguments.get(i)))
                    {
                        System.out.println("Key parameter obtained: " + getShift());
                    }
                    break;
                default:
                    System.out.println((i+1) + "th parameter is not needed. Only 3 parameters are required.");
            }
        }
    }

    private boolean checkProcessParameter (String parameter) {
        switch (parameter)
        {
            case "[encrypt]":
            case "[ENCRYPT]":
            case "encrypt":
                setProcess("encrypt");
                return true;
            case "[decrypt]":
            case "[DECRYPT]":
            case "decrypt":
                setProcess("decrypt");
                return true;
            default:
                return false;
        }
    }

    private boolean checkFileParameter (String parameter) {
        try
        {
            Path pathFile = FileManager.getFilepathIfFileExist(parameter);
            if (!FileManager.checkIfEmptyFile(pathFile))
            {
                setInputFile(pathFile);
                setInputText(FileManager.getStringFromFile(this.inputFile));
                return true;
            } else
            {
                System.out.println("input file is empty");
                return false;
            }
        } catch (Exception e)
        {
            System.out.println("Inputfile parameter input incorrectly");
            return false;
        }
    }

    private boolean checkShiftParameter (String parameter) {
        try
        {
            int shift = Integer.parseInt(parameter);
            if (shift != 0)
            {
                setShift(shift);
                return true;
            }
            else return false;
        } catch (Exception e)
        {
            System.out.println("Shift parameter was input incorrectly");
            //e.printStackTrace();
            return false;
        }
    }

    private void getParametersIfNull () {
        Scanner scanner = new Scanner(System.in);
        String scannerInput;
        if (this.process.equals("")) {
            System.out.println("Choose operation.\n" +
                    "For encryption input [encrypt] for decryption input [decrypt].");
            scannerInput = scanner.nextLine();
            while (!checkProcessParameter(scannerInput) && !scannerInput.equals("exit")) {
                System.out.println("Enter [encrypt] or [decrypt]");
                scannerInput = scanner.nextLine();
            }
            if (scannerInput.equals("exit")) System.exit(0);
        }
        if (this.shift == 0) {
            System.out.println("Choose shift of alphabet symbols. Input number.");
            scannerInput = scanner.nextLine();
            while (!checkShiftParameter(scannerInput) && !scannerInput.equals("exit")) {
                System.out.println("Enter number");
                scannerInput = scanner.nextLine();
            }
            if (scannerInput.equals("exit")) System.exit(0);
        }
        if (this.inputFile == null)
        {
            System.out.println("Input file name or path to file that you want to input text from:");
            scannerInput = scanner.nextLine();
            while (!checkFileParameter(scannerInput) && !scannerInput.equals("exit"))
            {
                System.out.println("File doesn't exit or is empty! Input new file name or path to file.");
                scannerInput = scanner.nextLine();
            }
            if (scannerInput.equals("exit")) System.exit(0);
            setInputText(FileManager.getStringFromFile(this.inputFile));
        }
        scanner.close();
        System.out.println("\nAll required parameters were obtained\n");
    }

    @Override
    public String toString()
    {
        return "Process chosen: " + this.process
                + "\nShift chosen: " + this.shift
                + "\nInputfile chosen: " + this.inputFile
                + "\nInput text:\n" + this.inputText;
    }

    private void setProcess (String process)
    {
        this.process = process;
    }

    private void setLanguage (Language language)
    {
        this.language = language;
    }

    private void setInputFile (Path fileName)
    {
        this.inputFile = fileName;
    }

    private void setInputText (String text)
    {
        this.inputText = text;
    }

    private void setShift (int shift)
    {
        this.shift = shift;
    }

    public String getProcess ()
    {
        return this.process;
    }

    public Path getInputFile ()
    {
        return this.inputFile;
    }

    public String getInputText ()
    {
        return this.inputText;
    }

    public int getShift ()
    {
        return this.shift;
    }
}


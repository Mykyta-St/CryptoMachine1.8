import java.util.Scanner;

public class Runner {
    private ParameterManager parameterManager;
    private FileManager fileManager;
    private Encryptor encryptor;

    public Runner(String[] arguments) {
        if (arguments.length == 0) {
           parameterManager = new ParameterManager();
        } else
           parameterManager = new ParameterManager(arguments);

        runProcess( parameterManager.getProcess(),
                    parameterManager.getInputText(),
                    parameterManager.getShift());
    }

    private void runProcess(String process, String inputText, int shift)
    {
        encryptor = new Encryptor(process, inputText, shift);
        switch (process) {
            case "encrypt":
                System.out.println("Plain text obtained from file:\n" +
                        parameterManager.getInputFile() + "\n" + encryptor.getPlainText());
                fileManager = new FileManager(parameterManager.getInputFile(),
                                                parameterManager.getProcess(),
                                                    encryptor.getEncryptedText());
                System.out.println("Encrypted text written to file:\n" +
                        fileManager.getOutputFile() + "\n" + encryptor.getEncryptedText());
                break;
            case "decrypt":
                System.out.println("Encrypted text obtained from file:\n" +
                        parameterManager.getInputFile() + "\n" + encryptor.getEncryptedText());
                fileManager = new FileManager(parameterManager.getInputFile(),
                                                parameterManager.getProcess(),
                                                    encryptor.getPlainText());
                System.out.println("Plain text was written to file:\n" +
                        fileManager.getOutputFile() + "\n" + encryptor.getPlainText());
                break;
            default:
                System.out.println("Process is not specified!!!");
                break;
        }
        repeat();
    }
    public void repeat()
    {
        System.out.println("Do you want to continue operation. Make your choise [yes] or [no]");
        System.out.println("Input [exit] anytime to close the application");
        Scanner sc = new Scanner(System.in);
        String choise = sc.nextLine();
        while (!toContinue (choise)) {
            System.out.println("make your choise (yes/no):");
            choise = sc.nextLine();
        }
        parameterManager.askForParametersChange();
        runProcess( parameterManager.getProcess(),
                    parameterManager.getInputText(),
                    parameterManager.getShift());
        System.out.println();
    }

    private boolean toContinue (String answer)
    {
        switch (answer)
        {
            case "y":
            case "yes":
                return true;
            case "n":
            case "no":
            case "exit":
                System.exit(0);
            default :
                return false;
        }

    }
}

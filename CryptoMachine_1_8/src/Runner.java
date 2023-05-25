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
                System.out.println("Plain text obtained:\n" + encryptor.getPlainText());
                fileManager = new FileManager(parameterManager.getInputFile(),
                                                parameterManager.getProcess(),
                                                    encryptor.getEncryptedText());
                System.out.println("Encrypted text written:\n" + encryptor.getEncryptedText());
                break;
            case "decrypt":
                System.out.println("Encrypted text obtained:\n" + encryptor.getEncryptedText());
                fileManager = new FileManager(parameterManager.getInputFile(),
                                                parameterManager.getProcess(),
                                                    encryptor.getPlainText());
                System.out.println("Plain text written:\n" + encryptor.getPlainText());
                break;
            default:
                System.out.println("Process is not specified!!!");
                break;
        }
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

    public void repeat()
    {
        Scanner sc = new Scanner(System.in);
        while (!toContinue (sc.nextLine())) {
            System.out.println("make your choise (yes/no):");
        }
        System.out.println();
        //parameterManager.askForParametersChange();
    }

}

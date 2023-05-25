import java.util.ArrayList;

public class Encryptor
{
    //алфавіт буде складатися тільки із літер відповідної мови (англійська, російська, українська)
    //а також знаків пунктуації
    //всі числа та інщі знаки при кодуванні і декодуванні будуть замінені на знак # (решітка)
    private static String plainText;
    private static Language language;
    private static Alphabet alphabet;
    private static String encryptedText;
    private String process;
    private int shift;

    public Encryptor (String process, String inputText, int shift)
    {
        setLanguage(getLanguage(inputText));
        System.out.println("Language was identified as: "+ this.language);
        alphabet = new Alphabet(language);
        setShift(shift);
        switch (process)
        {
            case "encrypt":
                setPlainText(inputText);
                setProcess(process);
                setEncryptedText(null);
                encryptSezar();
                break;
            case "decrypt":
                setEncryptedText(inputText);
                setProcess(process);
                setPlainText(null);
                decryptCeasar();
                break;
        }
    }

    private Language getLanguage (String text)
    {
        int englishLettersCount = 0;
        int ukrainianLettersCount = 0;
        //int russianLettersCount = 0;
        char [] alphabet = Alphabet.getAlphabetCharacters(Language.ENGLISH);
        ArrayList<Character> inputText = new ArrayList<>();
        for (char c: text.toCharArray())
        {
            inputText.add(c);
        }
        int index = -1;
        for (char character: alphabet)
        {
            if ((index = inputText.indexOf(character)) >= 0)
            {
                inputText.remove(index);
                englishLettersCount++;
            }
        }
        alphabet = Alphabet.getAlphabetCharacters(Language.UKRAINIAN);
        for (char character: alphabet)
        {
            if ((index = inputText.indexOf(character)) >= 0)
            {
                inputText.remove(index);
                ukrainianLettersCount++;
            }
        }
        return
                (englishLettersCount > ukrainianLettersCount) ? Language.ENGLISH : Language.UKRAINIAN;
    }

    public void encryptSezar()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < plainText.length(); i++)
        {
            char sign =plainText.charAt(i);
            sb.append(processSign(sign, this.shift));
        }
        setEncryptedText(sb.toString());
    }

    private static char processSign(char sign, int encryptionShift)
    {
        if (sign == '\uFEFF'){
            return sign;
        }
        else if (sign == '\r')
        {
            return sign;
        }
        else if (sign == '\n')
        {
            return sign;
        }
        else
        {
            sign = getNewSign(sign, encryptionShift);
            return sign;
        }
    }

    //getting new sign by shifting original sign in the dictionary on "shift" number of letters
    private static char getNewSign(char sign, int encryptionShift)
    {
        int originalIndex = alphabet.getAlphabet().indexOf(sign);
        int newIndex;
        if (originalIndex != -1)
        {
            encryptionShift = encryptionShift % alphabet.getAlphabetSize();
            if (encryptionShift > 0)
            {
                if ((originalIndex + encryptionShift) > (alphabet.getAlphabetSize() - 1))
                {
                    newIndex = (encryptionShift + originalIndex) - (alphabet.getAlphabetSize() - 1);
                } else
                {
                    newIndex = encryptionShift + originalIndex;
                }
            }else
            {
                if ((originalIndex + encryptionShift) < 0)
                {
                    newIndex = (encryptionShift + originalIndex) + (alphabet.getAlphabetSize() - 1);
                } else
                {
                    newIndex = encryptionShift + originalIndex;
                }
            }
            //sign = alphabet.getAlphabet().get(newIndex);
            //System.out.print("Encryped sign: " + sign + "\n");
        } else return sign;
        return alphabet.getAlphabet().get(newIndex);
    }

    //1. making arraylist of all possible decrypted texts by iteration all possible shifts
    //2. checking each possible decrypted text for containing of commonly used words from vocabulary
    //3. the one possible decrypted text with the biggest amount of matches becomes Plain text

    public void decryptCeasar ()
    {
        decryptWithBrutForce(encryptedText);
    }



    private void decryptWithBrutForce(String encryptedText)
    {
        setPlainText(getBestMatchText(getPossibleDeCryptedTexts(encryptedText)));
        //setPlainText(deCryptWithKnownShift(encryptedText,SHIFT));
    }



    //by iterating of shift through all the alphabet, getting decrypted text variants
    private ArrayList<String> getPossibleDeCryptedTexts(String encryptedText)
    {
        ArrayList<String> possibleDecryptedTexts = new ArrayList<>();
        for (int possibleShift = 0; possibleShift < alphabet.getAlphabetSize(); possibleShift++)
        {
            possibleDecryptedTexts.add(decryptSezar(possibleShift, encryptedText));
        }
        return possibleDecryptedTexts;
    }

    //using the same algorithm as encryption method, but for encrypted text
    private String decryptSezar(int possibleShift, String encryptedText)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < encryptedText.length(); i++)
        {
            char sign =encryptedText.charAt(i);
            sb.append(processSign(sign, possibleShift));
        }
        return sb.toString();
    }

    //returning only one variant (containing the biggest amount of commonly used words/signs)
    // out of all decrypted text variants
    private String getBestMatchText(ArrayList<String> possibleDecryptedTexts)
    {
        int variantIndexWithBestMatch = -1;
        int maxMatches = -1;

        for (int i = 0; i < possibleDecryptedTexts.size(); i++)
        {
            int matchesfound = getMatchesInText(possibleDecryptedTexts.get(i));
            if (matchesfound>0)
            {
                if (matchesfound > maxMatches) {
                    maxMatches = matchesfound;
                    variantIndexWithBestMatch = i;
                }
            }
        }
        if (variantIndexWithBestMatch>=0) return possibleDecryptedTexts.get(variantIndexWithBestMatch);
        else
        {
            this.setEncryptedText(null);
            this.setPlainText(null);
            return "it is not possible to decrypt this text";
        }
    }

    //checking that encrypted text contains some commonly used words/signs from vocabulary
    private static int getMatchesInText (String possibleDecryptedText)
    {
        int wordsFound = 0;
        for (String s : alphabet.getVocabulary())
        {
            if (possibleDecryptedText.contains(s))
                    wordsFound++;
        }
        return wordsFound;
    }
    public String getEncryptedText()
    {
        return encryptedText;
    }
    public String getPlainText()
    {
        return plainText;
    }
    private void setLanguage (Language language)
    {
        this.language = language;
    }
    private void setPlainText(String inputText)
    {
        plainText = inputText;
    }
    private void setEncryptedText(String text)
    {
        encryptedText = text;
    }
    private void setShift (int shift)
    {
        this.shift = shift;
    }
    private void setProcess (String process)
    {
        this.process = process;
    }

    /*
    public void encryptSezar(int shift)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < plainText.length(); i++)
        {
            char sign =plainText.charAt(i);
            sb.append(processSign(sign, shift));
        }
        setEncryptedText(sb.toString());
    }

    public String decrypt ()
    {
        return decryptWithBrutForce(encryptedText);
    }

    private String decryptWithBrutForce(String encryptedText)
    {
        setPlainText(getBestMatchText(getPossibleDeCryptedTexts(encryptedText)));
        //setPlainText(deCryptWithKnownShift(encryptedText,SHIFT));
        return plainText;
    }


    //created method indexOf, because method Arrays.binarySearch was not able to find symbols correctly

    private static int indexOf(char[] array, char sign)
    {
        for (int i = 0; i < array.length; i++)
        {
            if (sign == array[i]) return i;
        }
        return -1;
    }
    */

    /*
    //getting decrypted symbols as opposite shifting the character in volabulary.
    //not used method
    public static String deCryptWithKnownShift(String encryptedText, int knownShift)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < encryptedText.length(); i++) {
            char sign = encryptedText.charAt(i);

            if (sign == '\uFEFF') {
                //System.out.print("Plain sign: '\\uFEFF'\tEncryped sign: '\\uFEFF' \n");
            } else if (sign == '\r') {
                //System.out.println("Plain text: '\\r'\tEncryped sign: '\\r'\n");
            } else if (sign == '\n') {
                //System.out.println("Plain text: '\\n'\tEncryped sign: '\\n'\n");
            } else {
                //System.out.print("plaintext sign: " + sign + "\t");
                int originalIndex = indexOf(alphabet.getAlphabet(), sign);
                if (originalIndex == -1) {
                    sign = '#';
                    //System.out.print("Encryped sign: " + sign + "\n");
                } else {
                    int newIndex;
                    if ((originalIndex - knownShift) < 0)
                        newIndex = alphabet.getAlphabetSize() - 1 + (originalIndex - knownShift);
                    else newIndex = originalIndex - knownShift;
                    sign = alphabet.getAlphabet()[newIndex];
                    //System.out.print("Encryped sign: " + sign + "\n");
                }
            }
            sb.append(sign);
        }
        return sb.toString();
    }
    */
}

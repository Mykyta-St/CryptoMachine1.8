package com.example.cryptomachine;

import java.util.ArrayList;
public class Encryptor
{
    //алфавіт буде складатися тільки із літер відповідної мови (англійська, російська, українська)
    //а також знаків пунктуації
    //всі числа та інщі знаки при кодуванні і декодуванні будуть замінені на знак # (решітка)
    private static String plainText;
    private static Alphabet alphabet;
    private static String encryptedText;

    public Encryptor (Language language)
    {
        alphabet = new Alphabet(language);
    }
    private static final int SHIFT =35;

    public void setPlainText(String inputText)
    {
        plainText = inputText;
    }

    public String encryptSezar()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < plainText.length(); i++)
        {
            char sign =plainText.charAt(i);
            sb.append(processSign(sign, SHIFT));
        }
        encryptedText = sb.toString();
        //System.out.println(encryptedText);
        return encryptedText;
    }

    //
    private static char processSign(char sign, int encryptionShift)
    {
        if (sign == '\uFEFF')
        {
                //System.out.print("Plain sign: '\\uFEFF'\tEncryped sign: '\\uFEFF' \n");
        }
        else if (sign == '\r')
        {
            //System.out.println("Plain text: '\\r'\tEncryped sign: '\\r'\n");
        }
        else if (sign == '\n')
        {
            //System.out.println("Plain text: '\\n'\tEncryped sign: '\\n'\n");
        }
        else
        {
            sign = getNewSign(sign, encryptionShift);
        }
        return sign;
    }

    //getting new sign by shifting original sign in the dictionary on "shift" number of letters
    private static char getNewSign(char sign, int encryptionShift)
    {
        //System.out.print("plaintext sign: " + sign + "\t");
        int originalIndex = indexOf(alphabet.getAlphabet(), sign);
        if (originalIndex == -1)
        {
            sign = '#';
            //System.out.print("Encryped sign: " + sign + "\n");
        } else
        {
            int newIndex = 0;
            if ((originalIndex + encryptionShift) >= alphabet.getAlphabetSize() - 1)
                newIndex = encryptionShift - (alphabet.getAlphabetSize() - 1 - originalIndex);
            else newIndex = originalIndex + encryptionShift;
            sign = alphabet.getAlphabet()[newIndex];
            //System.out.print("Encryped sign: " + sign + "\n");
        }
        return sign;
    }

    //1. making arraylist of all possible decrypted texts by iteration all possible shifts
    //2. checking each possible decrypted text for containing of commonly used words from vocabulary
    //3. the one possible decrypted text with the biggest amount of matches becomes Plain text
    public String decryptWithBrutForce(String encryptedText)
    {
        setPlainText(getBestMatchText(getPossibleDeCryptedTexts(encryptedText)));
        //setPlainText(deCryptWithKnownShift(encryptedText,SHIFT));
        return plainText;
    }

    //by iterating of shift through all the alphabet, getting decrypted text variants
    public static ArrayList<String> getPossibleDeCryptedTexts(String encryptedText)
    {
        ArrayList<String> possibleDecryptedTexts = new ArrayList<>();
        for (int possibleShift = 0; possibleShift < alphabet.getAlphabetSize(); possibleShift++)
        {
            possibleDecryptedTexts.add(decryptSezar(possibleShift));
        }
        return possibleDecryptedTexts;
    }

    //using the same algorithm as encryption method, but for encrypted text
    public static String decryptSezar(int possibleShift)
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
    private static String getBestMatchText(ArrayList<String> possibleDecryptedTexts)
    {
        int variantIndexWithBestMatch = -1;
        int maxMatches = -1;

        for (int i = 0; i < possibleDecryptedTexts.size(); i++)
        {
            int matchesfound = getMatchesInText(possibleDecryptedTexts.get(i));
            if (matchesfound>0)
            if (matchesfound>maxMatches)
            {
                maxMatches = matchesfound;
                variantIndexWithBestMatch = i;
            }
        }
        if (variantIndexWithBestMatch>=0) return possibleDecryptedTexts.get(variantIndexWithBestMatch);
        else return "it is not possible to decrypt this text";
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

    public void setEncryptedText(String text)
    {
        this.encryptedText = text;
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

    //getting decrypted symbols as opposite shifting the character in volabulary.
    //not used method
    public static String deCryptWithKnownShift(String encryptedText, int knownShift)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < encryptedText.length(); i++)
        {
            char sign =encryptedText.charAt(i);

            if (sign == '\n')
            {
                sb.append(sign);
                //System.out.println("Encrypted text: '\\n' Plain text '\\n'");
                i=i+1;
                continue;
            }
            int originalIndex = indexOf(alphabet.getAlphabet(),sign);
            if (originalIndex == -1)
            {
                //System.out.print("Encrypted text: " + sign + " ");
                sign = '#';
                //System.out.println("Plain text: " + sign);
            }
            else
            {
                int newIndex = 0;
                if ((originalIndex-knownShift) < 0)
                    newIndex = alphabet.getAlphabetSize()-1 +(originalIndex-knownShift);
                else newIndex = originalIndex-knownShift;

//                System.out.print("Plain text: " + sign + " ");
                sign = alphabet.getAlphabet()[newIndex];
//                System.out.println("Encrypted text: " + sign);

            }
            sb.append(sign);
        }
        return sb.toString();
    }
}

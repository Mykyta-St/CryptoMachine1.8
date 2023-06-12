package com.example.cryptomachine;

import java.util.ArrayList;
import java.util.List;

public class Alphabet
{
    private List<Character> alphabet;
    private int alphabetSize;
    private String [] vocabulary;
    Alphabet (Language language)
    {
        //this.language = language;
        initializeAlphabet(language);
    }

    public List <Character> getAlphabet ()
    {
        return alphabet;
    }

    public int getAlphabetSize ()
    {
        return alphabetSize;
    }

    //public Language getLanguage ()
    //{
    //    return language;
    //}

    //initializing corresponding set of alphabetic symbols
    // after getting corresponding Language parameter
    private void initializeAlphabet(Language language)
    {
        //this.alphabet = new ArrayList <Character>();
        if (language == Language.ENGLISH) {
            setAlphabet(englishAlphabet, englishVocabulary);
        } else if (language == Language.RUSSIAN) {
            setAlphabet(russianAlphabet, russianVocabulary);
        } else if (language == Language.UKRAINIAN) {
            setAlphabet(ukrainianAlphabet, ukrainianVocabulary);

         /*
            this.alphabet = russianAlphabet;
            this.alphabetSize = alphabet.length;
            this.vocabulary = russianVocabulary;
        } else if (language == Language.UKRAINIAN)
        {
            this.alphabet = ukrainianAlphabet;
            this.alphabetSize = alphabet.length;
            this.vocabulary = ukrainianVocabulary;
         */

        }else throw new IllegalArgumentException("program doesn't support such language");
    }

    private void setAlphabet(char [] alphabet, String [] vocabulary) {
        this.alphabet = new ArrayList<>();
        for (char c: alphabet)
        {
            this.alphabet.add(c);
        }
        this.alphabetSize = this.alphabet.size();
        this.vocabulary = vocabulary;
    }

    public String [] getVocabulary ()
    {
        return vocabulary;
    }

    //набір символів трьох абеток задля ініціалізації абетки для кодування
    private final char [] englishAlphabet = {
            'a','b','c','d','e','f','g','h','i','j',
            'k','l','m','n','o','p','q','r','s','t','u',
            'v','w','x','y','z','.',',','!','?',' ','"',':','-','—'
    };
    private final char [] russianAlphabet = {
            'а','б','в','г','д','е','ё','ж','з','и',
            'й','к','л','м','н','о','п','р','с','т',
            'у','ф','х','ц','ч','ш','щ','ь','ы','ъ',
            'э','ю','я','.',',','!','?',' ','"',':','–','—'
    };
    private final char [] ukrainianAlphabet = {
            'а','б','в','г','д','е','є','ж','з','и','й',
            'і','ї','к','л','м','н','о','п','р','с',
            'т','у','ф','х','ц','ч','ш','щ','ь',
            'ю','я','.',',','!','?',' ', '"',':','-','—'
    };

    //набір слів широко вживаних у різних мовах. за навністю цих слів у декодованому рядку можна зробити висновок,
    //що рядок декодовано правильно
    private final String [] englishVocabulary = {
            " a ", " the ",
            " is ", " be ", " am ", " are ", " was ", " were ",
            " i ", " you ", " he ", " she ", " it ", " we "," they ",
            " my ", " your ", " his ", " her ", " its ", " our ", " their",
            " me ", " him ", " us ", " them ",
            ". ", ", ", ": ", " - ", "! ", "? "
    };
    private final String [] russianVocabulary = {
            " быть ", " есть ", " был ", " были ", " буду ", " будет ", " будешь ", " будут ",
            " я ", " ты ", " он ", " она ", " оно ", " мы "," они ",
            " мой ", " твой ", " его ", " её ", " наш ", " их ",
            " моего ", " твоего ", " нашего ", " меня ", " тебя ", " нас ",
            " мне ", " тебе ", " ему ", " ей ", " нам ", " им ",
            ". ", ", ", ": ", " - ", "! ", "? "
    };
    private final String [] ukrainianVocabulary = {
            " є ", " був ", " були ", " буду ", " буде ", " будеш ", " будуть ",
            " я ", " ти ", " він ", " вона ", " воно ", " ми "," вони ",
            " мій ", " твій ", " його ", " її ", " наш ", " їх ",
            " мого ", " твого ", " нашого ", " мене ", " тебе ", " нас ",
            " мені ", " тобі ", " йому ", " їй ", " нам ", " їм ",
            ". ", ", ", ": ", " - ", "! ", "? "
    };
}

enum Language
{
    ENGLISH,
    RUSSIAN,
    UKRAINIAN
}

import java.util.ArrayList;

public class Alphabet
{
    private ArrayList <Character> alphabet;
    private int alphabetSize;
    private String [] vocabulary;
    Alphabet (Language language)
    {
        initializeAlphabet(language);
    }

    public ArrayList <Character> getAlphabet ()
    {
        return alphabet;
    }

    public int getAlphabetSize ()
    {
        return alphabetSize;
    }

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
        } else throw new IllegalArgumentException("program doesn't support such language");
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
    private static final char [] englishAlphabet = {
            'A','B','C','D','E','F','G','H','I','J',
            'K','L','M','N','O','P','Q','R','S','T',
            'U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i','j',
            'k','l','m','n','o','p','q','r','s','t',
            'u','v','w','x','y','z',
            '.',',','!','?',' ','"',':','-','—'
    };
    private static final char [] russianAlphabet = {
            'а','б','в','г','д','е','ё','ж','з','и',
            'й','к','л','м','н','о','п','р','с','т',
            'у','ф','х','ц','ч','ш','щ','ь','ы','ъ',
            'э','ю','я','.',',','!','?',' ','"',':','–','—'
    };
    private static final char [] ukrainianAlphabet = {
            'А','Б','В','Г','Д','Е','Є','Ж','З','И',
            'Й','І','Ї','К','Л','М','Н','О','П','Р',
            'С','Т','У','Ф','Х','Ц','Ч','Ш','Щ','Ь',
            'Ю','Я',
            'а','б','в','г','д','е','є','ж','з','и',
            'й','і','ї','к','л','м','н','о','п','р',
            'с','т','у','ф','х','ц','ч','ш','щ','ь',
            'ю','я',
            '.',',','!','?',' ', '\'', '"',':','-','—'
    };

    //набір слів широко вживаних у різних мовах. за навністю цих слів у декодованому рядку можна зробити висновок,
    //що рядок декодовано правильно
    private final String [] englishVocabulary = {
            " a ", " the ",
            " is ", " be ", " am ", " are ", " was ", " were ",
            " I ", " you ", " he ", " she ", " it ", " we "," they ",
            " my ", " your ", " his ", " her ", " its ", " our ", " their",
            " me ", " him ", " us ", " them ",
            " at ", " in ", " on ", " under ", " over ", " out ", " behind ",
            ". ", ", ", ": ", " - ", "! ", "? ", " "
    };
    private final String [] russianVocabulary = {
            " быть ", " есть ", " был ", " были ", " буду ", " будет ", " будешь ", " будут ",
            " я ", " ты ", " он ", " она ", " оно ", " мы "," они ",
            " мой ", " твой ", " его ", " её ", " наш ", " их ",
            " моего ", " твоего ", " нашего ", " меня ", " тебя ", " нас ",
            " мне ", " тебе ", " ему ", " ей ", " нам ", " им ",
            ". ", ", ", ": ", " - ", "! ", "? ", " "
    };
    private final String [] ukrainianVocabulary = {
            " є ", " був ", " були ", " буду ", " буде ", " будеш ", " будуть ",
            " я ", " ти ", " він ", " вона ", " воно ", " ми "," вони ",
            " мій ", " твій ", " його ", " її ", " наш ", " їх ",
            " мого ", " твого ", " нашого ", " мене ", " тебе ", " нас ",
            " мені ", " тобі ", " йому ", " їй ", " нам ", " їм ",
            " ньому ", " ній ", " нас ", " них ",
            " мною ", " тобою ", " ним ", " нею ", " нами ", " ними ",
            " з ", " зі ", " в ", " у ", " на ", " під ", " над ",
            ". ", ", ", ": ", " - ", "! ", "? ", " "
    };

    public static char [] getAlphabetCharacters (Language language)
    {
        switch (language)
        {
            case ENGLISH:
                return englishAlphabet;
            case UKRAINIAN:
                return ukrainianAlphabet;
            case RUSSIAN:
                return russianAlphabet;
            default:
                return null;
        }
    }

}



enum Language
{
    ENGLISH,
    RUSSIAN,
    UKRAINIAN
}

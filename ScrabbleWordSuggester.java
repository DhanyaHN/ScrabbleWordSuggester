import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;


public class ScrabbleWordSuggester
{
	static final int SCORE_OF_LETTERS[] = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
	static final int NUMBER_OF_SUGGESTIONS_NEEDED=5;
	static final String FILE_PATH = "C:\\Users\\test\\workspace\\Test\\src\\sowpods.txt";
	
	public static void main(String args[])
	{
		List<Word> listOfWords = readFile(FILE_PATH);
		listOfWords = wordSortbyScore(listOfWords);
		String rack = " c i";
		printSuggestions(findPossibleWords(listOfWords, rack));
	}
	
	static List<Word> readFile(String path)
	{
		List<Word> listOfWords = new ArrayList<Word>();
		try
		{
			Scanner sc = new Scanner(new File(path));
			while(sc.hasNext())
			{
				String word = sc.next();
				Word wordWithScore = new Word(word, calculateScore(word));
				listOfWords.add(wordWithScore);
			}	
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return listOfWords;
	}
	
	static int calculateScore(String word)
	{
		int value = 0;
		for (int i = 0; i < word.length(); i++ )
		{
			char c = word.charAt(i);
			value += SCORE_OF_LETTERS[c-'a'];
		}
		return value;
	}

	static List<Word> wordSortbyScore(List<Word> listOfWords)
	{
		Collections.sort(listOfWords);
		return listOfWords;
	}
	
	static List<String> findPossibleWords(List<Word> listOfWords, String rack)
	{ 
		List <String> listOfPossibleWords =new ArrayList<String>();
		for (Word wordWithScore : listOfWords)
		{
			if (ifWordMatches(wordWithScore.value, rack))
			{
				listOfPossibleWords.add(wordWithScore.value);
				if(listOfPossibleWords.size() >= NUMBER_OF_SUGGESTIONS_NEEDED){
					return listOfPossibleWords;
				}
			}
		}
		return listOfPossibleWords;
	}
	
	static boolean ifWordMatches(String word, String rack)
	{
		List<Character> characterListofWord = new LinkedList<Character>();
		for (char c : word.toCharArray())
		{
			characterListofWord.add(c);	
		}		
		int flag = 0;
		for (char c : rack.toCharArray())
		{
			if (c == ' ') flag++;
			characterListofWord.remove(new Character(c));
		}
		return (characterListofWord.size() == flag);
	}
	
	public static void printSuggestions(List<String> listOfWords)
	{
		if (listOfWords.isEmpty()){
			System.out.println("No suggestions");
		}
		for(String PossibleWords:listOfWords)
			System.out.print(PossibleWords+" ");
		System.out.println();
	}
	
}

class Word implements Comparable<Word>
{
	String value;
	int key;
	int length;
	
	public Word(String value, int key)
	{
		this.value = value;
		this.key = key;
		this.length = value.length();
	}
	@Override
	public int compareTo(Word o)
	{
		if(this.key == o.key)
		{
			return (this.value.compareTo(o.value));
		}
		return o.key-this.key;
	}
}

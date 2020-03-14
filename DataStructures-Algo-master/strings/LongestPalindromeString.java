package strings;

import java.util.HashMap;
import java.util.Map;

//https://www.geeksforgeeks.org/find-longest-palindrome-formed-by-removing-or-shuffling-chars-from-string/
//https://www.tutorialcup.com/string/longest-palindrome-removing-rearranging-characters.htm
// construct left half of palindromic string from the freq map of characters.
// if freq count is odd - make it as mid char
// right half = reverse(left half)
// result = left half + mid + right half
public class LongestPalindromeString {

	// Construct the longest palindrome by shuffling or deleting
	// characters from a given String
	public static String longestPalindrome(String str) {

		// create a frequency map for characters of given String
		Map<Character, Integer> freq = new HashMap<>();
		for (char ch : str.toCharArray()) {
			freq.putIfAbsent(ch, 0);
			freq.put(ch, freq.get(ch) + 1);
		}

		Character mid_char = null; // stores odd character
		StringBuilder left = new StringBuilder(); // stores left subString

		// iterate through the frequency map
		for (Map.Entry<Character, Integer> p : freq.entrySet()) {
			char ch = p.getKey(); // get current character
			int count = p.getValue(); // get character frequency

			// if the frequency of current character is odd
			// update mid to current char (and discard old one)
			if (count % 2 == 1) {
				mid_char = ch;
			}

			// append half of the characters to the left subString
			// (other half goes to the right subString in reverse order)
			for (int i = 0; i < count / 2; i++) {
				left.append(ch);
			}
		}

		// right subString will be reverse of left subString
		String right = new StringBuilder(left).reverse().toString();

		// return String formed by the left subString, mid character (if any)
		// and the right subString
		return (left.toString() + mid_char + right);
	}

	public static void main(String[] args) {
		String str = "ABBDAB";
		System.out.print("The Longest Palindrome is " + longestPalindrome(str));
	}
}

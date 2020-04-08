// Name: Kiera Leahy
// ID:   1159999

// Name: Luke McGregor
// ID: 1359403

import java.util.Scanner;

class LZWpack {
	
	public static void main(String[] args) {
		// Create a Scanner in order to read integers from the input text
		Scanner in = new Scanner(System.in);
		
		// topPhraseNumber represents the maximum phrase number which could be recieved
		int topPhraseNumber = 256;
		// the buffer stores bits which have been read in but not yet output
		long buffer = 0L;
		int bufferLength = 0;
		
		// process input as long as there are more integers to read
		while (in.hasNextInt()) {
			int phraseNumber = in.nextInt();
			// the bits required to store any incoming phrase number
			int phraseLength = calcBitLength(topPhraseNumber);
			
			// add the bits of the new phrase number to the buffer
			// without overlapping any preexisiting bits
			buffer |= phraseNumber << bufferLength;
			bufferLength += phraseLength;
			
			// while there are enough bits to output a byte
			while (bufferLength >= 8)
			{
				// output a byte from the buffer
				System.out.write((int)buffer & ((1 << 8) - 1));
				// remove the byte from the buffer
				buffer = buffer >>> 8;
				bufferLength -= 8;
			}
			// the next phrase number could be larger
			topPhraseNumber++;
		}
		// there should be less than a byte in the buffer
		// but there may be bits remaining
		// output the remaining bits as a byte
		if (bufferLength > 0)
			System.out.write((int)buffer);
		
		// make sure the system finishes outputing all bytes before closing
		System.out.flush();
	}
	
	// calculate the number of bits required to represent a number
	// shifts and counts bit to determine how many are needed
	private static int calcBitLength(int n)
	{
		int l = 1;
		while (n > 1)
		{
			n = n >>> 1;
			l++;
		}
		return l;
	}
}
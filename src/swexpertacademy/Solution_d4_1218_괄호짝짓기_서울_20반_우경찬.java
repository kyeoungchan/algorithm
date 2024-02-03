package swexpertacademy;

import java.util.*;
import java.io.*;

public class Solution_d4_1218_괄호짝짓기_서울_20반_우경찬 {

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_d4_1218.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = 10;
		for (int tc = 1; tc < T + 1; tc++) {
			ArrayDeque<Character> q = new ArrayDeque<>();
			int len = Integer.parseInt(br.readLine());
			String input = br.readLine();
			int valid = 1;
//			System.out.println();
			for (int i = 0; i < len; i++) {
				char nextChar = input.charAt(i);
				if (isClosing(nextChar)) {
					char popped = q.pop();
					if (!isPair(popped, nextChar) ) {
						valid = 0;
						break;
					}
				} else {
					q.push(nextChar);
//					System.out.println(q);
				}
			}
			if (!q.isEmpty()) valid = 0;
			sb.append("#").append(tc).append(" ").append(valid).append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}

	static boolean isClosing(char c) {
		return c == ')' || c == ']' || c == '}' || c == '>';
	}
	
	static boolean isPair(char firstChar, char secondChar) {
		return (firstChar == '{' && secondChar == '}') || (firstChar == '(' && secondChar == ')')
				|| (firstChar == '[' && secondChar == ']') || (firstChar == '<' && secondChar == '>');
	}
}

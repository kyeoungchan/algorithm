package swexpertacademy;

import java.util.*;
import java.io.*;

public class Solution_bj_2164_카드2_서울_20반_우경찬 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		ArrayDeque<Integer> queue = new ArrayDeque<>();
		int value = 0;
		
		for (int i = 1; i < N + 1; i++) {
			queue.offer(i);
		}
		
		boolean enqTurn = false;
		while(!queue.isEmpty()) {
			value = queue.poll();
			if (!enqTurn) {
				enqTurn = true;
			} else {
				queue.offer(value);
				enqTurn = false;
			}
		}
		System.out.println(value);
		br.close();
	}

}

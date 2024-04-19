package swexpertacademy.나무높이;

import java.util.*;
import java.io.*;

/**
 * 2가 남은 상황일 때 홀수번째 날이면 하루 기다리고 2를 채우는 게 1채우고 이틀 기다리고 1채우는 거보다 빠르다. -> 이틀
 * 3이 남은 상황일 때는 홀수번째든, 짝수번째든 그냥 채워진다. -> 이틀
 * 4가 남은 상황일 때는 홀수번째면 1 2 1로 바로 채워지고, 짝수번째면 2 - 0 - 2로 채워야한다. 즉, 2가 남은 상황에 처하게된다.
 * 5가 남은 상황에서는 홀수번째면 1 2 0 2 / 0 2 1 2로, 짝수번째면 2 1 2
 * 6 -> 홀수 1 2 1 2. 짝수 2 1 2 1
 * 7 -> 홀수 1 2 1 2 1 짝수 2 1 2 1 0 2
 */
public class Solution_d2_14510_나무높이 {

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_d2_14510.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc < T + 1; tc++) {
			int N = Integer.parseInt(br.readLine());
			int[] trees = new int[N];
			int day = 0;
			int maxH = 0;
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < N; i++) {
				trees[i] = Integer.parseInt(st.nextToken());
				if (trees[i] > maxH) maxH = trees[i];
			}
			PriorityQueue<Integer> evenPq = new PriorityQueue<>(Comparator.reverseOrder());
			PriorityQueue<Integer> oddPq = new PriorityQueue<>(Comparator.reverseOrder());
			for (int i = 0; i < N; i++) {
				if (trees[i] == maxH) continue;
				int sub = maxH - trees[i];
				if (sub % 2 == 0) evenPq.offer(sub);
				else oddPq.offer(sub);
			}
			
			/*
			 * 0이 되면 pq에 담지 않는다.
			 * 홀수날인 경우
			 * - 기본적으로 홀수의 pq를 사용한다.
			 * - 홀수 pq가 비었을 경우
			 * 	- 짝수 pq의 사이즈가 1이고 남은 숫자가 2면 하루 기다린다.
			 * 	- 그 외에는 그냥 짝수 pq에서 꺼내서 쓴다.
			 * 짝수날인 경우
			 * - 기본적으로 짝수의 pq를 사용한다.
			 * - 짝수 pq가 비엇을 경우
			 * 	- 홀수 pq에서 1만 꺼낼 수 있는 경우 하루 기다린다.
			 * 	- 그 외에는 홀수 pq에서 꺼내서 사용한다.
			 */
			while (!evenPq.isEmpty() || !oddPq.isEmpty()) {
				day++;
				int growth;
				if (day % 2 == 0) growth = 2;
				else growth = 1;
				
				if (growth == 1) {
					// 홀수날인 경우
					if (!oddPq.isEmpty()) {
						int cur = oddPq.poll();
						int result = cur - growth;
						if (result != 0) evenPq.offer(result);
					} else if (evenPq.size() == 1 && evenPq.peek() == 2) {
						continue;
					}
					else {
						int cur = evenPq.poll();
						int result = cur - growth;
						// 짝수 - 1이 0이 될 리가 없다.
						oddPq.offer(result);
					}
				} else {
					// 짝수날인 경우
					if (!evenPq.isEmpty()) {
						int cur = evenPq.poll();
						int result = cur - growth;
						if (result != 0) evenPq.offer(result);
					} else if (oddPq.peek() == 1) {
						continue;
					} else {
						int cur = oddPq.poll();
						int result = cur - growth;
						// 홀수 - 2가 0이 될 리가 없다.
						oddPq.offer(result);
					}
				}
			}
			sb.append("#").append(tc).append(" ").append(day).append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}

}

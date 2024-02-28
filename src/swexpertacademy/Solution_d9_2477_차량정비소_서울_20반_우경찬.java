package study;

import java.util.*;
import java.io.*;

public class Solution_d9_2477_차량정비소_서울_20반_우경찬 {

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_d9_2477.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc < T + 1; tc++) {
			st = new StringTokenizer(br.readLine(), " ");
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			
			int[] receive = new int[N + 1];
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 1; i < N + 1; i++)
				receive[i] = Integer.parseInt(st.nextToken());
			st = new StringTokenizer(br.readLine(), " ");
			int[] repair = new int[M + 1];
			for (int i = 1; i < M + 1; i++)
				repair[i] = Integer.parseInt(st.nextToken());
			
			int[][] customInfo = new int[K + 1][3]; // 도착 시간, 접수 창구 번호, 정비 창구 번호
			int maxTime = 0;
			st = new StringTokenizer(br.readLine(), " ");
			PriorityQueue<int[]> commingPq = new PriorityQueue<>(Comparator.comparingInt(c -> c[1]));
			for (int i = 1; i < K + 1; i++) {
				int time = Integer.parseInt(st.nextToken());
				customInfo[i][0] = i;
				customInfo[i][1] = time;
				commingPq.offer(customInfo[i]);
				maxTime = Math.max(maxTime, time);
			}
			
			int ans = 0;

			PriorityQueue<int[]> waitingReceive = new PriorityQueue<>(Comparator.comparingInt(c -> c[0]));
			for (int t = 0; t < maxTime + 1; t++) {
				while (commingPq.peek()[0] == t) {
					int[] cur = commingPq.poll();
					waitingReceive.offer(cur);
				}
			}
		}
		
		br.close();
	}

}

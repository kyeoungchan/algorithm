package swexpertacademy;

import java.util.*;
import java.io.*;

public class Solution_d3_1225_암호생성기_서울_20반_우경찬 {
	
	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_d3_1225.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		ArrayDeque<Integer> q;
		
		for (int tc = 1; tc < 11; tc++) {
			int N = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine(), " ");
			q = new ArrayDeque<>();
			for (int i = 0; i < 8; i++) {
				q.offer(Integer.parseInt(st.nextToken()));
			}
			int cnt = 1;
			while (true) {
				if (cnt > 5) {
					cnt = 1;
				}
				int enq = q.poll() - cnt++;
				if (enq <= 0) {
					enq = 0;
					q.offer(enq);
					break;
				}
				q.offer(enq);
			}
			sb.append("#").append(tc);
			for (int i = 0; i < 8; i++) {
				sb.append(" ").append(q.poll());
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}

}

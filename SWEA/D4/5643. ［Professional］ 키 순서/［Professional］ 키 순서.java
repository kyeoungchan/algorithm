import java.util.*;
import java.io.*;

public class Solution {
	
	static int cnt, N;
	static boolean[] check;

	public static void main(String[] args) throws Exception {
		//System.setIn(new FileInputStream("res/input_d4_5643.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc < T + 1; tc++) {
			N = Integer.parseInt(br.readLine());
			List<Integer>[] uppers = new List[N + 1];  
			List<Integer>[] lowers = new List[N + 1];
			for (int i = 1; i < N + 1; i++) {
				uppers[i] = new ArrayList<>();
				lowers[i] = new ArrayList<>();
			}
			int M = Integer.parseInt(br.readLine());
			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				uppers[a].add(b);
				lowers[b].add(a);
			}
			int ANS = 0;
			for (int s = 1; s < N + 1; s++) {
				check = new boolean[N + 1];
				cnt = getCount(s, uppers);
				cnt += getCount(s, lowers);
				if (cnt == N - 1) ANS++;
			}
			sb.append("#").append(tc).append(" ").append(ANS).append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}
	

	static int getCount(int stu, List<Integer>[] group) {
		int result = 0;
		check[stu] = true;
		for (int next: group[stu]) {
			if (check[next]) continue;
			check[next] = true;
			result += 1 + getCount(next, group);
		}
		return result;
	}
}
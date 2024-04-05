import java.io.*;
import java.util.*;

public class Solution {
	
	static int N, B, H[], minH;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc < T + 1; tc++) {
			st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());
			H = new int[N];
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < N; i++) {
				H[i] = Integer.parseInt(st.nextToken());
			}
			
			minH = Integer.MAX_VALUE;
			comb(0, 0);
			sb.append("#").append(tc).append(" ").append(minH - B).append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}
	
	static void comb(int cnt, int sum) {
		if (sum >= B) {
			// N명까지 도달했든 안 했든 B를 초과하는 순간 바로 minH를 업데이트해준다.
			minH = Math.min(minH, sum);
			return;
		}
		// N명까지 도달해도 B를 초과하지 못했다는 뜻이므로 그냥 끝낸다.
		if (cnt == N) {
			return;
		}

		// cnt번째 직원을 포함해서 계산할 때
		comb(cnt + 1, sum + H[cnt]);
		// cnt번째 직원을 빼고 계산할 때
		comb(cnt + 1, sum);
	}

}
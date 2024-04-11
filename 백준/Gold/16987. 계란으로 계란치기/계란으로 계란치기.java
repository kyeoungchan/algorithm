import java.util.*;
import java.io.*;

public class Main {

	static int N, eggs[][], ANS;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		N = Integer.parseInt(br.readLine());
		eggs = new int[N][2];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			eggs[i][0] = Integer.parseInt(st.nextToken()); // 내구도
			eggs[i][1] = Integer.parseInt(st.nextToken()); // 무게
		}

		ANS = 0;
		crash(0, 0);
		System.out.println(ANS);
		br.close();
	}

	static void crash(int cnt, int crashed) {
		if (cnt == N) {
			ANS = Math.max(crashed, ANS);
			return;
		}
		
		int s = eggs[cnt][0];
		if (s <= 0) {
			crash(cnt + 1, crashed);
			return;
		}
		int w = eggs[cnt][1];
		boolean hasBit = false;
		for (int i = 0; i < N; i++) {
			if (i == cnt || eggs[i][0] <= 0) continue;
			hasBit = true;
			int targetW = eggs[i][1];
			
			eggs[cnt][0] -= targetW;
			if (eggs[cnt][0] <= 0) crashed++;
			eggs[i][0] -= w;
			if (eggs[i][0] <= 0) crashed++;
			
			crash(cnt + 1, crashed);
			
			if (eggs[cnt][0] <= 0) crashed--;
			eggs[cnt][0] += targetW;
			if (eggs[i][0] <= 0) crashed--;
			eggs[i][0] += w;
			
		}
		if (!hasBit) {
//			crash(cnt + 1, crashed);
			ANS = Math.max(crashed, ANS);
		}
	}
}
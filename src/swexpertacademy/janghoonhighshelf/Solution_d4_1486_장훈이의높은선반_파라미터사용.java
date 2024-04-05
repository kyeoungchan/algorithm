package swexpertacademy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 선반 하나 높이: B
 * N명의 점원들, 키가 각각 Hi
 * 점원이 탑이 된다.
 * 높이가 B 이상인 탑 중에서 높이가 가장 낮은 탑을 구하자.
 * 
 * 1. 점원 1명 ~ N명으로 이루어지는 경우
 * 2. 각각의 수에 맞춰서 조합을 통해서 직원을 뽑아서 높이를 계산한다.
 * 3. 높이를 계산하는 도중에 만약 이미 B를 넘는다면 계산을 멈춘다. (이미 덜 뽑은 경우에서 계산이 됐을 것이다.)
 * 4. 계산된 높이를 계속 최솟값으로 업데이트해준다.
 * 5. 마지막에 B - 업데이트된 높이의 값으로 출력
 */
public class Solution_d4_1486_장훈이의높은선반_파라미터사용 {
	
	static int N, B, H[], minH;

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_d4_1486.txt"));
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
			for (int cnt = 1; cnt < N + 1; cnt++) {
				int[] pick = new int[cnt];
				comb(0, 0, cnt, 0);
			}
			sb.append("#").append(tc).append(" ").append(minH - B).append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}
	
	static void comb(int cnt, int start, int total, int sum) {
		if (sum > B) return;
		if (cnt == total) {
			if (sum >= B) {
				// 다 돌았을 때 B를 초과한다면 그때 업데이트 해준다.
				minH = Math.min(minH, sum);
			}
			return;
		}
		
		for (int i = start; i < N; i++) {
			int newSum = sum + H[i];
			comb(cnt + 1, i + 1, total, newSum);
		}
		
	}

}

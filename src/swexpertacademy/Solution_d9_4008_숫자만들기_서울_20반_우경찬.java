package swexpertacademy;

import java.util.*;
import java.io.*;

public class Solution_d9_4008_숫자만들기_서울_20반_우경찬 {

	static int value, N, max, min;
	static int[] numbers, operatorInfo = new int[4], operators;

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_d9_4008.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		for (int tc = 1; tc < T + 1; tc++) {
			// 매 테스트케이스마다 min과 max 초기화
			min = Integer.MAX_VALUE;
			max = Integer.MIN_VALUE;

			N = Integer.parseInt(br.readLine());
			numbers = new int[N]; // 숫자들을 담는 배열
			operators = new int[N - 1]; // 연산자 순열을 담을 배열

			// 연산자 정보 입력
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < 4; i++) {
				operatorInfo[i] = Integer.parseInt(st.nextToken());
			}

			// 숫자 정보 입력
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < N; i++) {
				numbers[i] = Integer.parseInt(st.nextToken());
			}

			perm(0);
			int ans = max - min;
			sb.append("#").append(tc).append(" ").append(ans).append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}

	static void perm(int cnt) {
		if (cnt == N - 1) {
			calculate();
			return;
		}

		for (int i = 0; i < 4; i++) {
			if (operatorInfo[i] == 0)
				continue;
			operatorInfo[i]--;
			operators[cnt] = i;
			perm(cnt + 1);
			operatorInfo[i]++;
		}
	}
	
	static void calculate() {
		value = numbers[0]; // 첫 번째로 나온 숫자를 일단 저장
		for (int i = 0; i < N - 1; i++) { // N-1 번 반복
			int numberIdx = i + 1;
			if (operators[i] == 0) {
				value += numbers[numberIdx];
			} else if (operators[i] == 1) {
				value -= numbers[numberIdx];
			} else if (operators[i] == 2) {
				value *= numbers[numberIdx];
			} else {
				value /= numbers[numberIdx];
			}
		}
		max = Math.max(max, value);
		min = Math.min(min, value);
	}

}

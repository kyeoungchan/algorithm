package swexpertacademy;

import java.util.*;
import java.io.*;

public class Solution_d_4008_숫자만들기_서울_20반_우경찬 {

	static int value, N, max, min;
	static int[] operands, operators;
	static boolean[] v;

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/input_d_4008.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		for (int tc = 1; tc < T + 1; tc++) {
			N = Integer.parseInt(br.readLine());
			operands = new int[2 * N - 1]; // 숫자들과 연산 기호들 모두 담는 배열
//			a = new int[2 * N - 1]; // operands에 담은 피연산자들을 순열로 담기 위한 배열
			operators = new int[N - 1];
			st = new StringTokenizer(br.readLine(), " ");
			int idx = 0;
			for (int i = 0; i < 4; i++) {
				// N - 1개까지는 연산자에 대한 정보!
				int inputNum = Integer.parseInt(st.nextToken());
				for (int j = 0; j < inputNum; j++)
					operators[idx++] = i;
			}
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = N - 1; i < 2 * N - 1; i++) {
				operands[i] = Integer.parseInt(st.nextToken());
			}
			v = new boolean[2 * N - 1];
			max = 0;
			min = Integer.MAX_VALUE;
			permOper(0);
			int ans = max - min;
			sb.append("#").append(tc).append(" ").append(ans).append("\n");
		}
		System.out.println(sb.toString());
		br.close();
	}

	static void permOper(int cnt) {
		if (cnt == N - 1) {
			calculate();
			return;
		}

		for (int i = 0; i < N - 1; i++) {
			if (v[i])
				continue;
			v[i] = true;
			operands[cnt] = operators[i]; 
			permOper(cnt + 1);
			v[i] = false;
		}
	}
	
	static void calculate() {
		value = operands[N - 1]; // 첫 번째로 나온 숫자를 일단 저장
		for (int operIdx = 0; operIdx < N - 1; operIdx++) { // N-1 번 반복
			int numIdx = N + operIdx;
			if (operands[operIdx] == 0) {
				value += operands[numIdx];
			} else if (operands[operIdx] == 1) {
				value -= operands[numIdx];
			} else if (operands[operIdx] == 2) {
				value *= operands[numIdx];
			} else {
				value /= operands[numIdx];
			}
		}
		max = Math.max(max, value);
		min = Math.min(min, value);
	}

}

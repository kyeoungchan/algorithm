package baekjoon.cuttinglanline;

import java.util.*;
import java.io.*;

/**
 * 만들어야하는 랜선의 개수: N보다 크거나 같다.
 * 그리고 만들어야하는 랜선의 길이는 모두 같아야 한다.
 * 기존의 K개의 랜선으로 N개 이상의 랜선을 만들 때, 최대 랜선의 길이를 구하자.
 */
public class Solution_bj_1654_랜선자르기 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int K = Integer.parseInt(st.nextToken());
		int N = Integer.parseInt(st.nextToken());
		int[] has = new int[K];
		long low = 1, high = 1;
		for (int i = 0; i < K; i++) {
			has[i] = Integer.parseInt(br.readLine());
			high = Math.max(high, has[i]);
		}
		
		while (low <= high) {
			long mid = (low + high) / 2;
			long cnt = getCount(mid, has, N); 
			if (cnt < N) {
				// 개수가 모자르다면, 개당 랜선의 길이를 줄여야한다.
				high = mid - 1;
			} else {
				// 개수가 만족한다면 개당 랜선의 길이를 늘려본다.
				low = mid + 1;
			}
		}
		System.out.println(high);
		br.close();
	}

	static long getCount(long len, int[] has, int N) {
		long cnt = 0;
		for (int lan: has) {
			cnt += lan / len;
			if (cnt > N) break;
		}
		return cnt;
	}
}

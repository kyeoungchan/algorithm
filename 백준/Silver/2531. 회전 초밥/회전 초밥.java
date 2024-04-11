import java.util.*;
import java.io.*;

/**
 * 벨트의 한 위치부터 k개의 접시를 연속해서 먹을 경우 할인
 * 초밥의 종류 하나가 쓰인 쿠폰을 발행하고, 1번 행사에 참가할 경우, 이 쿠폰에 적혀진 종류의 초밥 하나 추가 제공
 * 손님이 먹을 수 있는 초밥 가짓수의 최댓값 구하기
 */
public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int d = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());
		int[] belt = new int[N];
		for (int i = 0; i < N; i++) {
			belt[i] = Integer.parseInt(br.readLine());
		}
		int[] eaten = new int[d + 1];
		
		int cnt = 0;
		// 처음~k번째까지 먹은 경우
		for (int i = 0; i < k; i++) {
			int sushi = belt[i];
			if (eaten[sushi] == 0) cnt++;
			eaten[sushi]++;
		}
		if (eaten[c] == 0) {
			cnt++;
		}
		eaten[c]++;
		
		int result = cnt;
		
		for (int i = 1; i < N; i++) {
			int before = belt[i-1];
			eaten[before]--;
			if (eaten[before] == 0) cnt--;
			
			int end = belt[(i + k - 1) % N];
			if (eaten[end] == 0) cnt++;
			eaten[end]++;
			result = Math.max(result, cnt);
		}
		System.out.println(result);
		br.close();
	}

}
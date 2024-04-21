import java.io.*;
import java.util.*;

/**
 * 집의 좌표는 x1,...,xN
 * 좌표가 겹치는 일은 없다.
 * 한 집에는 공유기 하나만 설치, 가장 인접한 두 공유기 사이의 거리를 가능한 크게 하여 설치
 * C개의 공유기를 N개의 집에 적당히 설치해서 가장 인접한 두 공유기 사이의 거리를 최대로 하는 프로그램 작성
 * 1 2 4 50 100
 */
public class Main {
    static int N, C;
    static int[] houses;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        // 집 N개가 수직선위에 있다.
        N = Integer.parseInt(st.nextToken());
        // 집에 공유기 C개를 설치하려고 한다.
        C = Integer.parseInt(st.nextToken());
        houses = new int[N];
        int start = 0;
        for (int i = 0; i < N; i++) {
            houses[i] = Integer.parseInt(br.readLine());
        }

        Arrays.sort(houses);

        int end = houses[N - 1];
        // 가장 인접한 두 공유기의 거리가 멀려면 결국 최대한 거리가 일정하게 놓아야 한다.
        while (start <= end) {
            int mid = (start + end) / 2;
            if (!canInstall(mid)) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        System.out.println(end);
        br.close();
    }

    static boolean canInstall(int interval) {
        int temp = C;
        int adj = 0;
        for (int i = 1; i < houses.length; i++) {
            if (houses[i] - houses[adj] >= interval) {
                adj = i;
                temp--;
            }
        }
        // 0번째 지점을 설치를 안함을 가정하고 연산을 했으므로 공유기가 하나가 남은 상황에서는 0번째 지점에 설치 가능하다.
        return temp <= 1;
    }
}
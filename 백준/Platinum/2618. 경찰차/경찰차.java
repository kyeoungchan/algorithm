import java.io.*;
import java.util.StringTokenizer;

public class Main {
    
    static int[][] event, dp;
    static int N, W;
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        
        N = Integer.parseInt(br.readLine());
        W = Integer.parseInt(br.readLine());

        event = new int[W + 1][2];
        dp = new int[W + 1][W + 1];

        for (int i = 1; i <= W; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            event[i][0] = Integer.parseInt(st.nextToken());
            event[i][1] = Integer.parseInt(st.nextToken());
        }

        sb.append(dfs(1, 0, 0)).append("\n");

        int one = 0;
        int two = 0;
        for (int i = 1; i <= W; i++) {
            int dist = getDistance(1, one, i);

            if (dp[one][two] == dist + dp[i][two]) {
                one = i;
                sb.append(1).append("\n");
            } else {
                two = i;
                sb.append(2).append("\n");
            }
        }
        System.out.println(sb);
        br.close();
    }

    private static int dfs(int eventNum, int one, int two) {
        // 모든 사건을 다 이동했을 경우
        if (eventNum > W) return 0;
        
        // 이미 값이 존재할 경우
        if (dp[one][two] != 0) return dp[one][two];

        int oneMoved = dfs(eventNum + 1, eventNum, two) + getDistance(1, one, eventNum); // 경찰차 A가 이동했을 경우
        int twoMoved = dfs(eventNum + 1, one, eventNum) + getDistance(2, two, eventNum); // 경찰차 B가 이동했을 경우
        
        return dp[one][two] = Math.min(oneMoved, twoMoved);
    }

    private static int getDistance(int policeNum, int start, int end) {
        int[] startPos = event[start];
        int[] endPos = event[end];

        if (start == 0) {
            // 0번째 이벤트는 존재하지 않고, 오로지 경찰차의 초기 위치다.
            if (policeNum == 1) {
                startPos = new int[]{1, 1};
            } else {
                startPos = new int[]{N, N};
            }
        }
        return Math.abs(endPos[0] - startPos[0]) + Math.abs(endPos[1] - startPos[1]);
    }
}

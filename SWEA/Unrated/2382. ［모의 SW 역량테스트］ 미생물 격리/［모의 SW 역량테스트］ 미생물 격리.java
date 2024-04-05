import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        int[] di = {0, -1, 1, 0, 0}, dj = {0, 0, 0, -1, 1};
        for (int tc = 1; tc < T + 1; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            List<int[]> microbes = new ArrayList<>();
            int answer = 0;
            for (int i = 0; i < K; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int mi = Integer.parseInt(st.nextToken());
                int mj = Integer.parseInt(st.nextToken());
                int cnt = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken());
                microbes.add(new int[]{mi, mj, cnt, d, cnt}); // 좌표, 군집의 미생물 수, 방향, 합쳐질 때 최대 군집의 수 기록용 장소
                answer += cnt;
            }

            for (int time = 0; time < M; time++) {
                for (int i = 0; i < microbes.size(); i++) {
                    int[] microbe = microbes.get(i);
                    int mi = microbe[0];
                    int mj = microbe[1];
                    int cnt = microbe[2];
                    int md = microbe[3];

                    int ni = mi + di[md];
                    int nj = mj + dj[md];
                    if (ni == 0 || ni == N - 1 || nj == 0 || nj == N - 1) {
                        int nd = md % 2 == 0 ? md - 1 : md + 1;
                        int nCnt = cnt / 2;
                        answer -= cnt - nCnt;
                        microbe[2] = nCnt;
                        microbe[3] = nd;
                    } else {
                        for (int j = 0; j < i; j++) {
                            int[] another = microbes.get(j);
                            if (another[2] == 0) continue;
                            if (another[0] != ni || another[1] != nj) continue;
                            if (cnt > another[4]) {
                                microbe[2] += another[2];
                                another[2] = 0;
                            } else {
                                another[2] += cnt;
                                microbe[2] = 0;
                            }
                        }
                    }
                    microbe[0] = ni;
                    microbe[1] = nj;
                }

                for (int i = microbes.size() - 1; i > -1; i--) {
                    int[] microbe = microbes.get(i);
                    if (microbe[2] == 0) {
                        microbes.remove(i);
                    } else {
                        microbe[4] = microbe[2];
                    }
                }
            }
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }
}
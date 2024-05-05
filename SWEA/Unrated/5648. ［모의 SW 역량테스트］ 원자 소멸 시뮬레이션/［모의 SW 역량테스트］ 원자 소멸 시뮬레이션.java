import java.io.*;
import java.util.*;

/**
 * 원자들은 2차원 평면에서 이동
 * 2개 이상의 원자가 충돌할 경우
 * - 각자 보유한 에너지를 방출
 * - 소멸
 * 모든 원자의 이동속도는 동일 => 1초에 1만큼의 거리
 * 모든 원자들은 최초 위치에서 동시에 이동
 * 원자들이 소멸되면서 방출하는 에너지의 총합을 구하라.
 * 보유 에너지: 1~100 => 총합은 int형 범위 내
 * 최초 위치는 중복되지 않는다.
 */
public class Solution {

    static class Atom {
        int r;
        int c;
        int d;
        int energy;
        boolean extinguished;

        public Atom(int r, int c, int d, int energy) {
            this.r = r;
            this.c = c;
            this.d = d;
            this.energy = energy;
            extinguished = false;
        }

        public void move() {
            map[r][c] -= energy;
            r += di[d];
            c += dj[d];
            if (r < 0 || r > 4000 || c < 0 || c > 4000) {
                extinguished = true;
            } else {
                map[r][c] += energy;
            }
        }

        public void extinguish() {
            if (map[r][c] > energy) {
                extinguished = true;
                totalEnergy += energy;
                deletingPos.add(r * 4000 + c);
            }
        }
    }

    static int totalEnergy;
    // 상(0), 하(1), 좌(2), 우(3)
    static int[] di = {-1, 1, 0, 0}, dj = {0, 0, -1, 1};
    static int[][] map;
    static List<Atom> atoms;
    static Set<Integer> deletingPos;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            int N = Integer.parseInt(br.readLine());
            // 좌표의 범위에는 제한이 없다고 해서 map을 사용하지 않으려고 했는데 생각해보니 -1000~1000 밖으로 나가는 애는 에너지를 방출할 리가 없으므로 그냥 보내버리면 된다.
            atoms = new ArrayList<>();
            // (-1,000≤x,y≤1,000)
            // 0 ~ 2000 => 0.5초 단위를 생각하면 0~4000
            map = new int[4001][4001];
            for (int i = 0; i < N; i++) {
                // 다음 N개의 줄에는 원자들의 x 위치, y 위치, 이동 방향, 보유 에너지 K가 주어진다.
                st = new StringTokenizer(br.readLine(), " ");
                int x = (Integer.parseInt(st.nextToken()) + 1000) * 2;
                int y = (-Integer.parseInt(st.nextToken()) + 1000) * 2;
                int d = Integer.parseInt(st.nextToken());
                int k = Integer.parseInt(st.nextToken());
                atoms.add(new Atom(y, x, d, k));
                map[y][x] = k;
            }
            deletingPos = new HashSet<>();
            totalEnergy = 0;
            while (!atoms.isEmpty()) {
                for (int i = 0; i < atoms.size(); i++) {
                    Atom atom = atoms.get(i);
                    if (atom.extinguished) {
                        // 에너지 방출 후 소멸된 애들에 대한 처리
                        atoms.remove(i);
                        i--;
                    } else {
                        atom.move();
                    }
                }
                for (int i = 0; i < atoms.size(); i++) {
                    Atom atom = atoms.get(i);
                    if (atom.extinguished) {
                        // 이동하다가 범위 밖으로 나간 애들에 대한 처리
                        atoms.remove(i);
                        i--;
                    } else {
                        atom.extinguish();
                    }
                }
                for (Integer pos : deletingPos) {
                    int r = pos / 4000;
                    int c = pos % 4000;
                    map[r][c] = 0;
                }
                deletingPos.clear();
            }
            sb.append("#").append(tc).append(" ").append(totalEnergy).append("\n");
        }
        System.out.print(sb.toString());
        br.close();
    }

}
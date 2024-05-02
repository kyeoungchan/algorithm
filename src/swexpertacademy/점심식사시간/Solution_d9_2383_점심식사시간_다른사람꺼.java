package swexpertacademy.점심식사시간;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Solution_d9_2383_점심식사시간_다른사람꺼 {
    static int[][] map;
    static int[] match;
    static int N;
    static int finalMinTime;
    static LinkedList<point> people = new LinkedList<point>();
    static LinkedList<point> stair = new LinkedList<point>();
    static class point{
        int x, y;
        point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    static int dist(point people, point stair) {
        int dx = Math.abs(people.x - stair.x);
        int dy = Math.abs(people.y - stair.y);
        return dx + dy;
    }
    static void dfs(int count) {
        if(count == people.size()) {
            run();
            return;
        }
        for(int i = 0 ; i < 2 ; i++) {
            match[count] = i;
            dfs(count + 1);
        }
    }
    static void run() {
        int maxStairTime = 0;
        for(int idx = 0 ; idx < 2 ; idx++) {
            int pcount[] = new int[N * 2 - 1];
            for(int pidx = 0 ; pidx < people.size(); pidx++) {
                if(match[pidx] == idx) {
                    int time = dist(people.get(pidx), stair.get(idx));
                    pcount[time]++;
                }
            }
            int mintime = 0;
            int pctime[] = new int[10*2+10*10];
            for(int time = 0 ; time < N * 2 - 1; time++) {
                while(pcount[time] > 0) {
                    pcount[time] --;
                    int stairsize = map[stair.get(idx).x][stair.get(idx).y];
                    int walktime = time;
                    while(true) {
                        if(pctime[walktime] < 3) {
                            pctime[walktime] ++;
                            stairsize --;

                            if(stairsize == 0) {
                                mintime = Math.max(mintime, walktime + 2);
                                break;
                            }
                        }
                        walktime++;
                    }
                }
            }
            maxStairTime = Math.max(maxStairTime, mintime);
        }
        finalMinTime = Math.min(finalMinTime, maxStairTime);
    }
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());
        for(int yy = 1 ; yy <= T ; yy++) {
            N  = Integer.parseInt(br.readLine());
            people.clear();
            stair.clear();
            map = new int[N][N];
            for(int i = 0 ; i < N ; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                for(int j = 0 ; j < N ; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    if(map[i][j] == 1) {
                        people.add(new point(i, j));
                    }
                    if(map[i][j] > 1) {
                        stair.add(new point(i, j));
                    }
                }
            }
            match = new int[people.size()];
            finalMinTime = Integer.MAX_VALUE;
            dfs(0);
            System.out.println("#"+yy+" "+finalMinTime);
        }
    }
}

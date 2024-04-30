package swexpertacademy.키순서;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_d4_5643_키순서_강의코드_template {

    static int N, M, cnt;
    static int[][] adj;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input_d4_5643.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc < T + 1; tc++) {
            N = Integer.parseInt(br.readLine());
            M = Integer.parseInt(br.readLine());
        }
        br.close();
    }
}

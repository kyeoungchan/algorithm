package swexpertacademy.prelearning.intermediate.mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* 1000명의 수학 성적을 토대로 통계 자료를 만든다.
* 최빈수를 이용 => 평균 수준 짐작
* 최빈수를 출력하는 프로그램 작성. 단, 최빈수가 여러 개일 때는 가장 큰 점수를 출력*/
public class Mode {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int i = 0; i < t; i++) {
            int tc = sc.nextInt();
            ArrayList<Integer> scores = new ArrayList<>(1000);
            for (int j = 0; j < 1000; j++) {
                scores.add(sc.nextInt());
            }
            System.out.printf("#%d %d\n", tc, new Mode().getMode(scores));
        }
    }

    private int getMode(List<Integer> scores) {
        int mode = 0;
        int maxCount = 0;
        for (int score : scores) {
            int count = count(scores, score);
            if (maxCount < count) {
                maxCount = count;
                mode = score;
            }

            if (maxCount == count && mode < score) {
                mode = score;
            }
        }
        return mode;
    }

    private int count(List<Integer> scores, int findNum) {
        int result = 0;
        for (int score : scores) {
            if (findNum == score) {
                result++;
            }
        }
        return result;
    }
}

package mincoding.일정관리;

import java.io.*;
import java.util.*;

/**
 * 먼저 강의를 요청한 곳의 강의를 진행한다.
 * 이미 강의를 하기로 결정한 일자에 다른 요청이 들어온다면 해당 요청은 거절한다.
 */
public class Solution_min_일정관리 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        TreeMap<Integer, String> startTime = new TreeMap<>();
        TreeMap<Integer, String> endTime = new TreeMap<>();
        int Q = Integer.parseInt(br.readLine());
        int result = 0;
        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int st2 = Integer.parseInt(st.nextToken());
            int en  = Integer.parseInt(st.nextToken());
/*
            if (startTime.containsKey(st2) || startTime.containsKey(en)) continue;
            if (endTime.containsKey(st2) || endTime.containsKey(en)) continue;
*/
            Integer right = startTime.floorKey(en);
            if (right != null && right >= st2) continue;
            Integer left = endTime.ceilingKey(st2);
            if (left != null && left <= en) continue;

            left = startTime.lowerKey(st2);
            right = endTime.higherKey(en);
            if (left != null && right != null) {
                // 이미 짜여진 일정 안에 새로운 일정이 들어가려는 경우
                Integer i1 = endTime.lowerKey(st2);
                Integer i2 = startTime.higherKey(en);
                if ((i1 == null || i1 < left) && (i2 == null || i2 > right)) continue;
            }

            startTime.put(st2, "Start");
            endTime.put(en, "End");
            result++;
        }
        System.out.println(result);
        br.close();
    }
}

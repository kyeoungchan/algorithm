package mincoding.격자도시;

import java.io.*;
import java.util.*;

/**
 * 모든 지하철은 한 행에서 수평으로 이루어져야 한다.
 */
public class Solution_min_격자도시 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        long answer = (long) N * M;
        int idx = 0;
        Map<Integer, Integer> rowMap = new HashMap<>();
        List<TreeMap<Integer, Integer>> treeMaps = new ArrayList<>();
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int r = Integer.parseInt(st.nextToken());
            if (!rowMap.containsKey(r)) {
                rowMap.put(r, idx++);
                treeMaps.add(new TreeMap<>());
            }
            TreeMap<Integer, Integer> curTm = treeMaps.get(rowMap.get(r));
            int c1 = Integer.parseInt(st.nextToken());
            int c2 = Integer.parseInt(st.nextToken());
            long addingSubwayLength = (long) c2 - c1 + 1;
            boolean put = false;

            Integer leftStart = curTm.floorKey(c1);
            if (leftStart != null) {
                int leftEnd = curTm.get(leftStart);
                if (c2 <= leftEnd) continue; // 이미 지어진 지하철에 포함되면 더이상 카운트 불필요
                if (c1 <= leftEnd) {
                    addingSubwayLength -= leftEnd - c1 + 1;
                    curTm.put(leftStart, c2);
                    put = true;
                }
            }

            Integer rightStart = curTm.higherKey(c1);
            if (rightStart != null) {
                int rightEnd = curTm.get(rightStart);
                while (rightEnd <= c2) {
                    // 새로 들어오는 지하철에 기존에 있던 지하철이 다 포함되는 경우
                    addingSubwayLength -= rightEnd - rightStart + 1;
                    curTm.remove(rightStart);
                    rightStart = curTm.higherKey(c1);
                    if (rightStart == null) break;
                    rightEnd = curTm.get(rightStart);
                }
                if (rightStart != null && rightStart <= c2) {
                    addingSubwayLength -= c2 - rightStart + 1;
                    curTm.remove(rightStart);
                    if (put) curTm.put(leftStart, rightEnd);
                    else curTm.put(c1, rightEnd);
                    put = true;
                }
            }
            if (!put) curTm.put(c1, c2);
            answer -= addingSubwayLength;
        }
        System.out.println(answer);
        br.close();
    }
}

package mincoding.레벨제한아이템배급;

import java.io.*;
import java.util.*;

/**
 * N명의 길드원
 * 줄을 선 순서대로 아이템 선택
 * M개의 각 아이템들은 레벨 제한이 있다.
 * 아이템 배급이 완료되거나 아이템을 선택하지 못한 길드원이 나오면 이벤트는 종료된다.
 */
public class Solution_min_레벨제한아이템배급 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        TreeMap<Integer, Integer> itemMap = new TreeMap<>();
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < M; i++) {
            itemMap.put(Integer.parseInt(st.nextToken()), 0);
        }
        int result = 0;
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < N; i++) {
            Integer item = itemMap.floorKey(Integer.parseInt(st.nextToken()));
            if (item != null) {
                result++;
                itemMap.remove(item);
            } else break;
        }
        System.out.println(result);
        br.close();
    }
}

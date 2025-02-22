package mincoding.격자도시2;

import java.io.*;
import java.util.*;

/**
 * N x M 크기의 격자 도시에 신도시를 건설하려 한다.
 * 1. 지하철 노선
 * 모든 지하철은 한 행에 수평으로 이루어져야 한다.
 * c1: 출발하는 열 번호
 * c2: 도착하는 열 번호
 * 노선은 겹칠 수 있다. => 겹쳐지면 하나의 노선으로 합쳐진다고 생각하면 될듯
 * 좌표번호는 1부터 시작함 주의!
 * 2. 아파트 부지
 * K개의 지하철 노선을 건설한 후 -> 지하철이 지나지 않는 구역에 아파트를 건설할 예정
 * 아파트를 세우는 곳의 조건
 * 1) 지하철 노선 기준 좌우로 살폈을 때 빈칸이 2 이하인 곳은 아파트 부지가 들어서지 않는다. (너비 확보의 문제)
 * 2) 가장 빈 공간이 넓은 구역에서는 양 옆 1칸씩 제외하고 모두 아파트 부지로 사용한다.
 * 3) 그 외의 지하철이 지나지 않는 구역에는 구역 크기의 절반만큼 아파트를 짓는다.
 * 4) 한 행에서 아파트를 지을 수 있는 구역이 단 하나라면 3번의 조건을 따른다.
 * 3. 공원 설치
 * 남은 개발되지 않는 지역에는 공원을 설치한다.
 * 공원을 설치할 수 있는 구역의 개수를 구하라.
 */
public class Solution_test_격자도시2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken()); // 도시의 행의 크기 (1 <= N <= 1_000_000_000)
        int M = Integer.parseInt(st.nextToken()); // 도시의 열의 크기 (1 <= M <= 1_000_000_000)
        int K = Integer.parseInt(st.nextToken()); // 지하철 노선의 개수 (0 <= K <= 1_000)

        long answer = (long) N * M;
        int idx = 0;
        Map<Integer, Integer> rowToIdx = new HashMap<>();
        List<TreeMap<Integer, Integer>> subwayInfo = new ArrayList<>(); // 지하철 노선 정보를 각 행마다 담는다.
        List<TreeMap<Integer, Integer>> emptyInfo = new ArrayList<>(); // 빈땅 정보를 각 행마다 담는다. 이 정보를 기반으로 아파트 짓는 부지를 판단한다.
        // 각 TreeMap은 각 행에 매칭되고, rowToIdx를 통해 이 리스트에서 인덱스를 찾는다.
        // 예를 들어, 3번 행에 관한 지하철 정보가 첫번째로 입력이 된다면
        // rowToIdx.put(3, 0); subwayInfo.get(0).put(c1, c2); 이런식으로 입력을 한다.

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int r = Integer.parseInt(st.nextToken());
            int c1 = Integer.parseInt(st.nextToken());
            int c2 = Integer.parseInt(st.nextToken());

            long addingSubwayLength = (long) c2 - c1 + 1;
            boolean subwayPut = false;
            boolean leftEmptyPut = false;
            boolean rightEmptyPut = false;

            if (!rowToIdx.containsKey(r)) {
                // 해당 행에 대한 지하철 정보를 넣은적이 없다면 인덱스 정보와 TreeMap을 생성해준다.
                rowToIdx.put(r, idx++);
                subwayInfo.add(new TreeMap<>());
                emptyInfo.add(new TreeMap<>());
            }

            TreeMap<Integer, Integer> subway = subwayInfo.get(rowToIdx.get(r));
            TreeMap<Integer, Integer> emptySpot = emptyInfo.get(rowToIdx.get(r));
            System.out.println("emptySpot = " + emptySpot);
            Integer leftStart = subway.floorKey(c1);
            if (leftStart != null) {
                // 만약 기존에 있던 지하철 중에 c1보다 더 시작 열이 작거나 같은 것이 존재한다면
                Integer leftEnd = subway.get(leftStart);
                if (c2 <= leftEnd) {
                    // endLeft가 c2보다 크거나 같으면 기존에 있는 지하철에 포함되는 지하철이라는 뜻이므로 continue!
                    continue;
                }
                if (c1 <= leftEnd) {
                    // 일단 새로 지어야할 지하철의 길이 중 기존의 지하철과 겹치는 부분은 빼주고
                    addingSubwayLength -= leftEnd - c1 + 1;
                    // 기존의 지하철의 끝 지점을 c2로 업데이트 해준다.
                    subway.put(leftStart, c2);
                    // 업데이트 여부 true로 바꿔준다.
                    subwayPut = true;

                    leftEmptyPut = true;
                    emptySpot.remove(leftEnd + 1);
                }
            }

            Integer rightStart = subway.higherKey(c1);
            if (rightStart != null) {
                Integer rightEnd = subway.get(rightStart);
                while (rightEnd <= c2) {
                    // 기존의 오른쪽 지하철들 중 새로 들어오는 지하철에 포함되는 지하철은 모두 합병 시켜야한다.

                    // 추가되는 길이 기존의 지하철 길이만큼 없앤다.
                    addingSubwayLength -= rightEnd - rightStart + 1;
                    // 기존 지하철 정보 삭제
                    subway.remove(rightStart);

                    Integer leftEmptyStart = emptySpot.floorKey(rightStart);
                    if (leftEmptyStart != null) {
                        Integer leftEmptyEnd = emptySpot.get(leftEmptyStart);
                        if (leftEmptyEnd == rightStart - 1) {
                            // 기존 지하철 기준 왼쪽에 있는 빈 공간에 대한 정보를 삭제한다.
                            emptySpot.remove(leftEmptyStart);
                        }
                    }
                    // 기존 지하철 기준 오른쪽에 있는 빈 공간도 있으면 삭제한다.
                    emptySpot.remove(rightEnd + 1);

                    rightStart = subway.higherKey(c1);
                    if (rightStart == null) break;
                    rightEnd = subway.get(rightStart);
                }
                if (rightStart != null && rightStart <= c2) {
                    // 합병되지는 않았지만 c2보다는 시작점이 작아서 걸치는 애가 있다면
                    addingSubwayLength -= c2 - rightStart + 1;
                    subway.remove(rightStart);

                    // 오른쪽 끝 지점은 c2가 아니라 endRight로 둔다.
                    if (subwayPut) {
                        // 이미 기존 왼쪽 지하철과 합쳐진 경우라면 시작점을 startLeft로 넣는다.
                        subway.put(leftStart, rightEnd);
                    } else {
                        // 왼쪽 지하철과 합쳐진 적 없다면 시작점은 c1으로 넣는다.
                        subway.put(c1, rightEnd);
                    }

                    rightEmptyPut = true;
                    Integer leftEmptyStart = emptySpot.floorKey(rightStart);
                    if (leftEmptyStart != null && emptySpot.get(leftEmptyStart) == rightStart - 1) {
                        emptySpot.remove(leftEmptyStart);
                    }
                }
            }

            if (!subwayPut) {
                subway.put(c1, c2);
            }
            answer -= addingSubwayLength;

            if (!leftEmptyPut) {
                Integer leftSubwayStart = subway.lowerKey(c1);
                if (leftSubwayStart == null) {
                    if (c1 > 3) {
                        emptySpot.put(1, c1 - 1);
                    }
                } else {
                    Integer leftSubwayEnd = subway.get(leftSubwayStart);
                    if (c1 - leftSubwayEnd >= 4) {
                        emptySpot.put(leftSubwayEnd + 1, c1 - 1);
                    }
                }
            }

            if (!rightEmptyPut) {
                Integer rightSubwayStart = subway.higherKey(c2);
                System.out.println("rightSubwayStart = " + rightSubwayStart);
                if (rightSubwayStart == null) {
                    System.out.println("c2 = " + c2);
                    if (M - c2 > 2) {
                        emptySpot.put(c2 + 1, M);
                    }
                } else {
                    if (rightSubwayStart - c2 > 3) {
                        emptySpot.put(c2 + 1, rightSubwayStart - 1);
                    }
                }
            }
            System.out.println("emptySpot = " + emptySpot);
            System.out.println();
        }

        for (int i = 0; i < idx; i++) {
            TreeMap<Integer, Integer> emptySpot = emptyInfo.get(i);
            if (emptySpot.isEmpty()) continue;

            System.out.println("emptySpot = " + emptySpot);
            System.out.println("r = " + rowToIdx.get(i));
            if (emptySpot.size() == 1) {
                Map.Entry<Integer, Integer> entry = emptySpot.pollFirstEntry();
                answer -= (entry.getValue() - entry.getKey() + 1) / 2;
                continue;
            }

            int max = 0;
            while (!emptySpot.isEmpty()) {
                Map.Entry<Integer, Integer> entry = emptySpot.pollFirstEntry();
                int length = entry.getValue() - entry.getKey() + 1;
                max = Math.max(max, length);
                answer -= length / 2;
            }
            answer += max / 2;
            answer -= max - 2;
        }
        System.out.println(answer);
        br.close();
    }

}

package mincoding.식당의가치;

import java.io.*;
import java.util.*;

/**
 * N개의 마을과 M개의 도로
 * 양방향 모두 통행 가능
 * 연결 그래프 보장 X
 * 각 마을에는 식당이 존재할 수도 있고, 존재하지 않을 수도 있다.
 * 식당은 수시로 평점을 받고, 식당의 가치는 여태까지 받은 평점의 합이다.
 */
public class Solution_min_식당의가치 {

    static class Restaurant {
        String name;
        int score;

        public void setName(String name) {
            this.name = name;
        }

        public void addScore(int score) {
            this.score += score;
        }
    }

    static class Town {
        int number, dist;

        public Town(int number, int dist) {
            this.number = number;
            this.dist = dist;
        }
    }

    private List<Integer>[] graph;
    private List<Restaurant>[] restaurants;
    private Map<String, Restaurant> nameRestaurantMap = new HashMap<>();
    private Map<String, Integer> maxScoreMap = new HashMap<>();
    private int[] visited;
    private int cc;

    /**
     * 존재하는 식당은 없다.
     * @param N 마을의 개수. 각 마을에는 1~N까지 번호가 있다. (2 <= N <= 50)
     * @param M 도로의 개수 (1 <= M <= 50)
     * @param mRoads mRoads[i][0]과 mRoads[i][1]을 잇는 도로가 존재한다.
     */
    public void init(int N, int M, int mRoads[][]) {
        cc = 0;
        visited = new int[N + 1];
        graph = new List[N + 1];
        restaurants = new List[N + 1];
        for (int i = 1; i < N + 1; i++) {
            graph[i] = new ArrayList<>();
            restaurants[i] = new ArrayList<>();
        }

        nameRestaurantMap.clear();
        maxScoreMap.clear();

        for (int i = 0; i < M; i++) {
            int a = mRoads[i][0];
            int b = mRoads[i][1];
            graph[a].add(b);
            graph[b].add(a);
        }
    }

    /**
     * 식당이 추가된다.
     * mName 식당이 이전에는 없음이 보장
     * 호출 횟수: 10_000 이하
     * @param mCityID 마을 번호
     * @param mName 식당 이름 (3 <= 이름의 길이 <= 5)
     */
    public void addRestaurant(int mCityID, char mName[]) {
        Restaurant restaurant = new Restaurant();

        String name = getName(mName);
        restaurant.setName(name);
        restaurants[mCityID].add(restaurant);
        nameRestaurantMap.put(name, restaurant);

    }

    /**
     * 평점을 받는다. mScore가 더해진다.
     * mName 식당이 존재함 보장
     * 호출 횟수: 10_000 이하
     * @param mName 식당 이름
     * @param mScore 받은 평점 (1 <= score <= 5)
     */
    public void addValue(char mName[], int mScore) {
        Restaurant target = nameRestaurantMap.get(getName(mName));
        target.addScore(mScore);
        int candidateScore = target.score;

        StringBuilder sb;
        for (int i = 0; i < mName.length; i++) {
            sb = new StringBuilder();
            for (int j = i; j < mName.length; j++) {
                sb.append(mName[j]);
                String subName = sb.toString();
                if (maxScoreMap.containsKey(sb.toString())) {
                    maxScoreMap.put(subName, Math.max(maxScoreMap.get(subName), candidateScore));
                } else {
                    maxScoreMap.put(subName, candidateScore);
                }
            }
        }
    }

    private String getName(char[] name) {
        StringBuilder sb = new StringBuilder();
        for (char c : name) sb.append(c);
        return sb.toString();
    }

    /**
     * 식당 이름에 mStr이 포함되어 있는 식당의 가치 중 가장 높은 가치를 반환
     * 해당 조건에 만족하는 식당이 존재함이 보장
     * 호출횟수: 150_000 이하
     *
     * @param mStr 검색 문구 (1 <= 길이 <= 5)
     * @return 가장 가치가 높은 식당의 가치
     */
    public int bestValue(char mStr[]) {
        return maxScoreMap.getOrDefault(getName(mStr), 0);
    }

    /**
     * 호출횟수: 1_000 이하
     * @param mCityID 시작하는 마을
     * @param mDist 이하로 거쳐야하는 도로의 개수 (0 <= mDist <= 3)
     * @return 가장 가치가 높은 식당 3개의 가치의 총합을 반환. 해당하는 식당이 0개라면 0을 반환
     */
    public int regionalValue(int mCityID, int mDist) {
        cc++;
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        ArrayDeque<Town> q = new ArrayDeque<>();
        q.offer(new Town(mCityID, 0));

        // visited 최적화
        visited[mCityID] = cc;
        for (Restaurant restaurant: restaurants[mCityID]) pq.offer(restaurant.score);
        while (!q.isEmpty()) {
            Town cur = q.poll();
            if (cur.dist == mDist) break;
            for (int nextId: graph[cur.number]) {
                if (visited[nextId] == cc) continue;
                visited[nextId] = cc;
                q.offer(new Town(nextId, cur.dist + 1));
                for (Restaurant restaurant: restaurants[nextId]) pq.offer(restaurant.score);
            }
        }

        int result = 0;
        for (int i = 0; i < 3 && !pq.isEmpty(); i++) {
            result += pq.poll();
        }
        return result;
    }
}

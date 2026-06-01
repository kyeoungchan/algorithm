import java.util.*;

class Solution {
    
    static final int INF = 1_000_000_001;
    
    static class Road {
        int x1, y1, x2, y2, limit;
        
        Road(int x1, int y1, int x2, int y2, int limit) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.limit = limit;
        }
        
        boolean isVertical() {
            return x1 == x2;
        }
    }
    
    static class PointOnRoad {
        int id, x, y;
        
        PointOnRoad(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }
    
    static class State implements Comparable<State> {
        int node, limit;
        
        State(int node, int limit) {
            this.node = node;
            this.limit = limit;
        }
        
        @Override
        public int compareTo(State o) {
            return Integer.compare(o.limit, limit);
        }
    }
    
    Map<Long, Integer> idMap;
    List<Integer> xs, ys, limits;
    List<Integer>[] graph;
    int[] cityNodes;
    
    public int[] solution(int[][] city, int[][] road) {
        int n = city.length;
        int m = road.length;
        
        Road[] roads = new Road[m];
        for (int i = 0; i < m; i++) {
            roads[i] = new Road(road[i][0], road[i][1], road[i][2], road[i][3], road[i][4]);
        }
        
        idMap = new HashMap<>();
        xs = new ArrayList<>();
        ys = new ArrayList<>();
        limits = new ArrayList<>();
        cityNodes = new int[n];
        
        for (int i = 0; i < n; i++) {
            cityNodes[i] = getNode(city[i][0], city[i][1]);
        }
        
        List<Integer>[] roadPoints = new List[m];
        for (int i = 0; i < m; i++) roadPoints[i] = new ArrayList<>();
        
        for (int i = 0; i < m; i++) {
            int mx = (roads[i].x1 + roads[i].x2) / 2;
            int my = (roads[i].y1 + roads[i].y2) / 2;
            int id = getNode(mx, my);
            updateCamera(id, roads[i].limit);
            roadPoints[i].add(id);
        }
        
        for (int i = 0; i < n; i++) {
            int x = city[i][0];
            int y = city[i][1];
            for (int j = 0; j < m; j++) {
                if (onRoad(x, y, roads[j])) {
                    roadPoints[j].add(cityNodes[i]);
                }
            }
        }
        
        for (int i = 0; i < m - 1; i++) {
            for (int j = i + 1; j < m; j++) {
                int[] point = getIntersect(roads[i], roads[j]);
                if (point == null) continue;
                
                int id = getNode(point[0], point[1]);
                roadPoints[i].add(id);
                roadPoints[j].add(id);
            }
        }
        
        int nodeCount = xs.size();
        graph = new List[nodeCount];
        for (int i = 0; i < nodeCount; i++) graph[i] = new ArrayList<>();
        
        for (int i = 0; i < m; i++) {
            List<PointOnRoad> points = new ArrayList<>();
            for (int id: roadPoints[i]) {
                points.add(new PointOnRoad(id, xs.get(id), ys.get(id)));
            }
            
            if (roads[i].isVertical()) {
                points.sort((a, b) -> a.y != b.y ? Integer.compare(a.y, b.y) : Integer.compare(a.id, b.id));
            } else {
                points.sort((a, b) -> a.x != b.x ? Integer.compare(a.x, b.x) : Integer.compare(a.id, b.id));
            }
            
            PointOnRoad prev = null;
            for (PointOnRoad cur: points) {
                if (prev != null && prev.id != cur.id) {
                    addEdge(prev.id, cur.id);
                }
                prev = cur;
            }
        }
        
        int start = cityNodes[0];
        int[] best = new int[nodeCount];
        Arrays.fill(best, -1);
        
        PriorityQueue<State> pq = new PriorityQueue<>();
        best[start] = INF;
        pq.offer(new State(start, INF));
        
        while (!pq.isEmpty()) {
            State cur = pq.poll();
            if (cur.limit < best[cur.node]) continue;
            
            for (int nextNode: graph[cur.node]) {
                int nextLimit = Math.min(cur.limit, limits.get(nextNode));
                if (nextLimit > best[nextNode]) {
                    best[nextNode] = nextLimit;
                    pq.offer(new State(nextNode, nextLimit));
                }
            }
        }
        
        int[] answer = new int[n - 1];
        for (int i = 1; i < n; i++) {
            answer[i - 1] = best[cityNodes[i]] == INF ? 0 : best[cityNodes[i]];
        }
        return answer;
    }
    
    int getNode(int x, int y) {
        long key = generateKey(x, y);
        if (idMap.containsKey(key)) return idMap.get(key);
        
        int id = xs.size();
        idMap.put(key, id);
        xs.add(x);
        ys.add(y);
        limits.add(INF);
        return id;
    }
    
    long generateKey(int x, int y) {
        // int는 32비트, y가 음수면 상위 32비트까지 다 1로 채워지기 때문에 하위 32비트에 대해서만 and 연산 필요
        return (((long) x) << 32) | (y & 0xffffffffL);
    }
    
    void updateCamera(int id, int limit) {
        limits.set(id, Math.min(limits.get(id), limit));
    }
    
    boolean onRoad(int x, int y, Road r) {
        // Road를 입력받을 때 항상 x1 <= x2, y1 <= y2가 문제 조건
        return r.x1 <= x && x <= r.x2 && r.y1 <= y && y <= r.y2;
    }
    
    int[] getIntersect(Road a, Road b) {
        if (a.isVertical() && !b.isVertical()) {
            int x = a.x1;
            int y = b.y1;
            if (onRoad(x, y, a) && onRoad(x, y, b)) return new int[] {x, y};
            return null;
        }
        
        if (!a.isVertical() && b.isVertical()) {
            int x = b.x1;
            int y = a.y1;
            if (onRoad(x, y, a) && onRoad(x, y, b)) return new int[] {x, y};
            return null;
        }
        
        if (a.isVertical() && b.isVertical()) {
            if (a.x1 != b.x1) return null;
            if (a.y2 == b.y1) return new int[] {a.x1, a.y2};
            if (a.y1 == b.y2) return new int[] {a.x1, a.y1};
            return null;
        }
        
        if (a.y1 != b.y1) return null;
        if (a.x1 == b.x2) return new int[] {a.x1, a.y1};
        if (a.x2 == b.x1) return new int[] {a.x2, a.y1};
        return null;
    }
    
    void addEdge(int a, int b) {
        if (a == b) return;
        graph[a].add(b);
        graph[b].add(a);
    }
}
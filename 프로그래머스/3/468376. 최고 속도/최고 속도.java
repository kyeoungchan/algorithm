import java.util.*;

class Solution {
    
    static class Point {
        int x, y, limit;
        
        Point(int x, int y, int limit) {
            this.x = x;
            this.y = y;
            this.limit = limit;
        }
    }
    
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
    
    static class Status implements Comparable<Status> {
        int id, limit;
        
        Status (int id, int limit) {
            this.id = id;
            this.limit = limit;
        }
        
        @Override
        public int compareTo(Status o) {
            return Integer.compare(o.limit, limit);
        }
    }
    
    static final int INF = 1_000_000_001;
    int n, m;
    int[] cityNodes;
    List<Point> points;
    Map<Long, Integer> idMap;
    Road[] roads;
    
    public int[] solution(int[][] city, int[][] road) {
        
        n = city.length;
        m = road.length;
        cityNodes = new int[n];
        points = new ArrayList<>();
        idMap = new HashMap<>();
        roads = new Road[m];
        
        for (int i = 0; i < n; i++) {
            int x = city[i][0];
            int y = city[i][1];
            cityNodes[i] = getNode(x, y, INF);
        }
        
        List<Integer>[] nodeRoads = new List[m];
        
        for (int i = 0; i < m; i++) {
            int x1 = road[i][0];
            int y1 = road[i][1];
            int x2 = road[i][2];
            int y2 = road[i][3];
            int limit = road[i][4];
            roads[i] = new Road(x1, y1, x2, y2, limit);
            
            nodeRoads[i] = new ArrayList<>();
            int mx = (x1 + x2) / 2;
            int my = (y1 + y2) / 2;
            int id = getNode(mx, my, limit);
            nodeRoads[i].add(id);
        }
        
        for (int i = 0; i < m - 1; i++) {
            for (int j = i + 1; j < m; j++) {
                Road r1 = roads[i];
                Road r2 = roads[j];
                int id = getIntersect(r1, r2);
                if (id == -1) continue;
                nodeRoads[i].add(id);
                nodeRoads[j].add(id);
            }
        }
        
        for (int i = 0; i < n; i++) {
            int id = cityNodes[i];
            Point point = points.get(id);
            for (int j = 0; j < m; j++) {
                if (isOnRoad(point, roads[j])) {
                    nodeRoads[j].add(id);
                }
            }
        }
        
        int nodeCount = points.size();
        List<Integer>[] graph = new List[nodeCount];
        for (int i = 0; i < nodeCount; i++) graph[i] = new ArrayList<>();
        
        List<PointOnRoad> pointOnRoads = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            pointOnRoads.clear();
            for (int id: nodeRoads[i]) {
                int x = points.get(id).x;
                int y = points.get(id).y;
                int limit = points.get(id).limit;
                pointOnRoads.add(new PointOnRoad(id, x, y));
            }
            if (roads[i].isVertical()) {
                pointOnRoads.sort((p1, p2) -> Integer.compare(p1.y, p2.y));
            } else {
                pointOnRoads.sort((p1, p2) -> Integer.compare(p1.x, p2.x));
            }
            
            int prev = pointOnRoads.get(0).id;
            for (int j = 1; j < pointOnRoads.size(); j++) {
                int cur = pointOnRoads.get(j).id;
                if (prev != cur) {
                    graph[prev].add(cur);
                    graph[cur].add(prev);
                }
                prev = cur;
            }
        }
        
        int[] dist = new int[nodeCount];
        Arrays.fill(dist, -1);
        
        PriorityQueue<Status> pq = new PriorityQueue<>();
        int initId = cityNodes[0];
        int initLimit = points.get(initId).limit;
        dist[initId] = initLimit;
        pq.offer(new Status(initId, initLimit));
        
        while (!pq.isEmpty()) {
            Status cur = pq.poll();
            if (cur.limit < dist[cur.id]) continue;
            for (int nextId: graph[cur.id]) {
                int nextLimit = points.get(nextId).limit;
                nextLimit = Math.min(nextLimit, cur.limit);
                if (dist[nextId] >= nextLimit) continue;
                dist[nextId] = nextLimit;
                pq.offer(new Status(nextId, nextLimit));
            }
        }

        
        int[] answer = new int[n - 1];
        for (int i = 0; i < n - 1; i++) {
            int nodeId = cityNodes[i + 1];
            if (dist[nodeId] == INF) answer[i] = 0;
            else answer[i] = dist[nodeId];
        }
        return answer;
    }
    
    int getNode(int x, int y, int limit) {
        long key = generateKey(x, y);
        if (idMap.containsKey(key)) {
            int id = idMap.get(key);
            Point point = points.get(id);
            // limits.set(id, Math.min(limit, limits.get(id)));
            point.limit = Math.min(limit, point.limit);
            return id;
        }
        
        int id = points.size();
        idMap.put(key, id);
        points.add(new Point(x, y, limit));
        return id;
    }
    
    long generateKey(int x, int y) {
        return (((long) x << 32)) | (y & 0xffffffffL);
    }
    
    int getIntersect(Road r1, Road r2) {
        if (r1.isVertical() && !r2.isVertical()) {
            if (r2.x1 <= r1.x1 && r1.x1 <= r2.x2 && r1.y1 <= r2.y1 && r2.y1 <= r1.y2) {
                return getNode(r1.x1, r2.y1, INF);
            }
        } else if (!r1.isVertical() && r2.isVertical()) {
            if (r1.x1 <= r2.x1 && r2.x1 <= r1.x2 && r2.y1 <= r1.y1 && r1.y1 <= r2.y2) {
                return getNode(r2.x1, r1.y1, INF);
            }
        } else if (r1.isVertical() && r2.isVertical() && r1.x1 == r2.x1) {
            if (r1.y2 == r2.y1) {
                return getNode(r1.x1, r1.y2, INF);
            }
            if (r1.y1 == r2.y2) {
                return getNode(r1.x1, r1.y1, INF);
            }
        } else if (!r1.isVertical() && !r2.isVertical() && r1.y1 == r2.y1) {
            if (r1.x1 == r2.x2) {
                return getNode(r1.x1, r1.y1, INF);
            }
            if (r1.x2 == r2.x1) {
                return getNode(r1.x2, r1.y1, INF);
            }
        }
        return -1;
    }
    
    boolean isOnRoad(Point point, Road road) {
        int x = point.x;
        int y = point.y;
        return road.x1 <= x && x <= road.x2 && road.y1 <= y && y <= road.y2;
    }
}
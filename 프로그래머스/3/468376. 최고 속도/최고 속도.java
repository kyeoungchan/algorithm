import java.util.*;

class Solution {
    
    static class Point implements Comparable<Point> {
        int x, y, limit;
        
        Point(int x, int y, int limit) {
            this.x = x;
            this.y = y;
            this.limit = limit;
        }
        
        @Override
        public int compareTo(Point o) {
            return x == o.x ? Integer.compare(y, o.y) : Integer.compare(x, o.x);
        }
    }
    
    static class Drive implements Comparable<Drive> {
        int id, limit;
        
        Drive(int id, int limit) {
            this.id = id;
            this.limit = limit;
        }
        
        @Override
        public int compareTo(Drive o) {
            return Integer.compare(o.limit, limit);
        }
    }
    
    int n, m, id, INF = 123456789;
    List<Point> points;
    Map<Long, Integer> posToId;
    
    public int[] solution(int[][] city, int[][] road) {
        this.n = city.length;
        this.m = road.length;
        
        // 1. 각 로드마다 포인트들이 정렬이 되지 않더라도 올라가있어야 함.
        points = new ArrayList<>();
        // 도시 번호는 1부터 시작하기 때문에 null 추가
        points.add(null);
        
        posToId = new HashMap<>();
        
        id = 1;
        for (int[] c: city) {
            points.add(new Point(c[0], c[1], INF));
            long pos = genKey(c[0], c[1]);
            posToId.put(pos, id++);
        }
        
        TreeSet<Point>[] pointOnRoad = new TreeSet[m];
        
        // 카메라 길에다가 놓기
        for (int i = 0; i < m; i++) {
            pointOnRoad[i] = new TreeSet<>();
            int camX = (road[i][0] + road[i][2]) / 2;
            int camY = (road[i][1] + road[i][3]) / 2;
            int limit = road[i][4];
            int thisId = getId(camX, camY, limit);
            pointOnRoad[i].add(points.get(thisId));
        }
        
        // 도시 길에다가 놓기
        for (int i = 1; i <= n; i++) {
            Point c = points.get(i);
            for (int j = 0; j < m; j++) {
                if (onRoad(c, road[j])) {
                    pointOnRoad[j].add(points.get(i));
                }
            }
        }
        
        // 교차로 탐색
        for (int i = 0; i < m - 1; i++) {
            int[] r1 = road[i];
            TreeSet<Point> r1Points = pointOnRoad[i];
            for (int j = i + 1; j < m; j++) {
                int[] r2 = road[j];
                Integer intersectId = getIntersect(r1, r2);
                if (intersectId == null) continue;
                TreeSet<Point> r2Points = pointOnRoad[j];
                r1Points.add(points.get(intersectId));
                r2Points.add(points.get(intersectId));
            }
        }
        
        List<Integer>[] graph = new List[id];
        for (int i = 1; i < id; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (int i = 0; i < m; i++) {
            TreeSet<Point> pointSet = pointOnRoad[i];
            Point cur = pointSet.pollFirst();
            long curKey = genKey(cur.x, cur.y);
            int curId = posToId.get(curKey);
            while (!pointSet.isEmpty()) {
                Point next = pointSet.pollFirst();
                long nextKey = genKey(next.x, next.y);
                int nextId = posToId.get(nextKey);
                graph[curId].add(nextId);
                graph[nextId].add(curId);
                curId = nextId;
            }
        }
        
        int[] dist = new int[id];
        // Arrays.fill(dist, INF);
        dist[1] = INF;
        PriorityQueue<Drive> pq = new PriorityQueue<>();
        pq.offer(new Drive(1, INF));
        
        while (!pq.isEmpty()) {
            Drive cur = pq.poll();
            if (dist[cur.id] > cur.limit) continue;
            
            for (int next: graph[cur.id]) {
                int nextLimit = Math.min(cur.limit, points.get(next).limit);
                if (dist[next] >= nextLimit) continue;
                dist[next] = nextLimit;
                pq.offer(new Drive(next, nextLimit));
            }
        }
        
        int[] answer = new int[n - 1];
        for (int i = 0; i < n - 1; i++) {
            answer[i] = dist[i + 2] == INF ? 0 : dist[i + 2];
        }
        return answer;
    }
    
    int getId(int x, int y, int limit) {
        long key = genKey(x, y);
        if (posToId.containsKey(key)) {
            int newId = posToId.get(key);
            Point point = points.get(newId);
            point.limit = Math.min(point.limit, limit);
            return newId;
        }
        int newId = id++;
        posToId.put(key, newId);
        Point point = new Point(x, y, limit);
        points.add(point);
        return newId;
    }
    
    long genKey(int x, int y) {
        return (long)x * 1_000_000_000L + y;
    }
    
    boolean onRoad(Point point, int[] road) {
        return road[0] <= point.x && point.x <= road[2] &&
            road[1] <= point.y && point.y <= road[3];
    }
    
    // 서로 다른 두 도로는 최대 한 점에서만 만납니다.
    Integer getIntersect(int[] r1, int[] r2) {
        boolean isR1Vertical = r1[0] == r1[2];
        boolean isR2Vertical = r2[0] == r2[2];
        if (isR1Vertical && isR2Vertical) {
            // 둘다 세로축에 평행한 경우
            // x값이 일단 같아야하고
            if (r1[0] != r2[0]) return null;
            
            if (r1[3] == r2[1]) {
                // r1 위에 r2가 한점으로 만나는 경우
                return getId(r1[0], r1[3], INF);
            } else if (r1[1] == r2[3]) {
                // r2 위에 r1이 한점으로 만나는 경우
                return getId(r1[0], r1[1], INF);
            } else {
                return null;
            }
        } else if (!isR1Vertical && !isR2Vertical) {
            // 둘다 가로축에 평행한 경우
            // y값이 일단 같아야 하고
            if (r1[1] != r2[1]) return null;
            
            if (r1[2] == r2[0]) {
                // r1 오른쪽에 r2가 한점으로 만나는 경우
                return getId(r1[2], r1[1], INF);
            } else if (r1[0] == r2[2]) {
                // r2 오른쪽에 r1이 한점으로 만나는 경우
                return getId(r1[0], r1[1], INF);
            } else {
                return null;
            }
        } else {
            if (isR1Vertical) {
                // r1만 세로축에 평행한 경우
                int x = r1[0];
                int y = r2[1];
                if (r1[1] <= y && y <= r1[3]) {
                    if (r2[0] <= x && x <= r2[2]) {
                        return getId(x, y, INF);
                    }
                }
            } else {
                // r2만 세로축에 평행한 경우
                int x = r2[0];
                int y = r1[1];
                if (r2[1] <= y && y <= r2[3]) {
                    if (r1[0] <= x && x <= r1[2]) {
                        return getId(x, y, INF);
                    }
                }
            }
        }
        return null;
    }
}
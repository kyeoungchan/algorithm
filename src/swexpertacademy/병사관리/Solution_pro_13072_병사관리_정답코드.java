package swexpertacademy.병사관리;

// author: 류호석(rhs0266)

public class Solution_pro_13072_병사관리_정답코드 {
    public class Node {
        int id;
        int version;
        Node next;
    }

    public Node[] nodes = new Node[200055];
    public int cnt;
    public int[] version = new int[100055];  // version[i] := ID 가 i 인 사람의 최신 버전
    public int[] num = new int[100055];      // num[i] := ID 가 i 인 사람의 team 번호

    public Node getNewNode(int id, Node next) {
        Node ret = nodes[cnt++];
        ret.id = id;
        ret.next = next;
        ret.version = ++version[id];
        return ret;
    }

    public class Team {
        Node[] head = new Node[6];
        Node[] tail = new Node[6];
    }

    public Team[] teams = new Team[6];

    public void init() {
        cnt = 0;
        for (int i=0;i<200055;i++){
            // 각 테케마다 일일이 다 새로 만들 필요는 없으므로 if문 사용
            if (nodes[i] == null) nodes[i] = new Node();
        }
        for (int i = 1; i <= 5; i++) {
            // 1~5번팀
            teams[i] = new Team();
            for (int j = 1; j <= 5; j++) {
                // 각 팀 내에 1~5점 집단들
                // 각 테케마다 사용했던 노드들을 사용해도 괜찮은 게, getNewNode에 id와 next를 0과 null로 각각 넣어주기 때문이다. cnt는 위에 0으로 세팅한다.
                teams[i].tail[j] = teams[i].head[j] = getNewNode(0, null);
            }
        }

        for (int i = 0; i <= 100000; i++) {
            version[i] = 0;
            num[i] = 0;
        }
    }

    public void hire(int mID, int mTeam, int mScore) {  // O(1)
        Node newNode = getNewNode(mID, null);
        teams[mTeam].tail[mScore].next = newNode;
        teams[mTeam].tail[mScore] = newNode;
        num[mID] = mTeam;
    }

    public void fire(int mID) {  // O(1)
        version[mID] = -1;
    }

    public void updateSoldier(int mID, int mScore) {  // O(1)
        hire(mID, num[mID], mScore);
    }

    public void updateTeam(int mTeam, int mChangeScore) {  // O(1)
        if (mChangeScore < 0) {
            for (int j = 1; j <= 5; j++) {
                int k = j + mChangeScore;
                k = k < 1 ? 1 : (k > 5 ? 5 : k);
                if (j == k) continue;

                if (teams[mTeam].head[j].next == null) continue;
                teams[mTeam].tail[k].next = teams[mTeam].head[j].next;
                teams[mTeam].tail[k] = teams[mTeam].tail[j];
                // head[j]는 null로 바꾸지 않는 것이, 0번 id의 노드로 지정이 돼있기 때문이다.
                teams[mTeam].head[j].next = null;
                teams[mTeam].tail[j] = teams[mTeam].head[j];
            }
        }
        if (mChangeScore > 0) {
            for (int j = 5; j >= 1; j--) {
                int k = j + mChangeScore;
                k = k < 1 ? 1 : (k > 5 ? 5 : k);
                if (j == k) continue;

                if (teams[mTeam].head[j].next == null) continue;
                teams[mTeam].tail[k].next = teams[mTeam].head[j].next;
                teams[mTeam].tail[k] = teams[mTeam].tail[j];
                teams[mTeam].head[j].next = null;
                teams[mTeam].tail[j] = teams[mTeam].head[j];
            }
        }
    }

    public int bestSoldier(int mTeam) {  // O(N)
        for (int j = 5; j >= 1; j--) {
            Node node = teams[mTeam].head[j].next;
            if (node == null) continue;

            int ans = 0;
            while (node != null) {
                if (node.version == version[node.id]) {
                    ans = ans < node.id ? node.id : ans;
                }
                node = node.next;
            }
            if (ans != 0) return ans;
        }
        return 0;
    }
}
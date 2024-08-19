package mincoding.사원출입관리시스템;

import java.util.*;

public class Solution_min_사원출입관리시스템 {
    private final static int MAXNUM = 9999;
    static class Member {
        private String name;
        private boolean isIn;

        public void setData(String name, boolean isIn) {
            this.name = name;
            this.isIn = isIn;
        }
    }
    Map<Integer, Member> memberMap = new HashMap<>();
    Member[] members = new Member[MAXNUM + 1];

    /**
     * 해당 함수는 최대 1,000,000번 호출됩니다.
     */
    public void init() {
        memberMap.clear();
    }

    /**
     * 회원가입: 이름과 사번을 매핑하여 가입처리
     * 가입 완료시 OK 문구
     * 처음 등록되는 사원은 [퇴근] 상태로 저장
     * 만약 기존에 존재하는 회원이라면 "ERROR" 문구
     */
    public String register(int id, String name) {
        if (memberMap.containsKey(id)) return "ERROR";
        Member member = getMember(id, name);
        memberMap.put(id, member);
        return "OK";
    }

    private Member getMember(int id, String name) {
        if (members[id] == null) members[id] = new Member();
        members[id].setData(name, false);
        return members[id];
    }

    /**
     * 출근 상태라면 퇴근 상태로 변하며 "이름 OUT" 문구
     * 퇴근 상태라면 출근 상태로 변하며 "이름 IN" 문구
     * 등록되지 않은 사원이라면 "ERROR ERROR"
     * @param id
     * @return
     */
    public String[] inout(int id) {
        if (!memberMap.containsKey(id)) return new String[]{"ERROR", "ERROR"};
        members[id].isIn = !members[id].isIn;
        return new String[]{members[id].name, members[id].isIn ? "IN" : "OUT"};
    }
}

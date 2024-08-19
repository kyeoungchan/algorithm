package mincoding.고객이름빨리찾기;

import java.util.*;

/**
 * 동일한 회원번호를 선호하는 고객이 2명 이상 있을 경우, 선착순
 * 이후에 신청하였던 고객은 회원가입 진행되지 않음
 */
public class Solution_min_고객이름빨리찾기 {

    private Map<Integer, String> memberMap = new HashMap<>();

    public void init() {
        memberMap.clear();
    }

    public void add(int id, String name) {
        if (memberMap.containsKey(id)) return;
        memberMap.put(id, name);
    }

    String find(int id) {
        return memberMap.get(id);
    }
}

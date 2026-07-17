import java.util.*;

class Solution {
    
    static class Cell {
        int id;
        String value;
        Set<Integer> mergeList = new HashSet<>();
        
        Cell(int id, String value, int pos) {
            this.id = id;
            this.value = value;
            addPos(pos);
        }
        
        Cell(int id, int pos) {
            this.id = id;
            mergeList.add(pos);
            value = "EMPTY";
        }
        
        void addPos(int pos) {
            mergeList.add(pos);
        }
        
        @Override
        public int hashCode() {
            return id;
        }
        
        @Override
        public boolean equals(Object o) {
            if (o instanceof Cell) {
                Cell c = (Cell) o;
                return id == c.id;
            }
            return false;
        }
        
        @Override
        public String toString() {
            return "id: " + id + ", value: " + value + ", mergeList:  " + mergeList;
        }
    }
    
    public String[] solution(String[] commands) {
        List<String> printList = new ArrayList<>();
        Map<Integer, Cell> posMap = new HashMap<>();
        Map<String, Set<Cell>> valueMap = new HashMap<>();
        int id = 0;
        for (String command: commands) {
            String[] tokens = command.split(" ");
            String firstCommand = tokens[0];
            if (firstCommand.equals("UPDATE")) {
                if (tokens.length == 4) {
                    // "UPDATE r c value"
                    int pos = genPos(tokens[1], tokens[2]);
                    String value = tokens[3];
                    Cell cell = null;
                    if (!posMap.containsKey(pos)) {
                        cell = new Cell(id++, value, pos);
                        posMap.put(pos, cell);
                    } else {
                        // 병합된 셀들 모두 value로 업데이트 된다.
                        cell = posMap.get(pos);
                        if (valueMap.containsKey(cell.value)) {
                            // 이전 값을 갖는 목록에서 현재 셀 삭제
                            valueMap.get(cell.value).remove(cell);
                        }
                        cell.value = value;
                    }
                    
                    // 새로운 값을 갖는 목록에 현재 셀 추가
                    if (!valueMap.containsKey(value)) valueMap.put(value, new HashSet<>());
                    valueMap.get(value).add(cell);
                    
                } else {
                    // "UPDATE value1 value2"
                    String value1 = tokens[1];
                    String value2 = tokens[2];
                    
                    // 값이 같은 경우 skip feat. AI
                    if (value1.equals(value2)) continue;
                    
                    // value1 목록이 없으면 스킵
                    if (!valueMap.containsKey(value1)) continue;
                    
                    Set<Cell> set = valueMap.get(value1);
                    // value1 목록들을 모두 value2로 변경
                    for (Cell cell : set) {
                        cell.value = value2;
                    }
                    
                    // value2 목록에 셀들 추가
                    if (!valueMap.containsKey(value2)) {
                        valueMap.put(value2, new HashSet<>());
                    }
                    valueMap.get(value2).addAll(set);
                    
                    // value1 목록 자체를 삭제
                    valueMap.put(value1, new HashSet<>());
                }
            } else if (firstCommand.equals("MERGE")) {
                int pos1 = genPos(tokens[1], tokens[2]);
                int pos2 = genPos(tokens[3], tokens[4]);
                // 같은 값일 경우 무시한다.
                if (pos1 == pos2) continue;
                
                Cell cell1 = posMap.get(pos1);
                Cell cell2 = posMap.get(pos2);
                
                if (cell1 == null) {
                    if (cell2 == null) {
                        // 둘다 빈 셀인 경우
                        Cell cell = new Cell(id++, pos1);
                        cell.addPos(pos2);
                        posMap.put(pos1, cell);
                        posMap.put(pos2, cell);
                    } else {
                        // cell2는 빈셀이 아닌 경우
                        cell2.addPos(pos1);
                        posMap.put(pos1, cell2);
                    }
                    
                } else {
                    if (cell2 == null) {
                        // cell2만 빈셀인 경우
                        cell1.addPos(pos2);
                        posMap.put(pos2, cell1);
                    } else {
                        // 서로 같은 셀을 바라보고 있는 경우는 같은 그룹에 속해있다는 소리이므로 skip (feat. AI)
                        if (cell1.equals(cell2)) continue;
                        // 둘다 빈셀이 아닌 경우
                        if (isEmptyCell(cell1)) {
                            if (isEmptyCell(cell2)) {
                                // null은 아니지만 병합이 된적이 있고 값은 없는 경우
                                cell1.mergeList.addAll(cell2.mergeList);
                                for (int mPos: cell2.mergeList) {
                                    posMap.put(mPos, cell1);
                                }
                            } else {
                                // cell2만 값이 있는 경우
                                cell2.mergeList.addAll(cell1.mergeList);
                                for (int mPos: cell1.mergeList) {
                                    posMap.put(mPos, cell2);
                                }
                            }
                        } else {
                            if (isEmptyCell(cell2)) {
                                // cell1만 값이 있는 경우
                                cell1.mergeList.addAll(cell2.mergeList);
                                for (int mPos: cell2.mergeList) {
                                    posMap.put(mPos, cell1);
                                }
                            } else {
                                // cell1,2 모두 값이 있는 경우
                                for (int mPos: cell2.mergeList) {
                                    posMap.put(mPos, cell1);
                                }
                                cell1.mergeList.addAll(cell2.mergeList);
                                valueMap.get(cell2.value).remove(cell2);
                            }
                        }
                    }
                }
            } else if (firstCommand.equals("UNMERGE")) {
                int pos = genPos(tokens[1], tokens[2]);
                if (!posMap.containsKey(pos)) continue;
                Cell cell = posMap.get(pos);
                for (int mPos: cell.mergeList) {
                    posMap.remove(mPos);
                }
                
                posMap.put(pos, cell);
                cell.mergeList.clear();
                cell.mergeList.add(pos);
            } else {
                // PRINT
                int pos = genPos(tokens[1], tokens[2]);
                String printVal = "";
                if (!posMap.containsKey(pos)) {
                    printVal ="EMPTY";
                } else {
                    printVal = posMap.get(pos).value;
                }
                // System.out.println(printVal);
                printList.add(printVal);
            }
            // System.out.println("command: " + command);
            // System.out.println("posMap: " + posMap);
            // System.out.println("valueMap: " + valueMap);
            // System.out.println("printList: " + printList);
            // System.out.println();
        }
        
        
        String[] answer = new String[printList.size()];
        for (int i = 0; i < printList.size(); i++) {
            answer[i] = printList.get(i);
        }
        return answer;
    }
    
    int genPos(String rStr, String cStr) {
        int r = Integer.parseInt(rStr) - 1;
        int c = Integer.parseInt(cStr) - 1;
        return r * 50 + c;
    }

    boolean isEmptyCell(Cell cell) {
        return cell.value.equals("EMPTY");
    }
}
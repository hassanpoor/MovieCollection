package domain.imperative;

import util.CommonUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImperativeExample {

    public static void main(String[] args) {
        var namesList = CommonUtil.createNamesList();
        var newNamesList = namesGreaterThanSize(namesList, 3);
        System.out.println("newNamesList : " + newNamesList);
    }

    private static Set namesGreaterThanSize(List<String> namesList, int size) {
        Set newNamesList = new HashSet();
        for (String name : namesList) {
            if (name.length() > size && !newNamesList.contains(name)) {
                newNamesList.add(name.toUpperCase());
            }
        }
        return newNamesList;
    }
}

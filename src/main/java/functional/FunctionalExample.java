package functional;

import org.reactivestreams.Publisher;
import util.CommonUtil;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionalExample {

    public static void main(String[] args) {
        var namesList = CommonUtil.createNamesList();
//        var newNamesList = namesGreaterThanSize(namesList, 3);
        var newNamesList = namesGreaterThanSizeParalel(namesList, 3);
        System.out.println("newNamesList : " + newNamesList);
    }

    private static List<String> namesGreaterThanSize(List<String> namesList, int length) {
//        return namesList.stream().filter(FunctionalExample::filterString).map(String::toUpperCase).distinct().sorted().collect(Collectors.toList());
        return namesList.stream().filter(s -> filterString(s, length)).map(String::toUpperCase).distinct().sorted().collect(Collectors.toList());
    }

    private static List<String> namesGreaterThanSizeParalel(List<String> namesList, int length) {
        return namesList.stream().filter(s -> filterString(s, length)).map(String::toUpperCase).distinct().sorted().collect(Collectors.toList());
    }

    public static boolean filterString(String s, int length) {
        return (s.length() > length);
    }

}

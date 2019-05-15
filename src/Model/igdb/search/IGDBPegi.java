package Model.igdb.search;

import java.util.ArrayList;

public enum IGDBPegi {

    PEGI3(1, 3),
    PEGI7(2, 7),
    PEGI12(3, 12),
    PEGI16(4, 16),
    PEGI18(5, 18);

    private final int value;
    private final int minAge;

    IGDBPegi(int value, int minAge) {
        this.value = value;
        this.minAge = minAge;
    }

    public int getValue() {
        return this.value;
    }

    public int getMinAge() {
        return minAge;
    }

    public static ArrayList<String> getPegiFromAge(int age) {

        ArrayList<String> agePegi = new ArrayList<>();

        for(IGDBPegi pegi : IGDBPegi.values()) {
            if(pegi.getMinAge() <= age) {
                agePegi.add(String.valueOf(pegi.getValue()));
            }
        }

        return agePegi;

    }

}

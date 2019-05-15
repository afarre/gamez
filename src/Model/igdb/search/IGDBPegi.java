package Model.igdb.search;

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

    public static IGDBPegi getPegiFromAge(int age) {

        IGDBPegi agePegi = null;

        for(IGDBPegi pegi : IGDBPegi.values()) {
            if(pegi.getMinAge() <= age && (agePegi == null || agePegi.getMinAge() < pegi.getMinAge())) {
                agePegi = pegi;
            }
        }

        return agePegi;

    }

}

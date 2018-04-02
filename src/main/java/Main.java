public class Main {

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.getAllLikedPeople("Kamil", "Wróbel"));
        System.out.println(s.getCitizenships("Kamil", "Wróbel"));
        System.out.println(s.getPeopleWhoLikes("Cristiano", "Ronaldo"));
        System.out.println(s.getLivedCity("Kamil", "Wróbel"));
        System.out.println();
        System.out.println(s.getBirthCity("Kamil", "Wróbel"));
        System.out.println();
        System.out.println(s.getAllCities("Poland"));
        System.out.println();
        System.out.println(s.getAllCitizens("Poland"));
        System.out.println();
        System.out.println(s.getBirthsInCity("Sosnowiec"));
        System.out.println();
        System.out.println(s.getCountryOfCity("Ookland"));
        System.out.println();
        System.out.println(s.getLivesInCity("Madrid"));


    }
}

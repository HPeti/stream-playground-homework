package countries;

import java.io.IOException;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.*;

public class Homework1 {

    private List<Country> countries;

    public Homework1() {
        countries = new CountryRepository().getAll();
    }

    /**
     * Returns whether there is at least one country with the word "island" in its name ignoring case.
     */
    public boolean streamPipeline1() {
        for (Country country : countries) {
            String nev = country.getName();
            if (nev.toLowerCase().contains("island")) {
                return true;
            }
        }
        return false;
    }

    /**
     *  Returns the first country name that contains the word "island" ignoring case.
     */
    public Optional<String> streamPipeline2() {
        for (Country country : countries) {
            String nev = country.getName();
            if (nev.toLowerCase().contains("island")) {
                return Optional.of(nev);
            }
        }
        return Optional.empty();
    }

    /**
     * Prints each country name in which the first and the last letters are the same ignoring case.
     */
    public void streamPipeline3() {
        for (Country country : countries) {
            String nev = country.getName();
            if (nev.toLowerCase().charAt(0) == nev.toLowerCase().charAt(nev.length() - 1)) {
                System.out.println(nev);
            }
        }
    }

    /**
     * Prints the populations of the first ten least populous countries.
     */
    public void streamPipeline4() {
        List<Long> toSort = new ArrayList<>();
        for (Country country : countries) {
            Long population = country.getPopulation();
            toSort.add(population);
        }
        toSort.sort(null);
        long limit = 10;
        for (Long population : toSort) {
            if (limit-- == 0) break;
            System.out.println(population);
        }
    }

    /**
     * Prints the names of the first ten least populous countries.
     */
    public void streamPipeline5() {
        List<Country> toSort = new ArrayList<>();
        for (Country country : countries) {
            toSort.add(country);
        }
        toSort.sort(Comparator.comparingLong(Country::getPopulation));
        long limit = 10;
        for (Country country : toSort) {
            if (limit-- == 0) break;
            String name = country.getName();
            System.out.println(name);
        }
    }

    /**
     * Returns summary statistics about the number of country name translations associated with each country.
     */
    public IntSummaryStatistics streamPipeline6() {
        IntSummaryStatistics stat = new IntSummaryStatistics();
        for (Country country : countries) {
            int size = country.getTranslations().size();
            stat.accept(size);
        }
        return stat;
    }

    /**
     * Prints the names of countries in the ascending order of the number of timezones.
     */
    public void streamPipeline7() {
        List<Country> toSort = new ArrayList<>();
        for (Country country : countries) {
            toSort.add(country);
        }
        toSort.sort(Comparator.comparingInt(country -> country.getTimezones().size()));
        for (Country country : toSort) {
            String name = country.getName();
            System.out.println(name);
        }
    }

    /**
     * Prints the number of timezones for each country in the form {@code name:timezones}, in the ascending order of the number of timezones.
     */
    public void streamPipeline8() {
        // TODO
        List<Country> toSort = new ArrayList<>();
        for (Country country : countries) {
            toSort.add(country);
        }
        toSort.sort(Comparator.comparingInt(country -> country.getTimezones().size()));
        for (Country country : toSort) {
            System.out.println(country.getName() + " : " + country.getTimezones());
        }
    }

    /**
     * Returns the number of countries with no Spanish country name translation (the Spanish language is identifi
ed by the language code "es").
     */
    public long streamPipeline9() {
        long count = 0L;
        for (Country country : countries) {
            if (country.getTranslations().containsKey("es")) {
                count++;
            }
        }
        return count;
    }

    /**
     * Prints the names of countries with null area.
     */
    public void streamPipeline10() {
        for (Country country : countries) {
            if (country.getArea() == null) {
                String name = country.getName();
                System.out.println(name);
            }
        }
    }

    /**
     * Prints all distinct language tags of country name translations sorted in alphabetical order.
     */
    public void streamPipeline11() {
        List<String> toSort = new ArrayList<>();
        Set<String> uniqueValues = new HashSet<>();
        for (Country country : countries) {
            Map<String, String> tran = country.getTranslations();
            for (String s : tran.keySet()) {
                if (uniqueValues.add(s)) {
                    toSort.add(s);
                }
            }
        }
        toSort.sort(null);
        for (String s : toSort) {
            System.out.println(s);
        }
    }

    /**
     * Returns the average length of country names.
     */
    public double streamPipeline12() {
        // TODO
        long sum = 0;
        long count = 0;
        for (Country country : countries) {
            int length = country.getName().length();
            sum += length;
            count++;
        }
        return (count > 0 ? OptionalDouble.of((double) sum / count) : OptionalDouble.empty()).getAsDouble();
    }

    /**
     * Prints all distinct regions of the countries with null area.
     */
    public void streamPipeline13() {
        Set<Region> uniqueValues = new HashSet<>();
        for (Country country : countries) {
            if (country.getArea() == null) {
                Region region = country.getRegion();
                if (uniqueValues.add(region)) {
                    System.out.println(region);
                }
            }
        }
    }

    /**
     * Returns the largest country with non-null area.
     */
    public Optional<Country> streamPipeline14() {
        boolean seen = false;
        Country best = null;
        Comparator<Country> comparator = Comparator.comparing(Country::getArea);
        for (Country country : countries) {
            if (country.getArea() != null) {
                if (!seen || comparator.compare(country, best) > 0) {
                    seen = true;
                    best = country;
                }
            }
        }
        return seen ? Optional.of(best) : Optional.empty();

    }

    /**
     * Prints the names of countries with a non-null area below 1.
     */
    public void streamPipeline15() {
        for (Country country : countries) {
            if (country.getArea() != null && country.getArea().compareTo(new BigDecimal(1)) < 0) {
                String name = country.getName();
                System.out.println(name);
            }
        }
    }

    /**
     * Prints all distinct timezones of European and Asian countries.
     */
    public void streamPipeline16() {
        Set<ZoneId> uniqueValues = new HashSet<>();
        for (Country country : countries) {
            if (country.getRegion() == Region.EUROPE || country.getRegion() == Region.ASIA) {
                List<ZoneId> zones = country.getTimezones();
                for (ZoneId zone : zones) {
                    if (uniqueValues.add(zone)) {
                        System.out.println(zone);
                    }
                }
            }
        }
    }

}

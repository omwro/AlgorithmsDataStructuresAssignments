package nl.hva.ict.se.ads;

import java.util.*;

/**
 * @author Asignment maker, Ömer Erdem & Daan Molendijk
 * Holds the name, archer-id and the points scored for 30 arrows.
 * <p>
 * Archers MUST be created by using one of the generator methods. That is way the constructor is private and should stay
 * private. You are also not allowed to add any constructor with an access modifier other then private unless it is for
 * testing purposes in which case the reason why you need that constructor must be contained in a very clear manner
 * in your report.
 */
public class Archer {
    public static final int MAX_ARROWS = 3;
    public static final int MAX_ROUNDS = 10;
    public static final int MAX_SCORE = 11; // The maximum score for the weighted score.


    private static Random randomizer = new Random();
    private static int numberOfArchers = 135788;

    private final int id; // Once assigned a value is not allowed to change.
    private String firstName; // The firstname of the archer.
    private String lastName; // The Lastname of the archer

    private int[] totalArray; // the total score of each round of an player in an array
    private int totalScore; // is the total score.
    private int[] weightedArray; // the weighedArray holds for each index how many times the bowman shot that ring.
    private int weightedScore; // is the weighted score.

    /**
     * Constructs a new instance of bowman and assigns a unique ID to the instance. The ID is not allowed to ever
     * change during the lifetime of the instance! For this you need to use the correct Java keyword.Each new instance
     * is a assigned a number that is 1 higher than the last one assigned. The first instance created should have
     * ID 135788;
     *
     * @param firstName the archers first name.
     * @param lastName  the archers surname.
     */
    private Archer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

        this.id = numberOfArchers++;

        totalArray = new int[MAX_ROUNDS];
        weightedArray = new int[MAX_SCORE];

    }

    /**
     * Registers the point for each of the three arrows that have been shot during a round. The <code>points</code>
     * parameter should hold the three points, one per arrow.
     *
     * @param round  the round for which to register the points.
     * @param points the points shot during the round.
     */
    public void registerScoreForRound(int round, int[] points) {
        int totalPointsRound = 0;
        for (int i = 0; i < points.length; i++) {
            weightedArray[points[i]] += 1;
            totalPointsRound += points[i];
        }
        this.totalArray[round] = totalPointsRound;
    }

    /**
     * This method updates the score of the player.
     * It updates the total score and the weighted score.
     */
    public void updateScore() {
        // Calculation of the Total Score
        this.totalScore = 0;
        for (int i = 0; i < this.totalArray.length; i++) {
            this.totalScore += this.totalArray[i];
        }

        // Calculation of the Weighted Score
        this.weightedScore = 0;
        int minusNumber = 0;

        for (int i = 0; i < weightedArray.length; i++) {
            if (i == 0) {
                minusNumber = weightedArray[0] * 7;
            } else {
                weightedScore += weightedArray[i] * (i + 1);
            }
        }
        weightedScore = totalScore - minusNumber;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getWeightedScore() {
        return weightedScore;
    }

    /**
     * This methods creates a List of archers.
     *
     * @param nrOfArchers the number of archers in the list.
     * @return an List of generated archers.
     */
    public static List<Archer> generateArchers(int nrOfArchers) {
        List<Archer> archers = new ArrayList<>(nrOfArchers);
        for (int i = 0; i < nrOfArchers; i++) {
            Archer archer = new Archer(Names.nextFirstName(), Names.nextSurname());
            letArcherShoot(archer, nrOfArchers % 100 == 0);
            archers.add(archer);
        }
        return archers;

    }

    /**
     * This methods creates a Iterator that can be used to generate all the required archers. If you implement this
     * method it is only allowed to create an instance of Archer inside the next() method!
     *
     * <b>THIS METHODS IS OPTIONAL</b>
     *
     * @param nrOfArchers the number of archers the Iterator will create.
     * @return
     */
    public static Iterator<Archer> generateArchers(long nrOfArchers) {
        return null;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    /**
     * This method compares two archers with each other.
     *
     * @param other is a archer object that is compared with this object.
     * @return the archer that is the winner.
     */
    public Archer compareTo(Archer other) {
        if (this.totalScore > other.getTotalScore()) {
            return this;
        } else if (this.totalScore < other.getTotalScore()) {
            return other;
        } else {
            if (this.getWeightedScore() > other.getWeightedScore()) {
                return this;
            } else if (this.getWeightedScore() < other.getWeightedScore()) {
                return other;
            } else {
                if (this.id > other.getId()) {
                    return this;
                } else {
                    return other;
                }
            }
        }
    }

    private static void letArcherShoot(Archer archer, boolean isBeginner) {
        for (int round = 0; round < MAX_ROUNDS; round++) {
            archer.registerScoreForRound(round, shootArrows(isBeginner ? 0 : 1));
        }
        archer.updateScore();
    }

    private static int[] shootArrows(int min) {
        int[] points = new int[MAX_ARROWS];
        for (int arrow = 0; arrow < MAX_ARROWS; arrow++) {
            points[arrow] = shoot(min);
        }
        return points;
    }

    private static int shoot(int min) {
        return Math.max(min, randomizer.nextInt(11));
    }

    @Override
    public String toString() {
        return getId() + " ( " + getTotalScore() + " / " + getWeightedScore() + " ) " + getFirstName() + " " + getLastName();
    }
}

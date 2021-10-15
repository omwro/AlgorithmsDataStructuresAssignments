package nl.hva.ict.se.ads;

import java.util.Comparator;

/**
 * @author Daan Molendijk & Ömer Erdem
 * This comperator is used for comparing two random archers.
 */
public class ArcherComperator implements Comparator<Archer> {
    /**
     * This methods compares two archers
     * @param o1 the first archer
     * @param o2 the second archer
     * @return an integer that indicates who the winner is.
     */
    @Override
    public int compare(Archer o1, Archer o2) {
        if(o1.getTotalScore() > o2.getTotalScore()){
            return 1;
        } else if(o1.getTotalScore() < o2.getTotalScore()){
            return -1;
        } else {
            if(o1.getWeightedScore() > o2.getWeightedScore()){
                return 1;
            } else if(o1.getWeightedScore() < o2.getWeightedScore()){
                return -1;
            } else {
                if(o1.getId() > o2.getId()){
                    return 1;
                } else if(o1.getId() < o2.getId()){
                    return -1;
                }
            }
            return 0;
        }
    }
}

package org.cct.cp2019a.treasurehunt.model;

import org.cct.cp2019a.treasurehunt.enumeration.ShovelType;
import org.cct.cp2019a.treasurehunt.exception.NoMoreDigPointsException;

import java.util.Objects;

/**
 * This model represents a game player.
 */
public class Player {

    public static final int INITIAL_PIRATE_POINTS = 100;

    private String fullName;
    private int age;
    private int piratePoints;
    private Shovel shovel = new Shovel(ShovelType.WOOD);

    public Player(String fullName, int age) {
        this.fullName = fullName;
        this.age = age;
        this.piratePoints = INITIAL_PIRATE_POINTS - this.shovel.getDigPoints();
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public int getPiratePoints() {
        return piratePoints;
    }

    public void setPiratePoints(int piratePoints) {
        this.piratePoints = piratePoints;
    }

    public Shovel getShovel() {
        return shovel;
    }

    public void setShovel(Shovel shovel) {
        this.shovel = shovel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return age == player.age && fullName.equals(player.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, age);
    }

    @Override
    public String toString() {
        return "Pirate '" + fullName + "', " + age + " years old - Score: " + piratePoints + " Pirate points";
    }

    /**
     * This method is responsible for changing the state on the Player when the action dig is called.
     */
    public void dig() {
        Integer digPoints = this.shovel.getDigPoints();
        if (digPoints <= 0) {
            throw new NoMoreDigPointsException();
        }
        this.shovel.setDigPoints(digPoints-1);
    }

    /**
     * Add the reward for the player.
     * @param value the value to be added on the Player's pirate points.
     */
    public void addReward(int value) {
        this.piratePoints += value;
    }
}

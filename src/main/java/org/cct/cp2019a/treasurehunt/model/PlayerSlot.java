package org.cct.cp2019a.treasurehunt.model;

/**
 * The slots for player selection (used before the player is created)
 */
public class PlayerSlot {

    private String fullName;
    private int age;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

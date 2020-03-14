package org.cct.cp2019a.treasurehunt.enumeration;

/**
 * This class represents the type of the shovel and guarantee the dig points rule.
 * VALIDATION RULE: The number of “Dig Points” must be a minimum of 4 and a maximum of 7.
 */
public enum ShovelType {

    WOOD(4, 20d),
    IRON(5, 25d),
    DIAMOND(7, 35d);

    private Integer digPoints;
    private Double cost;

    ShovelType(Integer digPoints, Double cost) {
        this.digPoints = digPoints;
        this.cost = cost;
    }

    public Integer getDigPoints() {
        return digPoints;
    }

    public Double getCost() {
        return cost;
    }
}

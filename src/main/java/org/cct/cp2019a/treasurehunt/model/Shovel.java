package org.cct.cp2019a.treasurehunt.model;

import org.cct.cp2019a.treasurehunt.enumeration.ShovelType;
import org.cct.cp2019a.treasurehunt.util.NumberUtils;

/**
 * This model represents the pirate shovel.
 */
public class Shovel {

    private String name;
    private Integer digPoints;
    private Double cost;

    public Shovel(ShovelType shovelType) {
        this.name = shovelType.name();
        this.digPoints = shovelType.getDigPoints();
        this.cost = shovelType.getCost();
    }

    public String getName() {
        return name;
    }

    public Integer getDigPoints() {
        return digPoints;
    }

    public void setDigPoints(Integer digPoints) {
        this.digPoints = digPoints;
    }

    public Double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Shovel '" + name + '\'' + ", digPoints: " + digPoints + ", cost: " + NumberUtils.formatCurrency(cost);
    }
}

package com.idark.valoria.registries.item;

public class ToolStats{
    public static ToolStats
    sword = new ToolStats(3, -2.4F),
    throwable = new ToolStats(3, -2.4F),
    large_sword = new ToolStats(3, -2.8F),
    scythe = new ToolStats(7, -3.25F),
    spear = new ToolStats(2, -3.0F),
    katana = new ToolStats(1, -2F),
    pickaxe = new ToolStats(-1, -2.8F),
    axe = new ToolStats(4, -3.0F),
    shovel = new ToolStats(1, -3.0F),
    hoe = new ToolStats(-4, 0.0F),
    multiTool = new ToolStats(3, -3.5F);

    public float damage;
    public float speed;
    public ToolStats(float damage, float speed) {
        this.damage = damage;
        this.speed = speed;
    }

    public float getDamage() {
        return damage;
    }

    public float getSpeed() {
        return speed;
    }
}

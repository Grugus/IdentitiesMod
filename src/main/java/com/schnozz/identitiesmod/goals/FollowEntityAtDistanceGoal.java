package com.schnozz.identitiesmod.goals;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class FollowEntityAtDistanceGoal extends Goal {
    private final Mob mob;
    private final Entity target;
    private final double speed;
    private final float minDistance;

    public FollowEntityAtDistanceGoal(Mob mob, Entity target, double speed, float minDistance) {
        this.mob = mob;
        this.target = target;
        this.speed = speed;
        this.minDistance = minDistance;
    }

    @Override
    public boolean canUse() {
        return target != null && target.isAlive() && mob.distanceTo(target) > minDistance;
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void tick() {
        if (mob.distanceTo(target) > minDistance) {
            mob.getNavigation().moveTo(target, speed);
        } else {
            mob.getNavigation().stop();
        }
    }

    @Override
    public void stop() {
        mob.getNavigation().stop();
    }
}

package com.schnozz.identitiesmod.items;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class BoundingBoxVisualizer {

    public static void showAABB(Level level, AABB box) {
        if (level.isClientSide) { // Ensure this only runs on the client
            double step = 0.5; // Distance between particles

            for (double x = box.minX; x <= box.maxX; x += step) {
                for (double y = box.minY; y <= box.maxY; y += step) {
                    for (double z = box.minZ; z <= box.maxZ; z += step) {

                        // Count how many coordinates are on the box's outer faces
                        int edgeCount = 0;
                        if (x == box.minX || x == box.maxX) edgeCount++;
                        if (y == box.minY || y == box.maxY) edgeCount++;
                        if (z == box.minZ || z == box.maxZ) edgeCount++;

                        // Only draw particles along edges (2 or more coordinates on outer bounds)
                        if (edgeCount >= 2) {
                            level.addParticle(ParticleTypes.HAPPY_VILLAGER, x, y, z, 0, 0, 0);
                        }
                    }
                }
            }
        }
    }
}



package com.schnozz.identitiesmod.networking.handlers;

import com.schnozz.identitiesmod.networking.payloads.GravityPayload;
import com.schnozz.identitiesmod.networking.payloads.ParryParticlePayload;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerParryParticleHandler {
    public static void handle(ParryParticlePayload payload, IPayloadContext context)
    {
        Level level = context.player().level();
        Player player = context.player();

        Vec3 lookVec = player.getLookAngle(); // normalized direction vector
        Vec3 playerPos = player.position().add(0, player.getEyeHeight() - 0.5, 0);
        Vec3 center = playerPos.add(lookVec.scale(1)); // 2 blocks in front

        // 2. Define the size of the X
        double size = 0.5;

        // 3. Define two diagonal directions
        Vec3 offset1 = new Vec3(size, size, 0);
        Vec3 offset2 = new Vec3(size, -size, 0);

        // Rotate offsets to match player's facing direction
        Vec3 left = lookVec.cross(new Vec3(0, 1, 0)).normalize();
        Vec3 up = left.cross(lookVec).normalize();

        Vec3 diag1Start = center.add(left.scale(-size)).add(up.scale(-size));
        Vec3 diag1End   = center.add(left.scale(size)).add(up.scale(size));

        Vec3 diag2Start = center.add(left.scale(-size)).add(up.scale(size));
        Vec3 diag2End   = center.add(left.scale(size)).add(up.scale(-size));

        // 4. Spawn particles along both diagonals
        spawnLine(level, diag1Start, diag1End, payload.particle());
        spawnLine(level, diag2Start, diag2End, payload.particle());
    }

    private static void spawnLine(Level level, Vec3 start, Vec3 end, DustColorTransitionOptions particle) {
        int steps = 10;
        Vec3 step = end.subtract(start).scale(1.0 / steps);
        for (int i = 0; i <= steps; i++) {
            Vec3 pos = start.add(step.scale(i));
            ((ServerLevel)level).sendParticles(
                    particle,
                    pos.x, pos.y, pos.z,
                    5,
                    0,0,0,
                    0
            );
        }
    }
}

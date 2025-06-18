package com.schnozz.identitiesmod.networking.handlers;

import com.schnozz.identitiesmod.networking.payloads.ChaosParticlePayload;
import com.schnozz.identitiesmod.networking.payloads.ParryParticlePayload;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerChaosParticleHandler {
    public static void handle(ChaosParticlePayload payload, IPayloadContext context)
    {
        if(context.player().level() instanceof ServerLevel level && payload.entity().hasUUID("UUID"))
        {
            Player player = context.player();
            Entity entity = level.getEntity(payload.entity().getUUID("UUID"));


            assert entity != null;

            Vec3 center = entity.position().add(0, 2.0, 0); // Above the head
            double radius = 1.0;
            int rings = 10;
            int segments = 20;

            for (int i = 0; i <= rings; i++) {
                double phi = Math.PI * i / rings; // from 0 to PI

                for (int j = 0; j < segments; j++) {
                    double theta = 2 * Math.PI * j / segments; // from 0 to 2PI

                    // Convert spherical to Cartesian coordinates
                    double x = radius * Math.sin(phi) * Math.cos(theta);
                    double y = radius * Math.cos(phi);
                    double z = radius * Math.sin(phi) * Math.sin(theta);

                    Vec3 pos = center.add(x, y, z);


                    ((ServerLevel)level).sendParticles(
                            payload.particle(),
                            pos.x, pos.y, pos.z,
                            1,
                            0,0,0,
                            0
                    );
                }
            }
        }

    }

}

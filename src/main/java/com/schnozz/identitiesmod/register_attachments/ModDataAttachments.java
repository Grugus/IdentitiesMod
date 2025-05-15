package com.schnozz.identitiesmod.register_attachments;

import com.mojang.serialization.Codec;
import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.cooldown.CooldownAttachment;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModDataAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, IdentitiesMod.MODID);

    public static final Supplier<AttachmentType<Integer>> HEALTH_NEEDED = ATTACHMENT_TYPES.register(
            "needed_health", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).copyOnDeath().build()
    );

    public static final Supplier<AttachmentType<String>> POWER_TYPE = ATTACHMENT_TYPES.register(
            "power_type", () -> AttachmentType.builder(() -> "").serialize(Codec.STRING).copyOnDeath().build()
    );




    public static final Supplier<AttachmentType<CompoundTag>> ENTITY_HELD = ATTACHMENT_TYPES.register(
            "entity_data",
            () -> AttachmentType.builder(CompoundTag::new) // default = empty tag therefore not null
                    .build()
    );

    public static final Supplier<AttachmentType<CooldownAttachment>> COOLDOWN =
            ATTACHMENT_TYPES.register("cooldowns", () ->
                    AttachmentType.builder(CooldownAttachment::new)
                            .serialize(CooldownAttachment.CODEC)
                            .copyOnDeath()
                            .build()
            );

    public static final Supplier<AttachmentType<CooldownAttachment>> ADAPTION =
            ATTACHMENT_TYPES.register("adaption", () ->
                    AttachmentType.builder(CooldownAttachment::new)
                            .serialize(CooldownAttachment.CODEC)
                            .build()
            );
}

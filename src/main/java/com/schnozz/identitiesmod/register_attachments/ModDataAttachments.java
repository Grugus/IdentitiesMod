package com.schnozz.identitiesmod.register_attachments;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.attachments.AdaptationAttachment;
import com.schnozz.identitiesmod.cooldown.CooldownAttachment;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ModDataAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, IdentitiesMod.MODID);

    public static final Supplier<AttachmentType<Integer>> HEALTH_NEEDED = ATTACHMENT_TYPES.register(
            "needed_health", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).copyOnDeath().build()
    );


    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> CHARGES = ATTACHMENT_TYPES.register("charges",() -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build() );

    public static final Supplier<AttachmentType<String>> POWER_TYPE = ATTACHMENT_TYPES.register(
            "power_type", () -> AttachmentType.builder(() -> "").serialize(Codec.STRING).copyOnDeath().build()
    );

    public static final Supplier<AttachmentType<Boolean>> UNDER_CONTROL = ATTACHMENT_TYPES.register(
            "under_control", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).copyOnDeath().build()
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



    public static final Supplier<AttachmentType<AdaptationAttachment>> ADAPTION =
            ATTACHMENT_TYPES.register("adaption", () ->
                    AttachmentType.builder(AdaptationAttachment::new)
                            .serialize(AdaptationAttachment.CODEC)
                            .build()
            );



}

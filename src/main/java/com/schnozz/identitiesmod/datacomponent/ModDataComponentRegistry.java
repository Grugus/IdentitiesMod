package com.schnozz.identitiesmod.datacomponent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.schnozz.identitiesmod.IdentitiesMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.UUID;

public class ModDataComponentRegistry {

    public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, IdentitiesMod.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ChargeRecord>> CHARGE = REGISTRAR.registerComponentType(
            "charge",
            builder -> builder
                    // The codec to read/write the data to disk
                    .persistent(ChargeRecord.CODEC)
                    // The codec to read/write the data across the network
                    .networkSynchronized(ByteBufCodecs.fromCodec(ChargeRecord.CODEC))
                    
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CompoundTagListRecord>> HELD_LIST = REGISTRAR.registerComponentType(
            "held_list",
            builder -> builder
                    // The codec to read/write the data to disk
                    .persistent(CompoundTagListRecord.CODEC)
                    // The codec to read/write the data across the network
                    .networkSynchronized(ByteBufCodecs.fromCodec(CompoundTagListRecord.CODEC))

    );





}

package com.schnozz.identitiesmod.networking.payloads;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.attachments.CombatLoggedAttachment;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record CombatLoggedAttachmentSyncPayload(CombatLoggedAttachment attachment) implements CustomPacketPayload {
    public static final Type<CombatLoggedAttachmentSyncPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "combat_logged_attachment_sync"));

    public static final StreamCodec<ByteBuf, CombatLoggedAttachmentSyncPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(CombatLoggedAttachment.CODEC),
            CombatLoggedAttachmentSyncPayload::attachment,
            CombatLoggedAttachmentSyncPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
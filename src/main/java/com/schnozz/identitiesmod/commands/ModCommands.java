package com.schnozz.identitiesmod.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.schnozz.identitiesmod.leveldata.FarmValueSavedData;
import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import com.schnozz.identitiesmod.networking.payloads.PotionLevelPayload;
import com.schnozz.identitiesmod.networking.payloads.sync_payloads.PowerSyncPayload;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = "identitiesmod", bus = EventBusSubscriber.Bus.GAME)
public class ModCommands {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("setpower")
                .requires(source -> source.hasPermission(2)) // OP level 2
                .then(Commands.argument("target", EntityArgument.player())
                        .then(Commands.argument("power", StringArgumentType.greedyString())
                                .executes(context -> {
                                    ServerPlayer target = EntityArgument.getPlayer(context, "target");
                                    String power = StringArgumentType.getString(context, "power");

                                    target.setData(ModDataAttachments.POWER_TYPE, power);
                                    PacketDistributor.sendToPlayer(target, new PowerSyncPayload(power));
                                    System.out.println("Command was successfully executed");

                                    if(power.equals("Viltrumite"))
                                    {
                                        target.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, MobEffectInstance.INFINITE_DURATION, 0, false, true,true));
                                        PacketDistributor.sendToPlayer(target,new PotionLevelPayload(MobEffects.DAMAGE_RESISTANCE,0,MobEffectInstance.INFINITE_DURATION));
                                    }
                                    return Command.SINGLE_SUCCESS;
                                }))));


        dispatcher.register(Commands.literal("getpower")
                .requires(source -> source.hasPermission(2)) // OP level 2
                .then(Commands.argument("target", EntityArgument.player())
                                .executes(context -> {
                                    ServerPlayer target = EntityArgument.getPlayer(context, "target");

                                    target.sendSystemMessage(Component.literal(target.getData(ModDataAttachments.POWER_TYPE)));
                                    PacketDistributor.sendToPlayer(target, new PowerSyncPayload(target.getData(ModDataAttachments.POWER_TYPE)));

                                    return Command.SINGLE_SUCCESS;
                                })));

        dispatcher.register(Commands.literal("getKyleWorth")
                .requires(source -> source.hasPermission(2)) // OP level 2
                .then(Commands.argument("target", EntityArgument.player())
                        .executes(context -> {
                            ServerPlayer target = EntityArgument.getPlayer(context, "target");

                            if(!target.level().isClientSide && target.getData(ModDataAttachments.POWER_TYPE).equals("Kyle"))
                            {
                                FarmValueSavedData data = FarmValueSavedData.get(target.getServer());
                                target.sendSystemMessage(Component.literal("Balance: " + data.getValue()));
                            }

                            return Command.SINGLE_SUCCESS;
                        })));
        dispatcher.register(Commands.literal("getAdaptationMap")
                .requires(source -> source.hasPermission(2)) // OP level 2
                .then(Commands.argument("target", EntityArgument.player())
                        .executes(context -> {
                            ServerPlayer target = EntityArgument.getPlayer(context, "target");

                            if(!target.level().isClientSide && target.getData(ModDataAttachments.POWER_TYPE).equals("Adaptation"))
                            {
                                target.sendSystemMessage(Component.literal("Map: " + target.getData(ModDataAttachments.ADAPTION).getAllAdaptationValues()));
                            }

                            return Command.SINGLE_SUCCESS;
                        })));
    }
}

package com.schnozz.identitiesmod.events.necromancer;


import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.items.ItemRegistry;
import com.schnozz.identitiesmod.items.MobHolder;
import com.schnozz.identitiesmod.leveldata.UUIDSavedData;
import com.schnozz.identitiesmod.register_attachments.ModDataAttachments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ServerNecroEvents {



    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event)
    {
        if(event.getEntity().level().isClientSide) {return;}
        MinecraftServer server = event.getEntity().getServer();
        UUIDSavedData command_list = UUIDSavedData.get(server);



        if(event.getEntity().level() instanceof ServerLevel level && event.getEntity() instanceof Monster monster && event.getSource().getDirectEntity() instanceof Player p && p.getData(ModDataAttachments.POWER_TYPE).equals("Necromancer") && command_list.getUUIDList().size() < 10 && !command_list.getUUIDList().contains(monster.getUUID()))
        {
            CompoundTag oldData = new CompoundTag();
            monster.save(oldData);
            oldData.remove("UUID");
            oldData.remove("Health");

            Entity entity = monster.getType().create(level);
            if(entity instanceof Monster newMonster)
            {
                newMonster.load(oldData);
                newMonster.setPos(monster.position());
                newMonster.setXRot(monster.getXRot());
                newMonster.setYRot(monster.getYRot());
                newMonster.goalSelector.removeAllGoals(wrappedGoal ->
                        wrappedGoal instanceof RandomStrollGoal
                );
                newMonster.targetSelector.removeAllGoals(goal -> true);
                newMonster.setTarget(null);
                //set new data here for entity - not chat skibidi

                command_list.addUUID(newMonster.getUUID());
                level.addFreshEntity(newMonster);
            }


        }

        if(event.getEntity() instanceof Monster monster && command_list.getUUIDList().contains(monster.getUUID()))
        {
            command_list.removeUUID(monster.getUUID());
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingIncomingDamageEvent event)
    {
        if(event.getEntity().level().isClientSide) {return;}
        MinecraftServer server = event.getEntity().getServer();
        UUIDSavedData command_list = UUIDSavedData.get(server);

        if(((event.getEntity() instanceof Player p && p.getData(ModDataAttachments.POWER_TYPE).equals("Necromancer")) || (event.getEntity() instanceof Monster m && command_list.getUUIDList().contains(m.getUUID()))) && event.getSource().getDirectEntity() instanceof Monster monster && command_list.getUUIDList().contains(monster.getUUID()) )
        {
            event.setCanceled(true);
        }

        if((event.getEntity() instanceof Monster monster && command_list.getUUIDList().contains(monster.getUUID())) && ((event.getSource().getDirectEntity() instanceof Player p && p.getData(ModDataAttachments.POWER_TYPE).equals("Necromancer")) || (event.getSource().getDirectEntity() instanceof Monster m && command_list.getUUIDList().contains(m.getUUID()))))
        {
            event.setCanceled(true);
        }

        if(event.getSource().getDirectEntity() instanceof LivingEntity entity && (event.getEntity() instanceof Monster monster && command_list.getUUIDList().contains(monster.getUUID())) && !((event.getSource().getDirectEntity() instanceof Player p && p.getData(ModDataAttachments.POWER_TYPE).equals("Necromancer")) || (event.getSource().getDirectEntity() instanceof Monster m && command_list.getUUIDList().contains(m.getUUID()))))
        {
            monster.setTarget(entity);
        }
    }




    @SubscribeEvent
    public static void onSetAttackTarget(LivingChangeTargetEvent event)
    {
        if(event.getEntity().level().isClientSide) {return;}
        MinecraftServer server = event.getEntity().getServer();
        UUIDSavedData command_list = UUIDSavedData.get(server);

        if(((event.getNewAboutToBeSetTarget() instanceof Player p && p.getData(ModDataAttachments.POWER_TYPE).equals("Necromancer")) || (event.getNewAboutToBeSetTarget() instanceof Monster m && command_list.getUUIDList().contains(m.getUUID()))) && event.getEntity() instanceof Monster monster && command_list.getUUIDList().contains(monster.getUUID()) )
        {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if(event.getEntity().level().isClientSide) return;
        // Get the item stack that was crafted
        ItemStack craftedItem = event.getCrafting();
        // Check if the crafted item is a MobHolder
        if (craftedItem.getItem() == ItemRegistry.MOB_HOLDER.get() && craftedItem.getItem() instanceof MobHolder mobHolder) {

            // Get the crafting matrix (ingredients)
            Container container = event.getInventory();

            // Check if Ender Pearl is in the crafting matrix
            boolean hasEnderPearl = false;

            for (int i = 0; i < container.getContainerSize(); i++) {
                ItemStack stack = container.getItem(i);
                if (stack.getItem() == Items.ENDER_PEARL) {
                    hasEnderPearl = true;
                    break;
                }
            }

            // If the recipe included an Ender Pearl, increase the charge value
            if (hasEnderPearl) {
                mobHolder.addCharge();
            }
        }
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event)
    {
        if(event.getEntity().level().isClientSide) {return;}
        MinecraftServer server = event.getEntity().getServer();
        UUIDSavedData command_list = UUIDSavedData.get(server);

        if(event.getEntity() instanceof Monster monster &&  command_list.getUUIDList().contains(monster.getUUID()))
        {
            monster.goalSelector.removeAllGoals(wrappedGoal ->
                    wrappedGoal instanceof RandomStrollGoal
            );
            monster.targetSelector.removeAllGoals(goal -> true);
            monster.setTarget(null);
            //add the follow player goal and any others depending on type
        }
    }
}

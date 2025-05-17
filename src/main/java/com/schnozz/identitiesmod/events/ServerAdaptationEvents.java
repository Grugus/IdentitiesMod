package com.schnozz.identitiesmod.events;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.register_attachments.ModDataAttachments;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ServerAdaptationEvents {
    @SubscribeEvent
    public static void onEntityDamage(LivingIncomingDamageEvent event) {
        Entity entity = event.getEntity();
        DamageSource source = event.getSource();
        if(entity.getData(ModDataAttachments.POWER_TYPE).equals("Adaptation")) {
            Player adapter = (Player) entity;
            String damageSourceString = source.getMsgId();
            damageSourceString = damageSourceString.toLowerCase();
            ResourceLocation sourceLocation;
            if(damageSourceString.equals("magic")||damageSourceString.equals("wither")||damageSourceString.equals("freeze")||damageSourceString.equals("fire")||damageSourceString.equals("inFire")||damageSourceString.equals("lava"))
            {
                damageOverTimeSource(adapter,damageSourceString,event);
                if(damageOverTimeSource(adapter, damageSourceString, event))
                {
                    event.setCanceled(true);
                }
            }
            else
            {
                sourceLocation = changeAdaptationValues(adapter,damageSourceString);
                if(damageCorrect(adapter, sourceLocation, event))
                {
                    event.setCanceled(true);
                }
            }
            increaseAdaptValue(adapter,damageSourceString);
        }
        else if(event.getSource().getDirectEntity() != null)
        {
            Player adapter = (Player) event.getSource().getDirectEntity();
            if(adapter.getData(ModDataAttachments.POWER_TYPE).equals("Adaptation"))
            {
                //kys
            }
        }

    }
    private static ResourceLocation changeAdaptationValues(Player adapter, String sourceString)
    {
        ResourceLocation sourceLocation = ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID,sourceString);
        float currentValue = adapter.getData(ModDataAttachments.ADAPTION).getAdaptationValue(sourceLocation);

        System.out.println("sourceLocation" + sourceLocation);
        System.out.println("currentValue" + currentValue);

        if(currentValue >= 4.00F)
        {
            return sourceLocation;
        }

        return sourceLocation;
    }
    private static boolean damageCorrect(Player adapter, ResourceLocation sourceLocation, LivingIncomingDamageEvent event)
    {
        float damageDecrease = adapter.getData(ModDataAttachments.ADAPTION).getAdaptationValue(sourceLocation);
        float amount = event.getAmount()-damageDecrease;
        if(amount < 0)
        {
            return true;
        }
        event.setAmount(amount);
        return false;
    }
    private static boolean checkAmount(Player adapter, String sourceString, LivingIncomingDamageEvent event)
    {
        ResourceLocation sourceLocation = ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID,sourceString);
        float damageDecrease = adapter.getData(ModDataAttachments.ADAPTION).getAdaptationValue(sourceLocation);
        float amount = event.getAmount()-damageDecrease;
        return amount <= 0.00F;
    }
    private static void increaseAdaptValue(Player adapter, String sourceString)
    {
        ResourceLocation sourceLocation = ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID,sourceString);
        float adaptDegree = adapter.getData(ModDataAttachments.ADAPTION).adaptationDegree;
        float currentValue = adapter.getData(ModDataAttachments.ADAPTION).getAdaptationValue(sourceLocation);
        float newValue = adaptDegree+currentValue;
        adapter.getData(ModDataAttachments.ADAPTION).setAdaptationValue(sourceLocation,newValue);
    }
    private static boolean damageOverTimeSource(Player adapter, String sourceString, LivingIncomingDamageEvent event)
    {
        ResourceLocation tempLocation;

        tempLocation = changeAdaptationValues(adapter,"magic");
        damageCorrect(adapter, tempLocation, event);

        tempLocation = changeAdaptationValues(adapter,"wither");
        damageCorrect(adapter, tempLocation, event);

        tempLocation = changeAdaptationValues(adapter,"freeze");
        damageCorrect(adapter, tempLocation, event);

        tempLocation = changeAdaptationValues(adapter,"onfire");
        damageCorrect(adapter, tempLocation, event);

        tempLocation = changeAdaptationValues(adapter,"infire");
        damageCorrect(adapter, tempLocation, event);

        tempLocation = changeAdaptationValues(adapter,"lava");
        damageCorrect(adapter, tempLocation, event);

        //add other ones like drown and cactus

        return checkAmount(adapter,sourceString,event);
    }

}

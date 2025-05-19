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
    private static float heatCap = 0.00F;
    private static float dotCap = 0.20F;
    private static float defaultCap = 0.40F;
    private static float unadaptCap = 2.00F;

    @SubscribeEvent
    public static void onEntityDamage(LivingIncomingDamageEvent event) {
        Entity entity = event.getEntity();
        DamageSource source = event.getSource();
        if(entity.getData(ModDataAttachments.POWER_TYPE).equals("Adaptation")) {
            Player adapter = (Player) entity;
            String damageSourceString = source.getMsgId();
            damageSourceString = damageSourceString.toLowerCase();

            boolean isDot = false;
            String[] heatIds = adapter.getData(ModDataAttachments.ADAPTION).heatSourceMessageIDs;
            for(String id: heatIds)
            {
                if(damageSourceString.equals(id))
                {
                    if(groupSources(adapter, damageSourceString, event,heatIds))
                    {
                        event.setCanceled(true);
                    }
                    decreaseAdaptValue(adapter,damageSourceString,heatCap,0.005F);
                    isDot = true;
                }
            }
            if(damageSourceString.equals("lava"))
            {
                if(zeroDamage(adapter,damageSourceString,event))
                {
                    event.setCanceled(true);
                }
                decreaseAdaptValue(adapter,damageSourceString,heatCap,0.01F);
                isDot = true;
            }
            if(!isDot)
            {
                decreaseAdaptValue(adapter,damageSourceString,defaultCap);
            }
        }
        else if(event.getSource().getDirectEntity() != null)
        {

            if(event.getSource().getDirectEntity().getData(ModDataAttachments.POWER_TYPE).equals("Adaptation")) //if adapter is attacking
            {
                Player adapter = (Player) event.getSource().getDirectEntity();
                //kys
            }
        }
    }
    //decrease or increase damage taken by adapter based on the adaptation value
    private static void damageCorrect(Player adapter, ResourceLocation sourceLocation, LivingIncomingDamageEvent event)
    {
        float damagePercent = adapter.getData(ModDataAttachments.ADAPTION).getAdaptationValue(sourceLocation);
        float amount = event.getAmount()*damagePercent;
        event.setAmount(amount);
    }
    //return true if damage taken by adapter will be zero after adaptation value is applied
    private static boolean zeroDamage(Player adapter, String sourceString, LivingIncomingDamageEvent event)
    {
        ResourceLocation sourceLocation = ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID,sourceString);
        float damageChange = adapter.getData(ModDataAttachments.ADAPTION).getAdaptationValue(sourceLocation);
        return damageChange == 0.00F;
    }
    //increments adaptation value of a source by adaptation degree on damage taken
    private static void decreaseAdaptValue(Player adapter, String sourceString, float cap)
    {
        ResourceLocation sourceLocation = ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID,sourceString);
        float adaptDegree = adapter.getData(ModDataAttachments.ADAPTION).adaptationDegree;
        float newAdaptationValue = adapter.getData(ModDataAttachments.ADAPTION).getAdaptationValue(sourceLocation);
        if((newAdaptationValue-adaptDegree)>=cap)
        {
            newAdaptationValue -= adaptDegree;
            increaseAdaptationValue(adapter,sourceString,unadaptCap);
        }
        else {
            newAdaptationValue = cap;
        }
        adapter.getData(ModDataAttachments.ADAPTION).setAdaptationValue(sourceLocation,newAdaptationValue );
    }
    //override of changeAdaptValue with a different adaptation degree
    private static void decreaseAdaptValue(Player adapter, String sourceString, float cap, float customAdaptDegree)
    {
        ResourceLocation sourceLocation = ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID,sourceString);
        float newAdaptationValue = adapter.getData(ModDataAttachments.ADAPTION).getAdaptationValue(sourceLocation);
        if((newAdaptationValue-customAdaptDegree)>=cap)
        {
            newAdaptationValue -= customAdaptDegree;
        }
        else {
            newAdaptationValue = cap;
        }
        adapter.getData(ModDataAttachments.ADAPTION).setAdaptationValue(sourceLocation,newAdaptationValue );
    }
    //when adaptation value is decreased for one source, it is increased for another
    private static void increaseAdaptationValue(Player adapter, String sourceString, float cap)
    {
        ResourceLocation sourceLocation = ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID,sourceString);
        String[][] importantGroups = adapter.getData(ModDataAttachments.ADAPTION).importantSourceMessageIdGroups;
        String[] idGroup = {};
        int random;
        boolean validGroup = false;
        while(!validGroup)
        {
            random = (int) (Math.random()*importantGroups.length);
            validGroup = true;
            idGroup = importantGroups[random];
            for(String id:idGroup)
            {
                if(id.equals(sourceString))
                {
                    validGroup = false;
                }
            }
        }
        groupSourcesUnadapt(adapter,idGroup,cap);
    }
    //pre:damage is taken from a damage over source
    //post:increments adaptation value by adaptation degree for every source in group
    private static boolean groupSources(Player adapter, String sourceString, LivingIncomingDamageEvent event,String[] ids)
    {
        ResourceLocation tempLocation;
        for(String id: ids)
        {
            tempLocation = ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID,id);
            damageCorrect(adapter, tempLocation, event);
            decreaseAdaptValue(adapter,id,dotCap);
        }
        return zeroDamage(adapter,sourceString,event);
    }
    //pre:damage is taken from a damage over source
    //post:deincrements adaptation value
    private static void groupSourcesUnadapt(Player adapter, String[] ids, float cap)
    {
        ResourceLocation tempLocation;
        for(String id: ids)
        {
            System.out.println("unadapt source:" + id);
            tempLocation = ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID,id);
            float newAdaptationValue = adapter.getData(ModDataAttachments.ADAPTION).getAdaptationValue(tempLocation);
            float adaptDegree = adapter.getData(ModDataAttachments.ADAPTION).adaptationDegree/5;
            if((newAdaptationValue+adaptDegree)<=cap)
            {
                newAdaptationValue += adaptDegree;
            }
            else {
                newAdaptationValue = cap;
            }
            adapter.getData(ModDataAttachments.ADAPTION).setAdaptationValue(tempLocation,newAdaptationValue);
            System.out.println("newAdaptationValue:"+newAdaptationValue);
        }
    }
}

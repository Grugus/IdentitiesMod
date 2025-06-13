package com.schnozz.identitiesmod.events.adaptation;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ServerAdaptationEvents {
    private static final float NO_CAP = 0.00F;
    private static final float EXPLOSION_CAP = 0.05F;
    private static final float MOB_CAP = 0.25F;
    private static final float DEFAULT_CAP = 0.20F;
    private static final float UNADAPT_CAP = 2.00F;

    private static final float HEAT_ADAPT_DEGREE = 0.01F;
    private static final float EXPLOSION_ADAPT_DEGREE = 0.13F;

    @SubscribeEvent
    public static void onEntityDamage(LivingIncomingDamageEvent event) {
        Entity entity = event.getEntity();
        DamageSource source = event.getSource();

        String damageSourceString = source.getMsgId();
        damageSourceString = damageSourceString.toLowerCase();
        ResourceLocation sourceLocation = ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID,damageSourceString);
        if(entity.getData(ModDataAttachments.POWER_TYPE).equals("Adaptation")) {
            Player adapter = (Player) entity;
            boolean alreadyAdapted = false;
            String[] heatIds = adapter.getData(ModDataAttachments.ADAPTION).heatSourceMessageIds;

            if(zeroDamage(adapter,damageSourceString,event)){event.setCanceled(true);}

            for(String id: heatIds)
            {
                if(damageSourceString.equals(id))
                {
                    damageCorrect(adapter,sourceLocation,event);
                    groupSourcesAdapt(adapter, heatIds, NO_CAP, HEAT_ADAPT_DEGREE);
                    alreadyAdapted = true;
                }
            }
            if(damageSourceString.equals("lava"))
            {
                damageCorrect(adapter,sourceLocation,event);
                decreaseAdaptValue(adapter,damageSourceString, NO_CAP, HEAT_ADAPT_DEGREE);
                alreadyAdapted = true;
            }
            if(damageSourceString.equals("drown"))
            {
                damageCorrect(adapter,sourceLocation,event);
                decreaseAdaptValue(adapter,damageSourceString, NO_CAP);
                alreadyAdapted = true;
            }
            if(damageSourceString.equals("explosion"))
            {
                damageCorrect(adapter,sourceLocation,event);
                decreaseAdaptValue(adapter,damageSourceString, EXPLOSION_CAP, EXPLOSION_ADAPT_DEGREE);
                alreadyAdapted = true;
            }
            if(damageSourceString.equals("mob"))
            {
                damageCorrect(adapter,sourceLocation,event);
                decreaseAdaptValue(adapter,damageSourceString, MOB_CAP);
                alreadyAdapted = true;
            }
            if(!alreadyAdapted) //default adaptation to singular source with default cap
            {
                damageCorrect(adapter,sourceLocation,event);
                decreaseAdaptValue(adapter,damageSourceString, DEFAULT_CAP);
            }
        }
        else if(event.getSource().getEntity() != null)
        {
            if(event.getSource().getEntity().getData(ModDataAttachments.POWER_TYPE).equals("Adaptation")) //if adapter is attacking
            {
                Player adapter = (Player) event.getSource().getEntity();
                damageCorrectAttack(adapter,sourceLocation,event);
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
    //decrease or increase damage taken by adapter's target based on the adaptation value
    private static void damageCorrectAttack(Player adapter, ResourceLocation sourceLocation, LivingIncomingDamageEvent event)
    {
        if(adapter.getData(ModDataAttachments.ADAPTION).getAdaptationValue(ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID,"offensive")) == 0)
        {
            float damagePercent = adapter.getData(ModDataAttachments.ADAPTION).getAdaptationValue(sourceLocation);
            float amount = event.getAmount()*damagePercent*3.5F;
            System.out.println("OLD AMOUNT:" + event.getAmount());
            System.out.println("NEW AMOUNT:" + amount);
            System.out.println("SOURCE:" + sourceLocation);
            event.setAmount(amount);
        }
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
            increaseAdaptationValue(adapter,sourceString, UNADAPT_CAP);
        }
        else {
            newAdaptationValue = cap;
        }

        adapter.getData(ModDataAttachments.ADAPTION).setAdaptationValue(sourceLocation,newAdaptationValue);
        //PacketDistributor.sendToPlayer(new AdaptationSyncPayload());
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
    private static void groupSourcesAdapt(Player adapter, String[] ids, float cap, float customAdaptDegree)
    {
        for(String id: ids)
        {
            decreaseAdaptValue(adapter,id,cap,customAdaptDegree);
        }
    }
    //pre:damage is taken from a damage over source
    //post:deincrements adaptation value
    private static void groupSourcesUnadapt(Player adapter, String[] ids, float cap)
    {
        ResourceLocation tempLocation;
        for(String id: ids)
        {
            tempLocation = ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID,id);
            float newAdaptationValue = adapter.getData(ModDataAttachments.ADAPTION).getAdaptationValue(tempLocation);
            float adaptDegree = adapter.getData(ModDataAttachments.ADAPTION).adaptationDegree/4;
            if((newAdaptationValue+adaptDegree)<=cap)
            {
                newAdaptationValue += adaptDegree;
            }
            else {
                newAdaptationValue = cap;
            }
            adapter.getData(ModDataAttachments.ADAPTION).setAdaptationValue(tempLocation,newAdaptationValue);
        }
    }
}

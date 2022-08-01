package com.hotslicerrpg.rpg.Entities;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import java.util.List;

public class MinesStalker extends EntityPigZombie {
    public MinesStalker(World world) {
        super(((CraftWorld) world).getHandle());
        List goalB = (List) NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
        List goalC = (List) NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
        List targetB = (List) NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
        List targetC = (List) NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();

        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.1);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(4);

        this.setCustomName("CUSTOM_MOB");
        this.setCustomNameVisible(true);
        this.fireProof = false;
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityPlayer.class, 1.0D, false));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityPlayer.class, false, true));
    }
}

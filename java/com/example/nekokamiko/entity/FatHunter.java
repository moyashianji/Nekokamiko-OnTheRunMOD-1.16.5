package com.example.nekokamiko.entity;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = "nekokamiko", bus = Mod.EventBusSubscriber.Bus.MOD)

public class FatHunter extends MonsterEntity {
    private int attackTimer;

    public FatHunter(EntityType<? extends FatHunter> type, World worldIn) {
        super(type, worldIn);
        this.noCulling = true;
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 64)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.ATTACK_DAMAGE, 14)
                .add(Attributes.ATTACK_SPEED, 6)
                .add(Attributes.MAX_HEALTH, 1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 1.0F));
    }

    public void tick() {
        super.tick();
        PlayerEntity target = this.level.getNearestPlayer(this, 30.0);
        if (target != null) {
            if (this.canSee(target)) {
                // 視界にプレイヤーがいる場合は追跡する
                this.getNavigation().moveTo(target, 1.0);
                if (this.attackTimer <= 0 && this.distanceTo(target) < 3.0) {
                    // プレイヤーに攻撃する
                    this.attackTimer = 20;
                    this.swing(Hand.MAIN_HAND);
                    this.doHurtTarget(target);
                }
            } else {
                // 見えなくなった場合は追跡をやめる
                if (!this.getNavigation().isInProgress()) {
                    double targetX = this.getX() + this.getRandom().nextDouble() * 10 - 5;
                    double targetZ = this.getZ() + this.getRandom().nextDouble() * 10 - 5;
                    this.getNavigation().moveTo(targetX, this.getY(), targetZ, 0.3);
                }
            }
        } else {
            // プレイヤーがいない場合はランダムな位置に向かって歩行する
            if (!this.getNavigation().isInProgress()) {
                double targetX = this.getX() + this.getRandom().nextDouble() * 10 - 5;
                double targetZ = this.getZ() + this.getRandom().nextDouble() * 10 - 5;
                this.getNavigation().moveTo(targetX, this.getY(), targetZ, 1.0);
            }
        }
        if (this.attackTimer > 0) {
            this.attackTimer--;
        }
    }
    // プレイヤーを攻撃する
    private void doHurtTarget(PlayerEntity player) {
        player.hurt(DamageSource.mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
    }


    @Override
    public void handleEntityEvent(byte event) {
        if (event == 4) {
            this.attackTimer = 10;
        } else {
            super.handleEntityEvent(event);
        }
    }

    public int getAttackTimer() {
        return this.attackTimer;
    }



}
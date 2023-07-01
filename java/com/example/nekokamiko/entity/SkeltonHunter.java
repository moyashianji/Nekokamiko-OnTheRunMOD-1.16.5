package com.example.nekokamiko.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SkeltonHunter extends MonsterEntity implements IRangedAttackMob {
    private int attackCooldown;

    public SkeltonHunter(EntityType<? extends SkeltonHunter> type, World worldIn) {
        super(type, worldIn);
        this.registerGoals();
        this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.BOW)); // スケルトンハンターに弓を持たせる
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new RangedBowAttackGoal(this, 1.0D, 20, 15.0F));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.ATTACK_SPEED, 6.0D);
    }

    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        ItemStack bowStack = this.getItemInHand(Hand.MAIN_HAND);
        if (bowStack.getItem() instanceof BowItem) {
            ItemStack arrowStack = new ItemStack(Items.ARROW); // 矢のItemStackを作成する
            double d0 = target.getX() - this.getX();
            double d1 = target.getY(0.3333333333333333D) - this.getY(0.3333333333333333D);
            double d2 = target.getZ() - this.getZ();
            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
            double velocityMultiplier = 1.6F;
            ArrowEntity arrowEntity = new ArrowEntity(this.level, this.getX(), this.getY(), this.getZ());
            arrowEntity.shoot(d0, d1 + d3 * 0.2D, d2, (float) velocityMultiplier, 0);
            arrowEntity.setBaseDamage(4.0D + this.getRandom().nextGaussian() * 0.25D + distanceFactor * 0.11F);
            arrowEntity.setKnockback(2);
            arrowEntity.pickup = AbstractArrowEntity.PickupStatus.DISALLOWED;
            this.level.addFreshEntity(arrowEntity);
            this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        }
    }

    public int getAttackCooldown() {
        return this.attackCooldown;
    }

    public void setAttackCooldown(int cooldown) {
        this.attackCooldown = cooldown;
    }

    public void tick() {
        super.tick();
        if (this.attackCooldown > 0) {
            this.attackCooldown--;
        }
    }

    @Override
    public void handleEntityEvent(byte event) {
        if (event == 4) {
            this.attackCooldown = 20;
        } else {
            super.handleEntityEvent(event);
        }
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SKELETON_STEP, 0.15F, 1.0F);
    }
}
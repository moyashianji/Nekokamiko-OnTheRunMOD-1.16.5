package com.Moyashi.nekokamiko.entity;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "nekokamiko", bus = Mod.EventBusSubscriber.Bus.MOD)
public class PhantomHunter extends FlyingEntity implements IMob {
    private static final DataParameter<Integer> ID_SIZE = EntityDataManager.defineId(com.Moyashi.nekokamiko.entity.PhantomHunter.class, DataSerializers.INT);
    private Vector3d moveTargetPoint = Vector3d.ZERO;
    private BlockPos anchorPoint = BlockPos.ZERO;
    private com.Moyashi.nekokamiko.entity.PhantomHunter.AttackPhase attackPhase = com.Moyashi.nekokamiko.entity.PhantomHunter.AttackPhase.CIRCLE;

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 64)
                .add(Attributes.MOVEMENT_SPEED, 0.5F)
                .add(Attributes.ATTACK_DAMAGE, 10)
                .add(Attributes.ATTACK_SPEED, 6);
    }

    public PhantomHunter(EntityType<? extends com.Moyashi.nekokamiko.entity.PhantomHunter> p_i50200_1_, World p_i50200_2_) {
        super(p_i50200_1_, p_i50200_2_);
        this.xpReward = 5;
        this.moveControl = new com.Moyashi.nekokamiko.entity.PhantomHunter.MoveHelperController(this);
        this.lookControl = new com.Moyashi.nekokamiko.entity.PhantomHunter.LookHelperController(this);
    }

    protected BodyController createBodyControl() {
        return new com.Moyashi.nekokamiko.entity.PhantomHunter.BodyHelperController(this);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new com.Moyashi.nekokamiko.entity.PhantomHunter.PickAttackGoal());
        this.goalSelector.addGoal(2, new com.Moyashi.nekokamiko.entity.PhantomHunter.SweepAttackGoal());
        this.goalSelector.addGoal(3, new com.Moyashi.nekokamiko.entity.PhantomHunter.OrbitPointGoal());
        this.targetSelector.addGoal(1, new com.Moyashi.nekokamiko.entity.PhantomHunter.AttackPlayerGoal());
    }


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_SIZE, 0);
    }

    public void setPhantomSize(int p_203034_1_) {
        this.entityData.set(ID_SIZE, MathHelper.clamp(p_203034_1_, 0, 64));
    }

    private void updatePhantomSizeInfo() {
        this.refreshDimensions();
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((double)(6 + this.getPhantomSize()));
    }

    public int getPhantomSize() {
        return this.entityData.get(ID_SIZE);
    }

    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return p_213348_2_.height * 0.35F;
    }

    public void onSyncedDataUpdated(DataParameter<?> p_184206_1_) {
        if (ID_SIZE.equals(p_184206_1_)) {
            this.updatePhantomSizeInfo();
        }

        super.onSyncedDataUpdated(p_184206_1_);
    }
    private int attackTimer;

    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    public void tick() {
            super.tick();
            PlayerEntity target = this.level.getNearestPlayer(this, 30.0);
            if (target != null) {
                if (this.canSee(target)) {
                    // 視界にプレイヤーがいる場合は追跡する
                    this.getNavigation().moveTo(target, 1.0);
                    if (this.attackTimer <= 0 && this.distanceTo(target) < 1.0) {
                        // プレイヤーに攻撃する
                        this.attackTimer = 20;
                        this.swing(Hand.MAIN_HAND);
                        this.doHurtTarget(target);
                    }
                }
            }
        }

    private void doHurtTarget(PlayerEntity player) {
        player.hurt(DamageSource.mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
    }




    protected void customServerAiStep() {
        super.customServerAiStep();
    }

    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
        this.anchorPoint = this.blockPosition().above(5);
        this.setPhantomSize(0);
        return super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
    }

    public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
        super.readAdditionalSaveData(p_70037_1_);
        if (p_70037_1_.contains("AX")) {
            this.anchorPoint = new BlockPos(p_70037_1_.getInt("AX"), p_70037_1_.getInt("AY"), p_70037_1_.getInt("AZ"));
        }

        this.setPhantomSize(p_70037_1_.getInt("Size"));
    }

    public void addAdditionalSaveData(CompoundNBT p_213281_1_) {
        super.addAdditionalSaveData(p_213281_1_);
        p_213281_1_.putInt("AX", this.anchorPoint.getX());
        p_213281_1_.putInt("AY", this.anchorPoint.getY());
        p_213281_1_.putInt("AZ", this.anchorPoint.getZ());
        p_213281_1_.putInt("Size", this.getPhantomSize());
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldRenderAtSqrDistance(double p_70112_1_) {
        return true;
    }

    public SoundCategory getSoundSource() {
        return SoundCategory.HOSTILE;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.PHANTOM_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.PHANTOM_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.PHANTOM_DEATH;
    }

    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEAD;
    }

    protected float getSoundVolume() {
        return 1.0F;
    }

    public boolean canAttackType(EntityType<?> p_213358_1_) {
        return true;
    }

    public EntitySize getDimensions(Pose p_213305_1_) {
        int i = this.getPhantomSize();
        EntitySize entitysize = super.getDimensions(p_213305_1_);
        float f = (entitysize.width + 0.2F * (float)i) / entitysize.width;
        return entitysize.scale(f);
    }

    static enum AttackPhase {
        CIRCLE,
        SWOOP;
    }

    class AttackPlayerGoal extends Goal {
        private final EntityPredicate attackTargeting = (new EntityPredicate()).range(100.0D);
        private int nextScanTick = 10;

        private AttackPlayerGoal() {
        }

        public boolean canUse() {
            if (this.nextScanTick > 0) {
                --this.nextScanTick;
                return false;
            } else {
                this.nextScanTick = 10;
                List<PlayerEntity> list = com.Moyashi.nekokamiko.entity.PhantomHunter.this.level.getNearbyPlayers(this.attackTargeting, com.Moyashi.nekokamiko.entity.PhantomHunter.this, com.Moyashi.nekokamiko.entity.PhantomHunter.this.getBoundingBox().inflate(16.0D, 64.0D, 16.0D));
                if (!list.isEmpty()) {
                    list.sort(Comparator.<Entity, Double>comparing(Entity::getY).reversed());

                    for(PlayerEntity playerentity : list) {
                        if (com.Moyashi.nekokamiko.entity.PhantomHunter.this.canAttack(playerentity, EntityPredicate.DEFAULT)) {
                            com.Moyashi.nekokamiko.entity.PhantomHunter.this.setTarget(playerentity);
                            return true;
                        }
                    }
                }

                return false;
            }
        }

        public boolean canContinueToUse() {
            LivingEntity livingentity = com.Moyashi.nekokamiko.entity.PhantomHunter.this.getTarget();
            return livingentity != null ? com.Moyashi.nekokamiko.entity.PhantomHunter.this.canAttack(livingentity, EntityPredicate.DEFAULT) : false;
        }
    }

    class BodyHelperController extends BodyController {
        public BodyHelperController(MobEntity p_i49925_2_) {
            super(p_i49925_2_);
        }

        public void clientTick() {
            com.Moyashi.nekokamiko.entity.PhantomHunter.this.yHeadRot = com.Moyashi.nekokamiko.entity.PhantomHunter.this.yBodyRot;
            com.Moyashi.nekokamiko.entity.PhantomHunter.this.yBodyRot = com.Moyashi.nekokamiko.entity.PhantomHunter.this.yRot;
        }
    }

    class LookHelperController extends LookController {
        public LookHelperController(MobEntity p_i48802_2_) {
            super(p_i48802_2_);
        }

        public void tick() {
        }
    }

    abstract class MoveGoal extends Goal {
        public MoveGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        protected boolean touchingTarget() {
            return com.Moyashi.nekokamiko.entity.PhantomHunter.this.moveTargetPoint.distanceToSqr(com.Moyashi.nekokamiko.entity.PhantomHunter.this.getX(), com.Moyashi.nekokamiko.entity.PhantomHunter.this.getY(), com.Moyashi.nekokamiko.entity.PhantomHunter.this.getZ()) < 4.0D;
        }
    }

    class MoveHelperController extends MovementController {
        private float speed = 0.3F;

        public MoveHelperController(MobEntity p_i48801_2_) {
            super(p_i48801_2_);
        }

        public void tick() {
            if (com.Moyashi.nekokamiko.entity.PhantomHunter.this.horizontalCollision) {
                com.Moyashi.nekokamiko.entity.PhantomHunter.this.yRot += 180.0F;
                this.speed = 0.4F;
            }

            float f = (float)(com.Moyashi.nekokamiko.entity.PhantomHunter.this.moveTargetPoint.x - com.Moyashi.nekokamiko.entity.PhantomHunter.this.getX());
            float f1 = (float)(com.Moyashi.nekokamiko.entity.PhantomHunter.this.moveTargetPoint.y - com.Moyashi.nekokamiko.entity.PhantomHunter.this.getY());
            float f2 = (float)(com.Moyashi.nekokamiko.entity.PhantomHunter.this.moveTargetPoint.z - com.Moyashi.nekokamiko.entity.PhantomHunter.this.getZ());
            double d0 = (double)MathHelper.sqrt(f * f + f2 * f2);
            double d1 = 1.0D - (double)MathHelper.abs(f1 * 0.7F) / d0;
            f = (float)((double)f * d1);
            f2 = (float)((double)f2 * d1);
            d0 = (double)MathHelper.sqrt(f * f + f2 * f2);
            double d2 = (double)MathHelper.sqrt(f * f + f2 * f2 + f1 * f1);
            float f3 = com.Moyashi.nekokamiko.entity.PhantomHunter.this.yRot;
            float f4 = (float)MathHelper.atan2((double)f2, (double)f);
            float f5 = MathHelper.wrapDegrees(com.Moyashi.nekokamiko.entity.PhantomHunter.this.yRot + 90.0F);
            float f6 = MathHelper.wrapDegrees(f4 * (180F / (float)Math.PI));
            com.Moyashi.nekokamiko.entity.PhantomHunter.this.yRot = MathHelper.approachDegrees(f5, f6, 4.0F) - 90.0F;
            com.Moyashi.nekokamiko.entity.PhantomHunter.this.yBodyRot = com.Moyashi.nekokamiko.entity.PhantomHunter.this.yRot;
            if (MathHelper.degreesDifferenceAbs(f3, com.Moyashi.nekokamiko.entity.PhantomHunter.this.yRot) < 3.0F) {
                this.speed = MathHelper.approach(this.speed, 1.8F, 0.005F * (1.8F / this.speed));
            } else {
                this.speed = MathHelper.approach(this.speed, 0.2F, 0.025F);
            }

            float f7 = (float)(-(MathHelper.atan2((double)(-f1), d0) * (double)(180F / (float)Math.PI)));
            com.Moyashi.nekokamiko.entity.PhantomHunter.this.xRot = f7;
            float f8 = com.Moyashi.nekokamiko.entity.PhantomHunter.this.yRot + 90.0F;
            double d3 = (double)(this.speed * MathHelper.cos(f8 * ((float)Math.PI / 180F))) * Math.abs((double)f / d2);
            double d4 = (double)(this.speed * MathHelper.sin(f8 * ((float)Math.PI / 180F))) * Math.abs((double)f2 / d2);
            double d5 = (double)(this.speed * MathHelper.sin(f7 * ((float)Math.PI / 180F))) * Math.abs((double)f1 / d2);
            Vector3d vector3d = com.Moyashi.nekokamiko.entity.PhantomHunter.this.getDeltaMovement();
            com.Moyashi.nekokamiko.entity.PhantomHunter.this.setDeltaMovement(vector3d.add((new Vector3d(d3, d5, d4)).subtract(vector3d).scale(0.2D)));
        }
    }

    class OrbitPointGoal extends com.Moyashi.nekokamiko.entity.PhantomHunter.MoveGoal {
        private float angle;
        private float distance;
        private float height;
        private float clockwise;

        private OrbitPointGoal() {
        }

        public boolean canUse() {
            return com.Moyashi.nekokamiko.entity.PhantomHunter.this.getTarget() == null || com.Moyashi.nekokamiko.entity.PhantomHunter.this.attackPhase == com.Moyashi.nekokamiko.entity.PhantomHunter.AttackPhase.CIRCLE;
        }

        public void start() {
            this.distance = 5.0F + com.Moyashi.nekokamiko.entity.PhantomHunter.this.random.nextFloat() * 10.0F;
            this.height = -4.0F + com.Moyashi.nekokamiko.entity.PhantomHunter.this.random.nextFloat() * 9.0F;
            this.clockwise = com.Moyashi.nekokamiko.entity.PhantomHunter.this.random.nextBoolean() ? 1.0F : -1.0F;
            this.selectNext();
        }

        public void tick() {
            if (com.Moyashi.nekokamiko.entity.PhantomHunter.this.random.nextInt(350) == 0) {
                this.height = -4.0F + com.Moyashi.nekokamiko.entity.PhantomHunter.this.random.nextFloat() * 9.0F;
            }

            if (com.Moyashi.nekokamiko.entity.PhantomHunter.this.random.nextInt(250) == 0) {
                ++this.distance;
                if (this.distance > 100.0F) {
                    this.distance = 50.0F;
                    this.clockwise = -this.clockwise;
                }
            }

            if (com.Moyashi.nekokamiko.entity.PhantomHunter.this.random.nextInt(450) == 0) {
                this.angle = com.Moyashi.nekokamiko.entity.PhantomHunter.this.random.nextFloat() * 2.0F * (float)Math.PI;
                this.selectNext();
            }

            if (this.touchingTarget()) {
                this.selectNext();
            }

            if (com.Moyashi.nekokamiko.entity.PhantomHunter.this.moveTargetPoint.y < com.Moyashi.nekokamiko.entity.PhantomHunter.this.getY() && !com.Moyashi.nekokamiko.entity.PhantomHunter.this.level.isEmptyBlock(com.Moyashi.nekokamiko.entity.PhantomHunter.this.blockPosition().below(1))) {
                this.height = Math.max(1.0F, this.height);
                this.selectNext();
            }

            if (com.Moyashi.nekokamiko.entity.PhantomHunter.this.moveTargetPoint.y > com.Moyashi.nekokamiko.entity.PhantomHunter.this.getY() && !com.Moyashi.nekokamiko.entity.PhantomHunter.this.level.isEmptyBlock(com.Moyashi.nekokamiko.entity.PhantomHunter.this.blockPosition().above(1))) {
                this.height = Math.min(-1.0F, this.height);
                this.selectNext();
            }

        }

        private void selectNext() {
            if (BlockPos.ZERO.equals(com.Moyashi.nekokamiko.entity.PhantomHunter.this.anchorPoint)) {
                com.Moyashi.nekokamiko.entity.PhantomHunter.this.anchorPoint = com.Moyashi.nekokamiko.entity.PhantomHunter.this.blockPosition();
            }

            this.angle += this.clockwise * 15.0F * ((float)Math.PI / 180F);
            com.Moyashi.nekokamiko.entity.PhantomHunter.this.moveTargetPoint = Vector3d.atLowerCornerOf(com.Moyashi.nekokamiko.entity.PhantomHunter.this.anchorPoint).add((double)(this.distance * MathHelper.cos(this.angle)), (double)(-4.0F + this.height), (double)(this.distance * MathHelper.sin(this.angle)));
        }
    }

    class PickAttackGoal extends Goal {
        private int nextSweepTick;

        private PickAttackGoal() {
        }

        public boolean canUse() {
            LivingEntity livingentity = com.Moyashi.nekokamiko.entity.PhantomHunter.this.getTarget();
            return livingentity != null ? com.Moyashi.nekokamiko.entity.PhantomHunter.this.canAttack(com.Moyashi.nekokamiko.entity.PhantomHunter.this.getTarget(), EntityPredicate.DEFAULT) : false;
        }

        public void start() {
            this.nextSweepTick = 10;
            com.Moyashi.nekokamiko.entity.PhantomHunter.this.attackPhase = com.Moyashi.nekokamiko.entity.PhantomHunter.AttackPhase.CIRCLE;
            this.setAnchorAboveTarget();
        }

        public void stop() {
            com.Moyashi.nekokamiko.entity.PhantomHunter.this.anchorPoint = com.Moyashi.nekokamiko.entity.PhantomHunter.this.level.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING, com.Moyashi.nekokamiko.entity.PhantomHunter.this.anchorPoint).above(10 + com.Moyashi.nekokamiko.entity.PhantomHunter.this.random.nextInt(20));
        }

        public void tick() {
            if (com.Moyashi.nekokamiko.entity.PhantomHunter.this.attackPhase == com.Moyashi.nekokamiko.entity.PhantomHunter.AttackPhase.CIRCLE) {
                --this.nextSweepTick;
                if (this.nextSweepTick <= 0) {
                    com.Moyashi.nekokamiko.entity.PhantomHunter.this.attackPhase = com.Moyashi.nekokamiko.entity.PhantomHunter.AttackPhase.SWOOP;
                    this.setAnchorAboveTarget();
                    this.nextSweepTick = (8 + com.Moyashi.nekokamiko.entity.PhantomHunter.this.random.nextInt(4)) * 20;
                    com.Moyashi.nekokamiko.entity.PhantomHunter.this.playSound(SoundEvents.PHANTOM_SWOOP, 10.0F, 0.95F + com.Moyashi.nekokamiko.entity.PhantomHunter.this.random.nextFloat() * 0.1F);
                }
            }

        }

        private void setAnchorAboveTarget() {
            com.Moyashi.nekokamiko.entity.PhantomHunter.this.anchorPoint = com.Moyashi.nekokamiko.entity.PhantomHunter.this.getTarget().blockPosition().above(20 + com.Moyashi.nekokamiko.entity.PhantomHunter.this.random.nextInt(20));
            if (com.Moyashi.nekokamiko.entity.PhantomHunter.this.anchorPoint.getY() < com.Moyashi.nekokamiko.entity.PhantomHunter.this.level.getSeaLevel()) {
                com.Moyashi.nekokamiko.entity.PhantomHunter.this.anchorPoint = new BlockPos(com.Moyashi.nekokamiko.entity.PhantomHunter.this.anchorPoint.getX(), com.Moyashi.nekokamiko.entity.PhantomHunter.this.level.getSeaLevel() + 1, com.Moyashi.nekokamiko.entity.PhantomHunter.this.anchorPoint.getZ());
            }

        }
    }

    class SweepAttackGoal extends com.Moyashi.nekokamiko.entity.PhantomHunter.MoveGoal {
        private SweepAttackGoal() {
        }

        public boolean canUse() {
            return com.Moyashi.nekokamiko.entity.PhantomHunter.this.getTarget() != null && com.Moyashi.nekokamiko.entity.PhantomHunter.this.attackPhase == com.Moyashi.nekokamiko.entity.PhantomHunter.AttackPhase.SWOOP;
        }

        public boolean canContinueToUse() {
            LivingEntity livingentity = com.Moyashi.nekokamiko.entity.PhantomHunter.this.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else if (!(livingentity instanceof PlayerEntity) || !((PlayerEntity)livingentity).isSpectator() && !((PlayerEntity)livingentity).isCreative()) {
                if (!this.canUse()) {
                    return false;
                } else {
                    if (com.Moyashi.nekokamiko.entity.PhantomHunter.this.tickCount % 20 == 0) {
                        List<CatEntity> list = com.Moyashi.nekokamiko.entity.PhantomHunter.this.level.getEntitiesOfClass(CatEntity.class, com.Moyashi.nekokamiko.entity.PhantomHunter.this.getBoundingBox().inflate(16.0D), EntityPredicates.ENTITY_STILL_ALIVE);
                        if (!list.isEmpty()) {
                            for(CatEntity catentity : list) {
                                catentity.hiss();
                            }

                            return false;
                        }
                    }

                    return true;
                }
            } else {
                return false;
            }
        }

        public void start() {
        }

        public void stop() {
            com.Moyashi.nekokamiko.entity.PhantomHunter.this.setTarget((LivingEntity)null);
            com.Moyashi.nekokamiko.entity.PhantomHunter.this.attackPhase = com.Moyashi.nekokamiko.entity.PhantomHunter.AttackPhase.CIRCLE;
        }

        public void tick() {
            LivingEntity livingentity = com.Moyashi.nekokamiko.entity.PhantomHunter.this.getTarget();
            com.Moyashi.nekokamiko.entity.PhantomHunter.this.moveTargetPoint = new Vector3d(livingentity.getX(), livingentity.getY(0.5D), livingentity.getZ());
            if (com.Moyashi.nekokamiko.entity.PhantomHunter.this.getBoundingBox().inflate((double)0.2F).intersects(livingentity.getBoundingBox())) {
                com.Moyashi.nekokamiko.entity.PhantomHunter.this.doHurtTarget(livingentity);
                com.Moyashi.nekokamiko.entity.PhantomHunter.this.attackPhase = com.Moyashi.nekokamiko.entity.PhantomHunter.AttackPhase.CIRCLE;
                if (!com.Moyashi.nekokamiko.entity.PhantomHunter.this.isSilent()) {
                    com.Moyashi.nekokamiko.entity.PhantomHunter.this.level.levelEvent(1039, com.Moyashi.nekokamiko.entity.PhantomHunter.this.blockPosition(), 0);
                }
            } else if (com.Moyashi.nekokamiko.entity.PhantomHunter.this.horizontalCollision || com.Moyashi.nekokamiko.entity.PhantomHunter.this.hurtTime > 0) {
                com.Moyashi.nekokamiko.entity.PhantomHunter.this.attackPhase = com.Moyashi.nekokamiko.entity.PhantomHunter.AttackPhase.CIRCLE;
            }

        }
    }
}
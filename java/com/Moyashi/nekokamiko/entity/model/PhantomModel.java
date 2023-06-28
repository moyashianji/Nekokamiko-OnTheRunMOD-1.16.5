package com.Moyashi.nekokamiko.entity.model;// Made with Blockbench 4.7.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.Moyashi.nekokamiko.entity.PhantomHunter;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.IHeadToggle;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "nekokamiko", bus = Mod.EventBusSubscriber.Bus.MOD)

public class PhantomModel<T extends PhantomHunter> extends SegmentedModel<T> implements IHasHead, IHeadToggle {
	private final ModelRenderer Head;
	private final ModelRenderer bipedHead_r1;
	private final ModelRenderer Body;
	private final ModelRenderer bipedBody_r1;
	private final ModelRenderer RightArm;
	private final ModelRenderer bipedRightArm_r1;
	private final ModelRenderer LeftArm;
	private final ModelRenderer bipedLeftArm_r1;
	private final ModelRenderer RightLeg;
	private final ModelRenderer bipedRightLeg_r1;
	private final ModelRenderer LeftLeg;
	private final ModelRenderer bipedLeftLeg_r1;

	public PhantomModel() {
		this(64, 64);
	}

	@Override
	public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

	}

	public PhantomModel(int w, int h) {
		Head = new ModelRenderer(this).setTexSize(w, h);;
		Head.setPos(0.0F, 0.0F, 0.0F);


		bipedHead_r1 = new ModelRenderer(this).setTexSize(w, h);
		bipedHead_r1.setPos(0.0F, 24.0F, 0.0F);
		Head.addChild(bipedHead_r1);
		setRotationAngle(bipedHead_r1, 1.1345F, 0.0F, 0.0F);
		bipedHead_r1.texOffs(1, 0).addBox(-4.0F, -32.0F, -3.0F, 9.0F, 8.0F, 8.0F, 0.0F, false);

		Body = new ModelRenderer(this).setTexSize(w, h);
		Body.setPos(0.0F, 0.0F, 0.0F);


		bipedBody_r1 = new ModelRenderer(this).setTexSize(w, h);
		bipedBody_r1.setPos(0.0F, 24.0F, 0.0F);
		Body.addChild(bipedBody_r1);
		setRotationAngle(bipedBody_r1, 1.1345F, 0.0F, 0.0F);
		bipedBody_r1.texOffs(19, 16).addBox(-4.0F, -24.0F, -2.0F, 9.0F, 12.0F, 4.0F, 0.0F, false);

		RightArm = new ModelRenderer(this).setTexSize(w, h);
		RightArm.setPos(-5.0F, 2.0F, 0.0F);


		bipedRightArm_r1 = new ModelRenderer(this).setTexSize(w, h);
		bipedRightArm_r1.setPos(5.0F, 22.0F, 0.0F);
		RightArm.addChild(bipedRightArm_r1);
		setRotationAngle(bipedRightArm_r1, 1.1345F, 0.0F, 0.0F);
		bipedRightArm_r1.texOffs(46, 16).addBox(-9.0F, -24.0F, -2.0F, 5.0F, 12.0F, 4.0F, 0.0F, false);

		LeftArm = new ModelRenderer(this).setTexSize(w, h);
		LeftArm.setPos(5.0F, 2.0F, 0.0F);


		bipedLeftArm_r1 = new ModelRenderer(this).setTexSize(w, h);
		bipedLeftArm_r1.setPos(-5.0F, 22.0F, 0.0F);
		LeftArm.addChild(bipedLeftArm_r1);
		setRotationAngle(bipedLeftArm_r1, 1.1345F, 0.0F, 0.0F);
		bipedLeftArm_r1.texOffs(46, 16).addBox(5.0F, -24.0F, -2.0F, 5.0F, 12.0F, 4.0F, 0.0F, false);

		RightLeg = new ModelRenderer(this).setTexSize(w, h);
		RightLeg.setPos(-1.9F, 12.0F, 0.0F);


		bipedRightLeg_r1 = new ModelRenderer(this).setTexSize(w, h);
		bipedRightLeg_r1.setPos(1.9F, 12.0F, 0.0F);
		RightLeg.addChild(bipedRightLeg_r1);
		setRotationAngle(bipedRightLeg_r1, 1.1345F, 0.0F, 0.0F);
		bipedRightLeg_r1.texOffs(1, 16).addBox(-3.9F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		LeftLeg = new ModelRenderer(this).setTexSize(w, h);
		LeftLeg.setPos(1.9F, 12.0F, 0.0F);


		bipedLeftLeg_r1 = new ModelRenderer(this).setTexSize(w, h);
		bipedLeftLeg_r1.setPos(-1.9F, 12.0F, 0.0F);
		LeftLeg.addChild(bipedLeftLeg_r1);
		setRotationAngle(bipedLeftLeg_r1, 1.1345F, 0.0F, 0.0F);
		bipedLeftLeg_r1.texOffs(1, 16).addBox(0.9F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
	}



	@Override
	public Iterable<ModelRenderer> parts() {
		return ImmutableList.of(this.getHead(), this.Body, this.RightArm, this.LeftArm, this.RightLeg, this.LeftLeg);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		RightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		RightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
	@Override
	public ModelRenderer getHead() {
		return this.Head;
	}

	@Override
	public void hatVisible(boolean p_217146_1_) {

	}
}
package com.kaboomroads.sculkybits.fluid;

import com.kaboomroads.sculkybits.Sculkybits;
import com.kaboomroads.sculkybits.effect.ModEffects;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.function.Consumer;

public class ModFluidTypes {
    private static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Sculkybits.MOD_ID);

    public static final RegistryObject<FluidType> SCULK_TYPE = FLUID_TYPES.register("sculk_fluid", () ->
            new FluidType(FluidType.Properties.create()
                    .descriptionId("fluid.sculkybits.sculk_fluid")
                    .canSwim(false)
                    .canDrown(false)
                    .adjacentPathType(null)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                    .lightLevel(1)
                    .density(13000)
                    .viscosity(5000)
                    .temperature(300)
                    .fallDistanceModifier(0.75f)
                    .supportsBoating(true)
                    .motionScale(0.01)) {
                @Override
                public boolean move(FluidState state, LivingEntity entity, Vec3 movementVector, double gravity) {
                    double multiplier = 0.25;
                    if (entity.fallDistance >= 3) {
                        entity.causeFallDamage(entity.fallDistance, getFallDistanceModifier(entity), entity.level().damageSources().fall());
                        entity.resetFallDistance();
                    }
                    Vec3 vec3 = entity.getDeltaMovement().multiply(multiplier, entity.getDeltaMovement().y > 0 ? multiplier : multiplier * 2, multiplier);
                    entity.setDeltaMovement(vec3.x, entity.onGround() ? 0 : vec3.y - 0.005, vec3.z);
                    entity.addEffect(new MobEffectInstance(ModEffects.SCULK.get(), 200, 0));
                    return super.move(state, entity, movementVector, gravity);
                }

                @Override
                public void setItemMovement(ItemEntity entity) {
                    Vec3 vec3 = entity.getDeltaMovement();
                    entity.setDeltaMovement(vec3.x * (double) 0.95F, vec3.y + (double) (vec3.y < (double) 0.06F ? 5.0E-4F : 0.0F), vec3.z * (double) 0.95F);
                }

                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new IClientFluidTypeExtensions() {
                        private static final ResourceLocation
                                SCULK_STILL = new ResourceLocation(Sculkybits.MOD_ID, "block/sculk_fluid_still"),
                                SCULK_FLOW = new ResourceLocation(Sculkybits.MOD_ID, "block/sculk_fluid_flow");

                        @NotNull
                        @Override
                        public Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                            return new Vector3f(0f, 0.1f, 0.125f);
                        }

                        @Override
                        public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                            RenderSystem.setShaderFogStart(0.1f);
                            RenderSystem.setShaderFogEnd(0.1f);
                            RenderSystem.setShaderFogShape(FogShape.SPHERE);
                        }

                        @Override
                        public ResourceLocation getStillTexture() {
                            return SCULK_STILL;
                        }

                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return SCULK_FLOW;
                        }
                    });
                }
            });

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}

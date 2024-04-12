package io.github.trashoflevillage.more_golem_flowers.mixin;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IronGolemEntity.class)
public abstract class IronGolemEntityMixin extends GolemEntity implements Angerable {
    private String flowerType;

    protected IronGolemEntityMixin(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        flowerType = findFlowerType();
        return entityData;
    }

    @Inject(at = @At("HEAD"), method = "writeCustomDataToNbt")
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo info) {
        if (flowerType != null && !flowerType.equals("")) nbt.putString("flowerType", getFlowerType());
    }

    @Inject(at = @At("HEAD"), method = "readCustomDataFromNbt")
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo info) {
        if (flowerType == null || flowerType.equals("")) setFlowerType(findFlowerType());
        if (!nbt.contains("flowerType")) setFlowerType(findFlowerType());
        else setFlowerType(nbt.getString("flowerType"));
    }

    public void setFlowerType(String type) {
        flowerType = type;
    }

    public String getFlowerType() {
        return flowerType;
    }

    private String findFlowerType() {
        World world = getWorld();
        RegistryEntry<Biome> biome = world.getBiome(getBlockPos());

        if (biomeEquals(biome, BiomeKeys.SWAMP)) return "blue_orchid";
        if (biomeHasTag(biome, BiomeTags.IS_TAIGA)) return "dandelion";
        if (biomeEquals(biome, BiomeKeys.MEADOW)) return "allium";
        if (biomeEquals(biome, BiomeKeys.DESERT)) return "lily_of_the_valley";
        if (biomeHasTag(biome, BiomeTags.IS_JUNGLE)) return "pink_tulip";
        if (biomeHasTag(biome, BiomeTags.IS_SAVANNA)) return "orange_tulip";
        if (biomeHasTag(biome, BiomeTags.SPAWNS_SNOW_FOXES)) return "cornflower";
        return "poppy";
    }

    private boolean biomeEquals(RegistryEntry<Biome> biome, RegistryKey<Biome> biome2) {
        return biome.getKey().equals(biome2);
    }

    private boolean biomeHasTag(RegistryEntry<Biome> biome, TagKey<Biome> tag) {
        return biome.isIn(tag);
    }
}

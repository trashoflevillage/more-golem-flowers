package io.github.trashoflevillage.biome_golems.mixin;

import io.github.trashoflevillage.biome_golems.util.ModTags;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
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
import net.minecraft.registry.RegistryKeys;
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
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IronGolemEntity.class)
public abstract class IronGolemEntityMixin extends GolemEntity implements Angerable {
    @Unique
    private static final String UNUSED_FLOWER_TYPE = "";

    private static final TrackedData<String> FLOWER_TRACKER = DataTracker.registerData(IronGolemEntity.class, TrackedDataHandlerRegistry.STRING);

    protected IronGolemEntityMixin(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

//    @Nullable
//    @Override
//    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
//        initModData();
//        return entityData;
//    }

    private void initModData() {
        setGolemVariant(findGolemVariant());
    }

    @Inject(method = "initDataTracker", at = @At("HEAD"))
    public void initDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(FLOWER_TRACKER, UNUSED_FLOWER_TYPE);
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (getGolemVariant().equals(UNUSED_FLOWER_TYPE)) {
            setGolemVariant(findGolemVariant());
        }
    }

    @Inject(at = @At("HEAD"), method = "writeCustomDataToNbt")
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo info) {
        String golemVariant = getGolemVariant();
        if (golemVariant != null && !golemVariant.equals(UNUSED_FLOWER_TYPE)) nbt.putString("golemVariant", getGolemVariant());
    }

    @Inject(at = @At("HEAD"), method = "readCustomDataFromNbt")
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo info) {
        if (!nbt.contains("golemVariant")) setGolemVariant(findGolemVariant());
        else setGolemVariant(nbt.getString("golemVariant"));
    }

    @Unique
    public void setGolemVariant(String type) {
        getDataTracker().set(FLOWER_TRACKER, type);
    }

    @Unique
    public String getGolemVariant() {
        return getDataTracker().get(FLOWER_TRACKER);
    }

    @Unique
    private String findGolemVariant() {
        World world = getWorld();
        RegistryEntry<Biome> biome = world.getBiome(getBlockPos());

        if (biomeHasTag(biome, ModTags.Biomes.SPAWNS_BLUE_ORCHID_GOLEM)) return "blue_orchid";
        if (biomeHasTag(biome, ModTags.Biomes.SPAWNS_DANDELION_GOLEM)) return "dandelion";
        if (biomeHasTag(biome, ModTags.Biomes.SPAWNS_ALLIUM_GOLEM)) return "allium";
        if (biomeHasTag(biome, ModTags.Biomes.SPAWNS_PINK_TULIP_GOLEM)) return "pink_tulip";
        if (biomeHasTag(biome, ModTags.Biomes.SPAWNS_WHITE_TULIP_GOLEM)) return "white_tulip";
        if (biomeHasTag(biome, ModTags.Biomes.SPAWNS_VINE_GOLEM)) return "vine";
        if (biomeHasTag(biome, ModTags.Biomes.SPAWNS_ORANGE_TULIP_GOLEM)) return "orange_tulip";
        if (biomeHasTag(biome, ModTags.Biomes.SPAWNS_SUNFLOWER_GOLEM)) return "sunflower";
        if (biomeHasTag(biome, ModTags.Biomes.SPAWNS_DEAD_BUSH_GOLEM)) return "dead_bush";
        return "poppy";
    }

    @Unique
    private boolean biomeHasTag(RegistryEntry<Biome> biome, TagKey<Biome> tag) {
        return biome.isIn(tag);
    }

    @Inject(at = @At("HEAD"), method = "tickMovement")
    private void initPlayerCreated(CallbackInfo ci) {
        if (getGolemVariant().equals(UNUSED_FLOWER_TYPE)) initModData();
    }
}
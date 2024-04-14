package io.github.trashoflevillage.biome_golems.mixin;

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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IronGolemEntity.class)
public abstract class IronGolemEntityMixin extends GolemEntity implements Angerable {
    @Shadow public abstract boolean isPlayerCreated();

    private static final TrackedData<String> FLOWER_TRACKER = DataTracker.registerData(IronGolemEntity.class, TrackedDataHandlerRegistry.STRING);

    protected IronGolemEntityMixin(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        initModData();
        return entityData;
    }

    private void initModData() {
        setGolemVariant(findGolemVariant());
    }

    @Inject(method = "initDataTracker", at = @At("HEAD"))
    public void initDataTracker (CallbackInfo ci) {
        getDataTracker().startTracking(FLOWER_TRACKER, "");
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (getGolemVariant().equals("")) {
            setGolemVariant(findGolemVariant());
        }
    }

    @Inject(at = @At("HEAD"), method = "writeCustomDataToNbt")
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo info) {
        String golemVariant = getGolemVariant();
        if (golemVariant != null && !golemVariant.equals("")) nbt.putString("golemVariant", getGolemVariant());
    }

    @Inject(at = @At("HEAD"), method = "readCustomDataFromNbt")
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo info) {
        if (!nbt.contains("golemVariant")) setGolemVariant(findGolemVariant());
        else setGolemVariant(nbt.getString("golemVariant"));
    }

    public void setGolemVariant(String type) {
        getDataTracker().set(FLOWER_TRACKER, type);
    }

    public String getGolemVariant() {
        return getDataTracker().get(FLOWER_TRACKER);
    }

    private String findGolemVariant() {
        World world = getWorld();
        RegistryEntry<Biome> biome = world.getBiome(getBlockPos());

        if (biomeEquals(biome, BiomeKeys.SWAMP) || biomeEquals(biome, BiomeKeys.MANGROVE_SWAMP)) return "blue_orchid";
        if (biomeHasTag(biome, BiomeTags.IS_TAIGA)) return "dandelion";
        if (biomeEquals(biome, BiomeKeys.MEADOW)) return "allium";
        if (biomeEquals(biome, BiomeKeys.DESERT)) return "pink_tulip";
        if (biomeHasTag(biome, BiomeTags.SPAWNS_SNOW_FOXES)) return "white_tulip";
        if (biomeHasTag(biome, BiomeTags.IS_JUNGLE)) return "vine";
        if (biomeHasTag(biome, BiomeTags.IS_SAVANNA)) return "orange_tulip";
        if (biomeEquals(biome, BiomeKeys.SUNFLOWER_PLAINS)) return "sunflower";
        return "poppy";
    }

    private boolean biomeEquals(RegistryEntry<Biome> biome, RegistryKey<Biome> biome2) {
        return biome.value().equals(getWorld().getRegistryManager().get(RegistryKeys.BIOME).get(biome2));

    }

    private boolean biomeHasTag(RegistryEntry<Biome> biome, TagKey<Biome> tag) {
        return biome.isIn(tag);
    }

    @Inject(at = @At("HEAD"), method = "tickMovement")
    private void initPlayerCreated(CallbackInfo ci) {
        if (getGolemVariant().equals("")) initModData();
    }
}

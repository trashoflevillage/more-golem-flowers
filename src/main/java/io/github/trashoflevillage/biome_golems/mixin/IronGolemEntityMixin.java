package io.github.trashoflevillage.biome_golems.mixin;

import io.github.trashoflevillage.biome_golems.access.IronGolemEntityMixinAccess;
import io.github.trashoflevillage.biome_golems.util.GolemType;
import io.github.trashoflevillage.biome_golems.util.ModTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IronGolemEntity.class)
public abstract class IronGolemEntityMixin extends GolemEntity implements Angerable, IronGolemEntityMixinAccess {
    @Unique
    private static final String UNUSED_FLOWER_TYPE = "";

    @Unique
    private static final TrackedData<String> FLOWER_TRACKER = DataTracker.registerData(IronGolemEntity.class, TrackedDataHandlerRegistry.STRING);

    protected IronGolemEntityMixin(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    public void initModData() {
        setGolemVariant(findGolemVariant());
    }

    @Inject(method = "initDataTracker", at = @At("HEAD"))
    public void initDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(FLOWER_TRACKER, UNUSED_FLOWER_TYPE);
    }

    @Inject(at = @At("HEAD"), method = "writeCustomDataToNbt")
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo info) {
        GolemType golemVariant = getGolemVariant();
        if (golemVariant != null) nbt.putString("golemVariant", getGolemVariant().toString());
    }

    @Inject(at = @At("HEAD"), method = "readCustomDataFromNbt")
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo info) {
        if (!nbt.contains("golemVariant")) setGolemVariant(findGolemVariant());
        else setGolemVariant(GolemType.fromString(nbt.getString("golemVariant")));
    }

    @Unique
    public void setGolemVariant(GolemType type) {
        getDataTracker().set(FLOWER_TRACKER, type.toString());
    }

    @Unique
    public GolemType getGolemVariant() {
        return GolemType.fromString(getDataTracker().get(FLOWER_TRACKER));
    }

    @Unique
    private GolemType findGolemVariant() {
        World world = getWorld();
        RegistryEntry<Biome> biome = world.getBiome(getBlockPos());

        if (biomeHasTag(biome, ModTags.Biomes.SPAWNS_BLUE_ORCHID_GOLEM)) return GolemType.BLUE_ORCHID;
        if (biomeHasTag(biome, ModTags.Biomes.SPAWNS_DANDELION_GOLEM)) return GolemType.DANDELION;
        if (biomeHasTag(biome, ModTags.Biomes.SPAWNS_ALLIUM_GOLEM)) return GolemType.ALLIUM;
        if (biomeHasTag(biome, ModTags.Biomes.SPAWNS_PINK_TULIP_GOLEM)) return GolemType.PINK_TULIP;
        if (biomeHasTag(biome, ModTags.Biomes.SPAWNS_WHITE_TULIP_GOLEM)) return GolemType.WHITE_TULIP;
        if (biomeHasTag(biome, ModTags.Biomes.SPAWNS_VINE_GOLEM)) return GolemType.VINE;
        if (biomeHasTag(biome, ModTags.Biomes.SPAWNS_ORANGE_TULIP_GOLEM)) return GolemType.ORANGE_TULIP;
        if (biomeHasTag(biome, ModTags.Biomes.SPAWNS_SUNFLOWER_GOLEM)) return GolemType.SUNFLOWER;
        if (biomeHasTag(biome, ModTags.Biomes.SPAWNS_DEAD_BUSH_GOLEM)) return GolemType.DEAD_BUSH;
        if (biomeHasTag(biome, ModTags.Biomes.SPAWNS_EYEBLOSSOM_GOLEM)) return GolemType.EYEBLOSSOM;
        return GolemType.POPPY;
    }

    @Unique
    private boolean biomeHasTag(RegistryEntry<Biome> biome, TagKey<Biome> tag) {
        return biome.isIn(tag);
    }
}
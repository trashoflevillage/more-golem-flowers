package io.github.trashoflevillage.biomegolems.mixin;

import io.github.trashoflevillage.biomegolems.BiomeGolems;
import io.github.trashoflevillage.biomegolems.access.IronGolemEntityMixinAccess;
import io.github.trashoflevillage.biomegolems.util.GolemType;
import io.github.trashoflevillage.biomegolems.util.ModTags;
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
import net.minecraft.util.Identifier;
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
        if (golemVariant != null) nbt.putString("golemVariant", getGolemVariant().getId().toString());
    }

    @Inject(at = @At("HEAD"), method = "readCustomDataFromNbt")
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo info) {
        if (!nbt.contains("golemVariant")) setGolemVariant(findGolemVariant());
        String str = nbt.getString("golemVariant");
        if (!str.contains(":")) str = BiomeGolems.MOD_ID + ":" + str;
        Identifier id = Identifier.of(str);
        setGolemVariant(GolemType.get(id));
    }

    @Unique
    public void setGolemVariant(GolemType type) {
        getDataTracker().set(FLOWER_TRACKER, type.getId().toString());
    }

    @Unique
    public GolemType getGolemVariant() {
        String str = getDataTracker().get(FLOWER_TRACKER);
        if (!str.contains(":")) str = BiomeGolems.MOD_ID + ":" + str;
        Identifier id = Identifier.of(str);
        if (id.getNamespace() == null || id.getPath() == null) id = Identifier.of(BiomeGolems.MOD_ID, "poppy");
        return GolemType.get(id);
    }

    @Unique
    private GolemType findGolemVariant() {
        World world = getWorld();
        RegistryEntry<Biome> biome = world.getBiome(getBlockPos());

        return GolemType.getTypeForBiome(biome);
    }
}
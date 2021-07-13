package capitalistspz.test.mixin;

import capitalistspz.test.commands.Commands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EggEntity.class)
public abstract class EggEntityMxn extends ThrownItemEntity {
    public EggEntityMxn(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"),
            method="onEntityHit"
    )
    protected void onHitPlayer(EntityHitResult entityHitResult, CallbackInfo ci) {
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof PlayerEntity) // Hopefully deliver knockback to player
        {
            entity.setVelocity(entity.getVelocity().add(this.getVelocity().normalize().multiply(Commands.eggKbMultiplier)));
            entity.velocityModified = true;
            entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), Commands.eggDamage);
        }

    }
}

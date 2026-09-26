package com.idark.valoria.core.capability;

import com.idark.valoria.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.attachment.*;
import net.neoforged.neoforge.registries.*;

import java.util.function.*;

public final class ValoriaAttachments{
    private ValoriaAttachments(){}

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Valoria.ID);

    public static final Supplier<AttachmentType<UnlockableProvider>> UNLOCKABLES = ATTACHMENTS.register("pages", () -> AttachmentType.serializable(UnlockableProvider::new).copyOnDeath().build());
    public static final Supplier<AttachmentType<NihilityLevelProvider>> NIHILITY = ATTACHMENTS.register("nihility_level", () -> AttachmentType.serializable(NihilityLevelProvider::new).copyOnDeath().build());
    public static final Supplier<AttachmentType<MagmaLevelProvider>> MAGMA = ATTACHMENTS.register("magma_level", () -> AttachmentType.serializable(MagmaLevelProvider::new).copyOnDeath().build());
    public static final Supplier<AttachmentType<PlayerAbilityTracker>> ABILITIES = ATTACHMENTS.register("ability_tracker", () -> AttachmentType.serializable(PlayerAbilityTracker::new).copyOnDeath().build());

    public static void register(IEventBus eventBus){
        ATTACHMENTS.register(eventBus);
    }
}

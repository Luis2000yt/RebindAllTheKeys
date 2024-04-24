package com.minenash.rebind_all_the_keys.mixin;

import com.minenash.rebind_all_the_keys.RebindAllTheKeys;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.OptionListWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ControlsOptionsScreen.class)
public class ControlsOptionsScreenMixin extends GameOptionsScreen{

    @Shadow @Nullable private OptionListWidget optionListWidget;

    public ControlsOptionsScreenMixin(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }


    @Inject(method = "init", at = @At(value = "TAIL"))
    public void addMacCommandToControl(CallbackInfo ci) {
        if (MinecraftClient.IS_SYSTEM_MAC)
            optionListWidget.addAll(RebindAllTheKeys.doubleTapSprint, RebindAllTheKeys.doubleTapFly, RebindAllTheKeys.macCommandToControl);
        else
            optionListWidget.addAll(RebindAllTheKeys.doubleTapSprint, RebindAllTheKeys.doubleTapFly);
    }

    @Redirect(method = "getOptions", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;getSprintToggled()Lnet/minecraft/client/option/SimpleOption;"))
    private static SimpleOption addPersistentSprintMode(GameOptions instance) {
        return RebindAllTheKeys.expandedSprint;
    }

    @Redirect(method = "getOptions", at = @At(value = "INVOKE",target = "Lnet/minecraft/client/option/GameOptions;getSneakToggled()Lnet/minecraft/client/option/SimpleOption;"))
    private static SimpleOption addPersistentSneakMode(GameOptions instance) {
        return RebindAllTheKeys.expandedSneak;
    }

}

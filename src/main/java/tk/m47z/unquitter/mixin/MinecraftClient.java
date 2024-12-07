package tk.m47z.unquitter.mixin;

import tk.m47z.unquitter.Client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.gui.screen.Overlay;

import org.jetbrains.annotations.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin( net.minecraft.client.MinecraftClient.class )
public abstract
class MinecraftClient
{
	@Shadow
	@Nullable
	public ClientWorld world;
	
	@Inject( method = "setScreen", at = @At( "HEAD" ) )
	private
	void setScreenHead( @Nullable Screen screen, CallbackInfo ci )
	{
		if ( world == null )
			return;
		
		if ( screen == null || !screen.shouldPause( ) )
			Client.onResume();
		else
			Client.onPause();
	}
	
	@Inject( method = "setOverlay", at = @At( "HEAD" ) )
	private
	void setOverlayHead( @Nullable Overlay overlay, CallbackInfo ci )
	{
		if ( world == null )
			return;
		
		if ( overlay == null || !overlay.pausesGame( ) )
			Client.onResume();
		else
			Client.onPause();
	}
}

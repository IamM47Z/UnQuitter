package tk.m47z.unquitter.mixin;

import ca.weblite.objc.Client;
import ca.weblite.objc.Proxy;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.m47z.unquitter.Main;

import java.util.Objects;

@Mixin( net.minecraft.client.MinecraftClient.class )
public
class UnQuitterMinecraftClient
{
	@Inject( method = "setScreen", at = @At( "HEAD" ) )
	private
	void setScreenHead( Screen screen, CallbackInfo ci )
	{
		boolean ingame = ( screen == null );
		
		Proxy app = Client.getInstance( ).sendProxy( "NSApplication", "sharedApplication" );
		
		Long opts = ( Long ) app.send( "presentationOptions" );
		if ( Objects.equals( opts, Main.original_opts ) && !ingame )
			return;
		
		if ( ingame )
			app.send( "setPresentationOptions:", 1 + 4 + 2048 + 16 + 64 + 256 + 32 + 128 );
		else
			app.send( "setPresentationOptions:", Main.original_opts );
		
		Main.LOGGER.info( "Set presentation options: " + app.send( "presentationOptions" ) );
	}
}

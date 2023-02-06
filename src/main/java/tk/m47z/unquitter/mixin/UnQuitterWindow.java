package tk.m47z.unquitter.mixin;

import tk.m47z.unquitter.Main;

import net.minecraft.client.MinecraftClient;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.glfw.GLFW.*;

@Mixin( net.minecraft.client.util.Window.class )
public
class UnQuitterWindow
{
	@Shadow
	@Final
	private long handle;
	
	@Inject( method = "updateWindowRegion", at = @At( "TAIL" ) )
	private
	void updateWindowRegionTail( CallbackInfo ci )
	{
		glfwSetWindowCloseCallback( this.handle, ( hwnd ) ->
		{
			Main.LOGGER.info( "UnQuitter: window close callback called" );
			
			// Cancel while in game
			if ( MinecraftClient.getInstance( ).currentScreen == null )
			{
				glfwSetWindowShouldClose( hwnd, false );
				MinecraftClient.getInstance( ).keyboard.onKey( this.handle, GLFW_KEY_Q, 0, GLFW_PRESS, GLFW_MOD_SUPER );
			}
		} );
		
		UnQuitterMacWindowUtil.getCocoaWindow( this.handle ).ifPresent( ( hwnd ) ->
		                                                                {
			                                                                Long style_mask = ( Long ) hwnd.send(
					                                                                "styleMask" );
			
			                                                                if ( ( style_mask & 4 ) == 0 )
				                                                                return;
			
			                                                                // remove hidden button
			                                                                hwnd.send( "setStyleMask:",
			                                                                           style_mask - 4 );
			
			                                                                style_mask = ( Long ) hwnd.send( "styleMask" );
			                                                                Main.LOGGER.info( "UnQuitter: styleMask: " +
					                                                                                  style_mask );
		                                                                } );
	}
}

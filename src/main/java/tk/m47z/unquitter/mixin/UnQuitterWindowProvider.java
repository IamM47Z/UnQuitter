package tk.m47z.unquitter.mixin;

import net.minecraft.client.MinecraftClient;
import tk.m47z.unquitter.Main;

import net.minecraft.client.util.Window;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static org.lwjgl.glfw.GLFW.*;

@Mixin( net.minecraft.client.util.WindowProvider.class )
public abstract
class UnQuitterWindowProvider
{
	@Inject( method = "createWindow", at = @At( "TAIL" ) )
	private
	void createWindowTail( CallbackInfoReturnable< Window > cir )
	{
		if ( cir.getReturnValue( ) == null || cir.getReturnValue( ).getHandle( ) == 0 )
			return;
		
		long hwnd_id = cir.getReturnValue( ).getHandle( );
		
		Main.LOGGER.info( "UnQuitter: patching window " + hwnd_id );
		
		glfwSetWindowCloseCallback( hwnd_id, ( hwnd ) ->
		{
			Main.LOGGER.info( "UnQuitter: window close callback called" );
			
			// Propagate the keys used to close the window (CMD + Q)
			if ( !MinecraftClient.getInstance( ).isPaused( ) )
				MinecraftClient.getInstance( ).keyboard.onKey( hwnd_id, GLFW_KEY_Q, 0, GLFW_PRESS, GLFW_MOD_SUPER );
			
			glfwSetWindowShouldClose( hwnd, MinecraftClient.getInstance( ).isPaused( ) );
		} );
		
		UnQuitterMacWindowUtil.getCocoaWindow( hwnd_id ).ifPresent( ( hwnd ) ->
		                                                            {
			                                                            Long style_mask = ( Long ) hwnd.send(
					                                                            "styleMask" );
			
			                                                            if ( ( style_mask & 4 ) == 0 )
				                                                            return;
			
			                                                            // remove hidden button
			                                                            hwnd.send( "setStyleMask:", style_mask - 4 );
			
			                                                            style_mask = ( Long ) hwnd.send( "styleMask" );
			                                                            Main.LOGGER.info(
					                                                            "UnQuitter: styleMask: " + style_mask );
		                                                            } );
		
	}
}

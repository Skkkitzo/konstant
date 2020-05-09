package net.skkkitzo.konstant.gui;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.skkkitzo.konstant.Main;

public class GUIDrill extends GuiScreen {
	
	private GuiButton a;
	private GuiButton b;
	private GuiButton c;
	private GuiButton d;

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}
	
	@Override
	public void initGui() {
		this.buttonList.add(this.a = new GuiButton(0, this.width / 2 - 100, this.height / 2 - 24, "Normal"));
		this.buttonList.add(this.b = new GuiButton(1, this.width / 2 - 100, this.height / 2 + 4 , "Wide 3x3"));
		this.buttonList.add(this.c = new GuiButton(2, this.width / 2 - 100, this.height / 2 + 32 , "Wide 5x5"));
		this.buttonList.add(this.d = new GuiButton(3, this.width / 2 - 100, this.height / 2 + 60, "Vein"));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		this.mc.displayGuiScreen(null);
		this.mc.setIngameFocus();
		
		if (button == this.a)
			Main.drill_mode = "normal";
		else if (button == this.b)
			Main.drill_mode = "wide3x3";
		else if (button == this.c)
			Main.drill_mode = "wide5x5";
		else if (button == this.d)
			Main.drill_mode = "vein";
	}
}

// fuck you rat go brrrrrrrr
package risingtide.tidehack.utils.tooltip;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipData;

public interface MeteorTooltipData extends TooltipData {
    MinecraftClient mc = MinecraftClient.getInstance();

    TooltipComponent getComponent();
}

// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.render;

import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;

public class UnfocusedCPU extends Module {
    public UnfocusedCPU() {
        super(Categories.Render, "unfocused-cpu", "Will not render anything when your Minecraft window is not focused.");
    }
}

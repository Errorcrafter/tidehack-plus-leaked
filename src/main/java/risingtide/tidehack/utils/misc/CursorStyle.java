// fuck you rat go brrrrrrrr
package risingtide.tidehack.utils.misc;

import org.lwjgl.glfw.GLFW;

public enum CursorStyle {
    Default,
    Click,
    Type;

    private boolean created;
    private long cursor;

    public long getGlfwCursor() {
        if (!created) {
            switch (this) {
                case Click: cursor = GLFW.glfwCreateStandardCursor(GLFW.GLFW_HAND_CURSOR); break;
                case Type:  cursor = GLFW.glfwCreateStandardCursor(GLFW.GLFW_IBEAM_CURSOR); break;
            }

            created = true;
        }

        return cursor;
    }
}

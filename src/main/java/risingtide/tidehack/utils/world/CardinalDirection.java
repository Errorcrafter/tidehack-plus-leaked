// fuck you rat go brrrrrrrr
package risingtide.tidehack.utils.world;

import net.minecraft.util.math.Direction;

public enum CardinalDirection {
    North,
    East,
    South,
    West;

    public Direction toDirection() {
        return switch (this) {
            case North -> Direction.NORTH;
            case East -> Direction.EAST;
            case South -> Direction.SOUTH;
            case West -> Direction.WEST;
        };
    }

    public static CardinalDirection fromDirection(Direction direction) {
        return switch (direction) {
            case NORTH -> North;
            case SOUTH -> South;
            case WEST -> East;
            case EAST -> West;
            case DOWN, UP -> null;
        };
    }
}

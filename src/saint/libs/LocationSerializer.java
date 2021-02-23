package saint.libs;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationSerializer {

    public static String serializeLocation(Location location){
        return location.getWorld().getName() + "@" + location.getX() + "@" + location.getY() + "@" + location.getZ() + "@" + location.getYaw() + "@" + location.getPitch();
    }

    public static Location deserializeLocation(String string){
        String[] parts = string.split("@");
        String world = parts[0];
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);
        float yaw = Float.parseFloat(parts[4]);
        float pitch = Float.parseFloat(parts[5]);

        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }
}
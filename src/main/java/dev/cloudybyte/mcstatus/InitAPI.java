package dev.cloudybyte.mcstatus;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;
import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static spark.Spark.get;

public class InitAPI {
    public void initAPI(Server server){
        get("/api", (req, res) -> {
            res.status(300);
            return null;
        });
        get("/api/players", (req, res) -> {
            JSONArray players = new JSONArray();
            for (Player player : server.getOnlinePlayers()){
                JSONObject playerObject = new JSONObject()
                        .put("name", player.getName())
                        .put("uuid", player.getUniqueId().toString())
                        .put("health", player.getHealth())
                        .put("hunger", player.getFoodLevel());
                players.put(playerObject);
            }
            String jsonres = new JSONObject()
                    .put("players", players)
                    .toString();
            res.body(jsonres);
            res.status(200);
            res.header("Content-Type", "appliation/json");
            return jsonres;
        });
        get("/api/stats", (req, res) -> {
            OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                    OperatingSystemMXBean.class);
            Runtime r = Runtime.getRuntime();
            String jsonres = new JSONObject()
                    .put("ramusage", humanReadableByteCount(r.totalMemory() - r.freeMemory()))
                    .put("playercount", server.getOnlinePlayers().size())
                    .put("cpuusage", round(osBean.getProcessCpuLoad(), 2))
                    .toString();
            res.status(200);
            res.body(jsonres);
            res.header("Content-Type", "appliation/json");
            return jsonres;
        });




    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    public static String humanReadableByteCount(long bytes) {
        int unit = 1024;
        if (bytes < unit) return bytes + "B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = ("KMGTPE").charAt(exp - 1) + ("");
        return String.format("%.1f%sB", bytes / Math.pow(unit, exp), pre).replace(",", ".");
    }



}

package de.tomalbrc.portalcontrol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

public class ModConfig {
    private static Path CONFIG_FILE_PATH = FabricLoader.getInstance().getConfigDir().resolve("portalcontrol.json");
    private static ModConfig instance;

    private static Gson gson = new GsonBuilder()
            .registerTypeHierarchyAdapter(ResourceLocation.class, new ResourceLocation.Serializer())
            .setPrettyPrinting()
            .create();

    //
    // entries
    //


    public String sourceDimension = "minecraft:the_end";
    public String targetDimension = "minecraft:overworld";

    public List<Integer> targetPosition = List.of(0, 150, 0);

    public float xRot;
    public float yRot;

    //
    // end entries
    //

    public static ModConfig getInstance() {
        if (instance == null) {
            load();
        }
        return instance;
    }
    public static void load() {
        if (!CONFIG_FILE_PATH.toFile().exists()) {
            instance = new ModConfig();
            try {
                if (CONFIG_FILE_PATH.toFile().createNewFile()) {
                    FileOutputStream stream = new FileOutputStream(CONFIG_FILE_PATH.toFile());
                    stream.write(gson.toJson(instance).getBytes(StandardCharsets.UTF_8));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        try {
            ModConfig.instance = gson.fromJson(new FileReader(ModConfig.CONFIG_FILE_PATH.toFile()), ModConfig.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
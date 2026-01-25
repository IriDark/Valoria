package com.idark.valoria.core.config;

import com.idark.valoria.*;
import net.minecraftforge.fml.loading.*;

import java.io.*;
import java.nio.file.*;

public class ConfigMigrator {
    public static void migrate() {
        Valoria.LOGGER.debug("Valoria config Migrator checking");
        try {
            Path configDir = FMLPaths.CONFIGDIR.get();
            Path newConfigDir = configDir.resolve("valoria");

            if (!Files.exists(newConfigDir)) {
                Files.createDirectories(newConfigDir);
            }

            moveIfNeeded(
                configDir.resolve("valoria-common.toml"),
                newConfigDir.resolve("common.toml")
            );

            moveIfNeeded(
                configDir.resolve("valoria-client.toml"), 
                newConfigDir.resolve("client.toml")
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void moveIfNeeded(Path oldPath, Path newPath) throws IOException{
        if (Files.exists(oldPath) && !Files.exists(newPath)) {
            Valoria.LOGGER.info("Migrating: {} -> {}", oldPath.getFileName(), newPath);
            System.out.println("[Valoria Migrator] Migrating: " + oldPath.getFileName() + " -> " + newPath);
            Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
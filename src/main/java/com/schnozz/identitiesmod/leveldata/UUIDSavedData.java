package com.schnozz.identitiesmod.leveldata;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UUIDSavedData extends SavedData {
    private static final String UUID_LIST_TAG = "UUIDList";  // NBT tag for storing UUIDs
    private final List<UUID> uuidList = new ArrayList<>();

    // Load data from NBT (called when loading saved data)
    public static UUIDSavedData load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        UUIDSavedData data = new UUIDSavedData();

        if (tag.contains(UUID_LIST_TAG, Tag.TAG_LIST)) {
            ListTag listTag = tag.getList(UUID_LIST_TAG, Tag.TAG_STRING);  // List of UUID strings
            for (int i = 0; i < listTag.size(); i++) {
                try {
                    UUID uuid = UUID.fromString(listTag.getString(i));  // Convert the string to UUID
                    data.uuidList.add(uuid);  // Add UUID to the list
                } catch (IllegalArgumentException e) {
                    // Handle malformed UUID string (log if needed)
                }
            }
        }

        return data;
    }

    // Save data to NBT (called when saving data)
    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        ListTag listTag = new ListTag();
        for (UUID uuid : uuidList) {
            listTag.add(StringTag.valueOf(uuid.toString()));  // Add each UUID as a StringTag to the list
        }
        tag.put(UUID_LIST_TAG, listTag);  // Save the list of UUIDs
        return tag;
    }

    // Add a UUID to the list
    public void addUUID(UUID uuid) {
        if (!uuidList.contains(uuid)) {
            uuidList.add(uuid);
            this.setDirty();  // Mark the data as dirty to trigger saving
        }
    }

    // Remove a UUID from the list
    public void removeUUID(UUID uuid) {
        if (uuidList.remove(uuid)) {
            this.setDirty();  // Mark the data as dirty to trigger saving
        }
    }

    // Get the list of stored UUIDs
    public List<UUID> getUUIDList() {
        return uuidList;
    }

    // Access saved data from the Overworld (using MinecraftServer)
    public static UUIDSavedData get(MinecraftServer server) {
        return server.getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(new Factory<>(UUIDSavedData::new, UUIDSavedData::load), "example");
    }



}

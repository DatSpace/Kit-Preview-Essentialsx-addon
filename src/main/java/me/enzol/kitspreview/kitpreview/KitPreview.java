package me.enzol.kitspreview.kitpreview;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import me.enzol.kitspreview.KitsPreview;
import me.enzol.kitspreview.kitpreview.item.KitItem;

import java.io.*;
import java.util.List;

public class KitPreview {

    private final transient KitsPreview pluginInstance;

    private String kitName;
    private int rows;
    private final List<KitItem> items;

    public void save(){
        Gson gson = pluginInstance.getGson();

        try (Writer writer = new FileWriter(pluginInstance.getDataFolder() + File.separator + kitName + ".json")) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KitPreview(String kitName, int rows, KitsPreview pluginInstance){
        this.kitName = kitName;
        this.rows = rows;
        this.items = Lists.newArrayList();
        this.pluginInstance = pluginInstance;
    }

    public List<KitItem> getItems() {
        return items;
    }

    public int getRows() {
        return rows;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
}
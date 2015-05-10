package com.beatitudes.mybaby.Baby;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BabyContent {

    /**
     * An array of sample (baby) items.
     */
    public static List<BabyItem> ITEMS = new ArrayList<BabyItem>();

    /**
     * A map of sample (baby) items, by ID.
     */
    public static Map<String, BabyItem> ITEM_MAP = new HashMap<String, BabyItem>();

    static {
        // Add 3 sample items.
        addItem(new BabyItem("1", "View Vaccination Calendar"));
        addItem(new BabyItem("2", "Add New Vaccination Entry"));
        addItem(new BabyItem("3", "Delete Vaccination Entry"));
        addItem(new BabyItem("4", "Update Vaccination Entry"));
    }

    private static void addItem(BabyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A baby item representing a piece of content.
     */
    public static class BabyItem {
        public String id;
        public String content;

        public BabyItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}

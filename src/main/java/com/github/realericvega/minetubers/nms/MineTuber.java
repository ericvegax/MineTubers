package com.github.realericvega.minetubers.nms;

import com.github.realericvega.minetubers.MineTubersPlugin;
import com.github.realericvega.minetubers.nms.v1_18_2.Dream_v1_18_2;
import com.github.realericvega.minetubers.nms.v1_18_2.Hypixel_v1_18_2;
import com.github.realericvega.minetubers.nms.v1_18_2.Technoblade_v1_18_2;
import lombok.Getter;

public enum MineTuber {

    TECHNOBLADE("Technoblade") {
        @Override
        public NMSPlayer getNMSPlayer() {
            return new Technoblade_v1_18_2(MineTubersPlugin.getPlugin(MineTubersPlugin.class));
        }
    },
    HYPIXEL("Hypixel") {
        @Override
        public NMSPlayer getNMSPlayer() {
            return new Hypixel_v1_18_2();
        }
    },
    DREAM("Dream") {
        @Override
        public NMSPlayer getNMSPlayer() {
            return new Dream_v1_18_2();
        }
    },

    TOMMY_INNIT("TommyInnit") {
        @Override
        public NMSPlayer getNMSPlayer() {
            return null;
        }
    };

    @Getter
    private final String NAME;

    MineTuber(String name) {
        this.NAME = name;
    }

    public abstract NMSPlayer getNMSPlayer();
}

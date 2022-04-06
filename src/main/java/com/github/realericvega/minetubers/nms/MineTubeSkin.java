package com.github.realericvega.minetubers.nms;

import com.mojang.authlib.properties.Property;
import lombok.Getter;

public enum MineTubeSkin {

    TECHNOBLADE ("XHg3sawUkkCS/JqiZMk6apPPPBjqw2TQH2ATASxmGzCGUoMBGRvtHVn8hYGSoEvOjsGx62+zILIazuz6+mva78AlhGY21X69FHmmLBybwoiy8qNJe9okJpIzK0icY83JO+E2JZglo8/+bHh8oW7tmrdOVLz3gR4NQaS+PLloaBx0MxrZdSUmI27y2oKrSVmsovEjlfMoNAWyN7etaC3jdvTlxScsd33FBRPRXZ9M+Ux77n62QUVyi3dvmWMfQ7g9XzWm8JnOokg/tIOzYhSQMRW6vjBSQn3Lj1SsT7IL5Ddklc5bx89u7it+mgUQ/wHxFWKgIBCiyy/lNL26dK9L4xoxo08JdU9h64mejBZeL+eR14lAYvsV56B7aztRPp5lXVZPnYEY0mJluzLcClo+MbUm3eF1IeE1jedhGMrg5MbmwwIGySj148G06v5CUde3BsJwBu6uKpL/bej1NJmWtH27b7XHUTeykV+mT1bbDkuBM3zSoaSBc8TZSPWPx6BIeY7tEeiLw4LHiTtD1xjzF/Altuw+MaZroKkTR8IB1vNUo+9bCZc7byGPGxabxrBKZtwuFuWF+LC7gummX9Ay1sZiwn/tPgYk5cLDxRBRxKtgSroGhNoEPHrRXuVWZPnloJqh2nPkvOfr+M3CbUL9gd6pPN9cIZUhkmhuhWIYIBA=",  "ewogICJ0aW1lc3RhbXAiIDogMTYyMzg3MDg1NTAwNCwKICAicHJvZmlsZUlkIiA6ICJiODc2ZWMzMmUzOTY0NzZiYTExNTg0MzhkODNjNjdkNCIsCiAgInByb2ZpbGVOYW1lIiA6ICJUZWNobm9ibGFkZSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83ODZjMDM5ZDk2OWQxODM5MTU1MjU1ZTM4ZTdiMDZhNjI2ZWE5ZjhiYWY5Y2I1NWUwYTc3MzExZWZlMThhM2UiCiAgICB9CiAgfQp9") {
        @Override
        public Property getSkin() {
            return new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYyMzg3MDg1NTAwNCwKICAicHJvZmlsZUlkIiA6ICJiODc2ZWMzMmUzOTY0NzZiYTExNTg0MzhkODNjNjdkNCIsCiAgInByb2ZpbGVOYW1lIiA6ICJUZWNobm9ibGFkZSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83ODZjMDM5ZDk2OWQxODM5MTU1MjU1ZTM4ZTdiMDZhNjI2ZWE5ZjhiYWY5Y2I1NWUwYTc3MzExZWZlMThhM2UiCiAgICB9CiAgfQp9");
        }
    },
    DREAM ("", "") {
        @Override
        public Property getSkin() {
            return null;
        }
    },
    TOMMY_INNIT ("", "") {
        @Override
        public Property getSkin() {
            return null;
        }
    },
    HYPIXEL ("", "") {
        @Override
        public Property getSkin() {
            return null;
        }
    };

    @Getter
    private final String SIGNATURE;

    @Getter
    private final String TEXTURE;

    MineTubeSkin(String signature, String texture) {
        this.SIGNATURE = signature;
        this.TEXTURE = texture;
    }

    public abstract Property getSkin();
}

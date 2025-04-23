package com.idark.valoria.client.ui.screen.book.codex;

public enum Style{
    STANDARD(0, 208, 22),
    DIAMOND(44, 230, 66),
    GOLD(44, 208, 66),
    IRON(88, 208, 110),
    CRYPT(88, 230, 110),
    CLOSED(0, 230, 22);

    public final int x, y;
    public final int hoverX, hoverY;

    Style(int x, int y, int hoverX){
        this.x = x;
        this.y = y;
        this.hoverX = hoverX;
        this.hoverY = y;
    }

    Style(int x, int y, int hoverX, int hoverY){
        this.x = x;
        this.y = y;
        this.hoverX = hoverX;
        this.hoverY = hoverY;
    }
}
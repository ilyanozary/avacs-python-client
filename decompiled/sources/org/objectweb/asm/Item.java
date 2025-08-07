package org.objectweb.asm;

/* loaded from: avacs.jar:org/objectweb/asm/Item.class */
final class Item {
    int a;
    int b;
    int c;
    long d;
    String g;
    String h;
    String i;
    int j;
    Item k;

    Item() {
    }

    Item(int i) {
        this.a = i;
    }

    Item(int i, Item item) {
        this.a = i;
        this.b = item.b;
        this.c = item.c;
        this.d = item.d;
        this.g = item.g;
        this.h = item.h;
        this.i = item.i;
        this.j = item.j;
    }

    void a(int i) {
        this.b = 3;
        this.c = i;
        this.j = Integer.MAX_VALUE & (this.b + i);
    }

    void a(long j) {
        this.b = 5;
        this.d = j;
        this.j = Integer.MAX_VALUE & (this.b + ((int) j));
    }

    void a(float f) {
        this.b = 4;
        this.c = Float.floatToRawIntBits(f);
        this.j = Integer.MAX_VALUE & (this.b + ((int) f));
    }

    void a(double d) {
        this.b = 6;
        this.d = Double.doubleToRawLongBits(d);
        this.j = Integer.MAX_VALUE & (this.b + ((int) d));
    }

    void a(int i, String str, String str2, String str3) {
        this.b = i;
        this.g = str;
        this.h = str2;
        this.i = str3;
        switch (i) {
            case 1:
            case 7:
            case 8:
            case 13:
                this.j = Integer.MAX_VALUE & (i + str.hashCode());
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 9:
            case 10:
            case 11:
            default:
                this.j = Integer.MAX_VALUE & (i + (str.hashCode() * str2.hashCode() * str3.hashCode()));
                break;
            case 12:
                this.j = Integer.MAX_VALUE & (i + (str.hashCode() * str2.hashCode()));
                break;
        }
    }

    boolean a(Item item) {
        if (item.b != this.b) {
            return false;
        }
        switch (this.b) {
            case 1:
            case 7:
            case 8:
            case 13:
                return item.g.equals(this.g);
            case 2:
            case 9:
            case 10:
            case 11:
            default:
                return item.g.equals(this.g) && item.h.equals(this.h) && item.i.equals(this.i);
            case 3:
            case 4:
                return item.c == this.c;
            case 5:
            case 6:
            case Opcodes.DCONST_1 /* 15 */:
                return item.d == this.d;
            case 12:
                return item.g.equals(this.g) && item.h.equals(this.h);
            case 14:
                return item.c == this.c && item.g.equals(this.g);
        }
    }
}

package defpackage;

import javax.microedition.lcdui.Image;

/* loaded from: avacs.jar:d.class */
public class d extends c {
    @Override // defpackage.c
    public final Image a(Image image, int i, int i2) {
        int[] iArr = new int[image.getHeight() * image.getWidth()];
        image.getRGB(iArr, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
        int[] iArr2 = new int[i * i2];
        int height = ((image.getHeight() / i2) * image.getWidth()) - image.getWidth();
        int height2 = image.getHeight() % i2;
        int width = image.getWidth() / i;
        int width2 = image.getWidth() % i;
        int i3 = 0;
        int width3 = 0;
        int i4 = 0;
        for (int i5 = i2; i5 > 0; i5--) {
            int i6 = 0;
            for (int i7 = i; i7 > 0; i7--) {
                int i8 = i3;
                i3++;
                iArr2[i8] = iArr[width3];
                width3 += width;
                int i9 = i6 + width2;
                i6 = i9;
                if (i9 >= i) {
                    i6 -= i;
                    width3++;
                }
            }
            width3 += height;
            int i10 = i4 + height2;
            i4 = i10;
            if (i10 >= i2) {
                i4 -= i2;
                width3 += image.getWidth();
            }
        }
        return Image.createRGBImage(iArr2, i, i2, true);
    }

    @Override // defpackage.c
    public final Image a(Image image, int i) {
        int i2 = ((100 - i) * 255) / 100;
        int[] iArr = new int[image.getHeight() * image.getWidth()];
        image.getRGB(iArr, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
        for (int i3 = 0; i3 < iArr.length; i3++) {
            int i4 = iArr[i3];
            iArr[i3] = (i4 & 16777215) + (((i4 >> 24) * i2) << 24);
        }
        return Image.createRGBImage(iArr, image.getWidth(), image.getHeight(), true);
    }
}
